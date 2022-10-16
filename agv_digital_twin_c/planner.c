#include "header.h"
#include "planner.h"

#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mman.h>


int matrix[MATRIX_WIDTH][MATRIX_LENGTH] = {{ 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                            { -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 },
                            { -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                            { -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1 },
                            { -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0 } };


int node_equals(node_t *n1, node_t *n2)
{
    return n1->x == n2->x && n1->y == n2->y;
}

int f_cost(node_t *node)
{
    return node->gCost + node->hCost;
}

void push_coordinates(route_t **head_ref, node_t *node)
{
    /* 1. allocate node */
    route_t *new_node = (route_t *)malloc(sizeof(route_t));

    /* 2. put in the data  */
    new_node->coordinates[0] = node->x;
    new_node->coordinates[1] = node->y;

    /* 3. Make next of new node as head */
    new_node->next = (*head_ref);

    /* 4. move the head to point to the new node */
    (*head_ref) = new_node;
}

void push(list_t **head_ref, node_t *node)
{
    /* 1. allocate node */
    list_t *new_node = (list_t *)malloc(sizeof(list_t));

    /* 2. put in the data  */
    new_node->node = node;

    /* 3. Make next of new node as head */
    new_node->next = (*head_ref);

    /* 4. move the head to point to the new node */
    (*head_ref) = new_node;
}

void append(list_t **head_ref, node_t *node)
{
    /* 1. allocate node */
    list_t *new_node = (list_t *)malloc(sizeof(list_t));

    list_t *last = *head_ref; /* used in step 5*/
    node_t *node2 = calloc(1, sizeof(node_t));
    /* 2. put in the data  */
    memmove(node2, node, sizeof(node_t));
    new_node->node = node2;
    /* 3. This new node is going to be the last node, so make next
          of it as NULL*/
    new_node->next = NULL;

    /* 4. If the Linked List is empty, then make the new node as head */
    if (*head_ref == NULL)
    {
        *head_ref = new_node;
        return;
    }

    /* 5. Else traverse till the last node */
    while (last->next != NULL)
        last = last->next;

    /* 6. Change the next of last node */
    last->next = new_node;
    return;
}

void deleteNode(list_t **head_ref, node_t *node)
{
    // Store head node
    list_t *temp = *head_ref, *prev;

    // If head node itself holds the key to be deleted
    if (temp != NULL && node_equals(temp->node, node))
    {
        *head_ref = temp->next; // Changed head
        free(temp);             // free old head
        return;
    }

    // Search for the key to be deleted, keep track of the
    // previous node as we need to change 'prev->next'
    while (temp != NULL && !node_equals(temp->node, node))
    {
        prev = temp;
        temp = temp->next;
    }

    // If key was not present in linked list
    if (temp == NULL)
        return;

    // Unlink the node from linked list
    if(prev != NULL && prev->next != NULL)
        prev->next = temp->next;

    free(temp); // Free memory
}

node_t *list_get(list_t *head, int index)
{
    list_t *current = head;

    // The index of the node we're
    // currently looking at
    int count = 0;
    while (current != NULL)
    {
        if (count == index)
            return (current->node);
        count++;
        current = current->next;
    }

    /* If we get to this line, the
       caller was asking for a
       non-existent element so we
        assert fail */
    assert(0);
}

int get_distance(node_t *nodeA, node_t *nodeB)
{
    int distX = abs(nodeA->x - nodeB->x);
    int distY = abs(nodeA->y - nodeB->y);

    return distX + distY;
}

int search(list_t *head, node_t *node)
{
    list_t *current = head; // Initialize current
    while (current != NULL)
    {
        if (node_equals(node, current->node))
            return 1;
        current = current->next;
    }
    return 0;
}

int list_size(list_t *list)
{
    int count = 0;
    list_t *current = list;

    while (current != NULL)
    {
        count++;
        current = current->next;
    }
    return count;
}

route_t *retrace_path(node_t *start, node_t *end)
{
    route_t *path = NULL;

    node_t *current = end;

    while (!node_equals(current, start))
    {
        push_coordinates(&path, current);
        current = current->parent;
    }
    return path;
}

list_t *neighbours(node_t *node)
{
    list_t *neighbours = NULL;

    int x = node->x + 1;
    int y = node->y;

    node_t *newNode = calloc(4, sizeof(node_t));
    if (x >= 0 && x < MATRIX_WIDTH && y >= 0 && y < MATRIX_LENGTH && matrix[x][y] == 0)
    {
        newNode->x = x;
        newNode->y = y;
        newNode->walkable = 1;
        append(&neighbours, newNode);
    }

    x = node->x - 1;
    if (x >= 0 && x < MATRIX_WIDTH && y >= 0 && y < MATRIX_LENGTH && matrix[x][y] == 0)
    {
        (newNode+1)->x = x;
        (newNode+1)->y = y;
        (newNode+1)->walkable = 1;
        append(&neighbours, newNode+1);
    }

    x = node->x;
    y = node->y + 1;

    if (x >= 0 && x < MATRIX_WIDTH && y >= 0 && y < MATRIX_LENGTH && matrix[x][y] == 0)
    {
        (newNode+2)->x = x;
        (newNode+2)->y = y;
        (newNode+2)->walkable = 1;
        append(&neighbours, newNode+2);
    }

    y = node->y - 1;

    if (x >= 0 && x < MATRIX_WIDTH && y >= 0 && y < MATRIX_LENGTH && matrix[x][y] == 0)
    {
        (newNode+3)->x = x;
        (newNode+3)->y = y;
        (newNode+3)->walkable = 1;
        append(&neighbours, newNode+3);
    }
    free(newNode);
    return neighbours;
}

route_t *find_path(node_t *start, node_t *end){
    list_t *openSet = NULL;
    list_t *closedSet = NULL;

    append(&openSet, start);

    while(openSet != NULL && openSet->node != NULL){
        node_t *current_node = calloc(1, sizeof(node_t));
        memmove(current_node, list_get(openSet, 0), sizeof(node_t));
        for (int i = 1; i < list_size(openSet); i++)
        {
            node_t *other = calloc(1, sizeof(node_t));
            memmove(other, list_get(openSet, i), sizeof(node_t));
            if((f_cost(other) < f_cost(current_node)) || (f_cost(other) == f_cost(current_node) && other->hCost < current_node->hCost))
                current_node = other;
        }

        deleteNode(&openSet, current_node);
        append(&closedSet, current_node); 

        if(node_equals(current_node, end)){
            end->parent = calloc(1, sizeof(node_t));
            memmove(end->parent, current_node->parent, sizeof(node_t));
            return retrace_path(start, end);
        }  

        list_t *neighbours_list = neighbours(current_node); 
        int size = list_size(neighbours_list);

        for (int i = 0; i < size; i++)
        {
            node_t *node = list_get(neighbours_list, i);
            if(!node->walkable || search(closedSet, node))
                continue;
            int newMovementCostToNeighbour = current_node->gCost + get_distance(current_node, node);
            if(newMovementCostToNeighbour < node->gCost || !search(openSet, node)){
                node->gCost = newMovementCostToNeighbour;
                node->hCost = get_distance(node, end);
                node->parent = current_node;

                if(!search(openSet, node))
                    append(&openSet, node);
            }
        }
        
    }
    assert(0);
}

void *agvRoutePlanner(void *sh_mem)
{
    shared_mem *shared_mem = sh_mem;

    node_t *start = calloc(1, sizeof(node_t));
    start->x = shared_mem->xPos;
    start->y = shared_mem->yPos;
    start->walkable = 1;

    node_t *end = calloc(1, sizeof(node_t));
    end->x = shared_mem->route_end[0];
    end->y = shared_mem->route_end[1];
    start->walkable = 1;

    // *matrix = shared_mem->warehouse;

    pthread_mutex_lock(&create_new_route);

    while (shared_mem->is_to_calculate_route == 0)
        pthread_cond_wait(&create_new_route_cond, &create_new_route);
    shared_mem->route = *find_path(start, end);
    shared_mem->is_to_calculate_route = 0;

    pthread_mutex_unlock(&create_new_route);    

    pthread_exit((void*)NULL);
}

// int main()
// {
//     node_t start = {3, 0, 1, 0, 0};
//     node_t end = {14, 19, 1, 0, 0};

//     // node_t start = {17, 0, 1, 0, 0};
//     // node_t end = {17, 19, 1, 0, 0};
//     list_t *path = find_path(&start, &end);

//     while (path != NULL && path->node != NULL)
//     {
//         printf("(%d,%d)\n", path->node->y + 1, path->node->x + 1);
//         if(path->next != NULL)
//             path = path->next;
//         else
//             break;
//     }
// }
