#if !defined(_PLANNER_H)
#define _PLANNER_H

typedef struct node_t
{
    int x;
    int y;
    int walkable;

    int gCost;
    int hCost;

    struct node_t *parent;
} node_t;

typedef struct list_t
{
    node_t *node;
    struct list_t *next;
} list_t;

route_t *find_path(node_t *start, node_t *end);
void *agvRoutePlanner(void *sh_mem);

#endif // _PLANNER_H
