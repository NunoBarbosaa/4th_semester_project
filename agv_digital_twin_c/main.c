#include "header.h"
#include "planner.h"
#include "connections.h"

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>
#include <pthread.h>
#include <errno.h>
#include <semaphore.h>

#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mman.h>

#include <openssl/crypto.h>
#include <openssl/bio.h>
#include <openssl/ssl.h>
#include <openssl/err.h>

#define AGV_MANAGER_IP "localhost"
#define AGV_MANAGER_PORT "8999"
#define BUFFER_SIZE 300
#define SERVER_PORT "7823"
#define SHM_AGV "/shmAGV_"
#define AGV_THREADS 4 // number of modules used by each AGV
#define MAX_NUMBER_AGVs 6
#define MUTEX_AGVDATA "accessAgvData"

#define SERVER_SSL_CERT_FILE "../certs/agv_digital_twin_server.pem"
#define SERVER_SSL_KEY_FILE "../certs/agv_digital_twin_server.key"

#define AUTH_CLIENTS_SSL_CERTS_FILE "../certs/trusted_clients.pem"

int parse_messages(SSL *sslConn, char *msg_header);
void handleAssignAgvRequest(SSL *sslConn, char *msg_header);
void getWarehousePlant();
void getAGVsLocations(int warehouse_plant[MATRIX_WIDTH][MATRIX_LENGTH], int id, int coordinates[2]);
void obstacleCheck(int min, int max, int x, int y, int type, int mat[MATRIX_WIDTH][MATRIX_LENGTH], int is);
void *currentPosition(void *arg);
void *batteryManagement(void *arg);
void *agvSensors(void *n);
void *agvStartup(void *arg);
void *simEngine(void *mem);
void *agvThread(void *arg);
void writeComms (int i, int it, float b, int s);
pthread_mutex_t mutexes[6][2];
pthread_mutex_t acessWarehouse;
pthread_mutex_t create_new_route;
pthread_mutex_t controlFlow;
pthread_cond_t create_new_route_cond;
int server;
int scomp;
sem_t *sem;

void handle_SIGINT(int signo)
{
    printf("Closing!");
    kill(9, server);
    kill(9, scomp);
}

int main()
{
    struct sigaction act;
    memset(&act, 0, sizeof(struct sigaction));
    sigemptyset(&act.sa_mask); /* No signals blocked */
    act.sa_handler = handle_SIGINT;
    sigaction(9, &act, NULL);

    server = getpid();

    printf("Server !!!!!!!!!!!\n");

    struct sockaddr_storage from;
    int err, newSock, sock;
    unsigned int adl;
    char cliIPtext[BUFFER_SIZE], cliPortText[BUFFER_SIZE];
    struct addrinfo req, *list;

    bzero((char *)&req, sizeof(req));
    // requesting a IPv6 local address will allow both IPv4 and IPv6 clients to use it
    req.ai_family = AF_INET6;
    req.ai_socktype = SOCK_STREAM;
    req.ai_flags = AI_PASSIVE; // local address

    err = getaddrinfo(NULL, SERVER_PORT, &req, &list);

    if (err)
    {
        printf("Failed to get local address, error: %s\n", gai_strerror(err));
        exit(1);
    }

    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
    if (sock == -1)
    {
        perror("Failed to open socket");
        freeaddrinfo(list);
        exit(1);
    }

    if (bind(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen) == -1)
    {
        perror("Bind failed");
        close(sock);
        freeaddrinfo(list);
        exit(1);
    }

    freeaddrinfo(list);

    listen(sock, SOMAXCONN);

    const SSL_METHOD *method;
    SSL_CTX *ctx;

    method = SSLv23_server_method();
    ctx = SSL_CTX_new(method);

    // Load the server's certificate and key

    SSL_CTX_use_certificate_file(ctx, SERVER_SSL_CERT_FILE, SSL_FILETYPE_PEM);
    SSL_CTX_use_PrivateKey_file(ctx, SERVER_SSL_KEY_FILE, SSL_FILETYPE_PEM);
    if (!SSL_CTX_check_private_key(ctx))
    {
        puts("Error loading server's certificate/key");
        close(sock);
        exit(1);
    }

    // THE CLIENTS' CERTIFICATES ARE TRUSTED
    SSL_CTX_load_verify_locations(ctx, AUTH_CLIENTS_SSL_CERTS_FILE, NULL);

    // Restrict TLS version and cypher suite
    SSL_CTX_set_min_proto_version(ctx, TLS1_2_VERSION);
    SSL_CTX_set_cipher_list(ctx, "HIGH:!aNULL:!kRSA:!PSK:!SRP:!MD5:!RC4");

    // The client must provide a certificate and it must be trusted, the handshake will fail otherwise
    SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER | SSL_VERIFY_FAIL_IF_NO_PEER_CERT, NULL);

    puts("Accepting TCP connections (both over IPv6 or IPv4). Use CTRL+C to terminate the server");

    if (!fork())
    {
        adl = sizeof(from);
        for (;;)
        {
            newSock = accept(sock, (struct sockaddr *)&from, &adl);
            if (!fork())
            {
                close(sock);
                getnameinfo((struct sockaddr *)&from, adl, cliIPtext, BUFFER_SIZE, cliPortText, BUFFER_SIZE,
                            NI_NUMERICHOST | NI_NUMERICSERV);
                printf("New connection from node %s, port number %s\n", cliIPtext, cliPortText);
                SSL *sslConn = SSL_new(ctx);
                SSL_set_fd(sslConn, newSock);
                if (SSL_accept(sslConn) != 1)
                {
                    puts("TLS handshake error: client not authorized");
                    SSL_free(sslConn);
                    close(newSock);
                    exit(1);
                }
                printf("TLS version: %s\nCypher suite: %s\n",
                       SSL_get_cipher_version(sslConn), SSL_get_cipher(sslConn));
                X509 *cert = SSL_get_peer_certificate(sslConn);
                X509_free(cert);
                X509_NAME_oneline(X509_get_subject_name(cert), cliIPtext, BUFFER_SIZE);
                printf("Client's certificate subject: %s\n", cliIPtext);

                while (1)
                {
                if (SSL_read(new_sock, msg_header, sizeof(char) * 4) > 0)
                                {
                                    if (parse_messages(new_sock, msg_header))
                                        break;
                                }
                }
                SSL_free(sslConn);
                close(newSock);
                printf("Connection from node %s, port number %s closed\n", cliIPtext, cliPortText);
                exit(0);
            }
            close(newSock);
        }
        close(sock);
    }

	sem = sem_open("semafloww", O_CREAT | O_EXCL, 0644, 0);


    shared_mem *sharedMems[MAX_NUMBER_AGVs];
    pthread_t agvs[MAX_NUMBER_AGVs];
    shm_unlink("sim_engine");
    int fd = shm_open("sim_engine", O_CREAT | O_TRUNC | O_RDWR, S_IRUSR | S_IWUSR);
    if (fd == -1)
    {
        perror("Erro ao abrir o ficheiro.");
        return -1;
    }
    ftruncate(fd, sizeof(sim_engine));
    if (fd == -1)
    {
        perror("Erro no fftruncate.");
        return -1;
    }
    sim_engine *sim;
    sim = (sim_engine *)mmap(NULL, sizeof(sim_engine), PROT_READ | PROT_WRITE,
                             MAP_SHARED, fd, 0);



    getWarehousePlant(sim);

    pthread_t simeng;
    pthread_create(&simeng, NULL, simEngine, sim);
    scomp = getpid();
    for (int i = 0; i < MAX_NUMBER_AGVs; i++)
    {
        int numbers[6];
        numbers[i] = i;
        shared_mem *s;
        int fd;
        char shm_name[10];
        sprintf(shm_name, "%s%d", SHM_AGV, i);
        printf("%s\n", shm_name);
        pthread_mutex_init(&mutexes[i][0], NULL);
        pthread_mutex_init(&mutexes[i][1], NULL);
        shm_unlink(shm_name);
        fd = shm_open(shm_name, O_CREAT | O_TRUNC | O_RDWR, S_IRUSR | S_IWUSR);
        if (fd == -1)
        {
            perror("Erro ao abrir o ficheiro.");
            return -1;
        }
        ftruncate(fd, sizeof(shared_mem));
        if (fd == -1)
        {
            perror("Erro no fftruncate.");
            return -1;
        }
        s = (shared_mem *)mmap(NULL, sizeof(shared_mem), PROT_READ | PROT_WRITE,
                               MAP_SHARED, fd, 0);
        sharedMems[i] = s;
        s->speed = 0;
        s->sqr_travelled = 0;
        s->batterySOC = 100;
        s->status = 0;
        s->go_status = 0;
        int location[2] = {0,0};
        getAGVsLocations(sim->warehouse, i, location);
        if (location[0] == 0 && location[1] == 0)
            perror("AGV is unavailable");
        else
        {
            s->xPos = location[0];
            s->yPos = location[1];
            printf("Initiating AGV %d...\n", i);
            pthread_create(&agvs[i], NULL, agvStartup, (void *)&numbers[i]);
        }
        sem_post(sem);
    }

    for (int i = 0; i < MAX_NUMBER_AGVs; i++)
    {
        char shm_name[10];
        sprintf(shm_name, "%s%d", SHM_AGV, i);
        printf("%s\n", shm_name);
        int r;
        r = munmap(sharedMems[i], sizeof(shared_mem));
        if (r < 0)
        {
            perror("Error:Closing mapmemory\n");
            exit(1);
        }

        r = shm_unlink(shm_name);
        if (r < 0)
        {
            perror("Error:Closing shared memory\n");
            exit(1);
        }
        pthread_mutex_destroy(&mutexes[i][0]);
        pthread_mutex_destroy(&mutexes[i][1]);
    }

    shm_unlink("sim_engine");

    // kill(a,9);
    // do something
}

int parse_messages(SSL *sslConn, char *msg_header)
{
    if (msg_header[0] == 1 && msg_header[1] == 0)
    {
        printf("INFO: Commtest Received!\n");
        char response[4] = {1, 2, 0, 0};
        SSL_write(sslConn, response, sizeof(char) * 4);
        return 0;
    }
    if (msg_header[0] == 1 && msg_header[1] == 1)
    {
        printf("INFO: Disconnect request received!\n");
        char response[4] = {1, 2, 0, 0};
        SSL_write(sslConn, response, sizeof(char) * 4);
        return 1;
    }

    if (msg_header[0] == 1 && msg_header[1] == 5)
    {
        printf("INFO: AGV Assignment request received!\n");
        handleAssignAgvRequest(sslConn, msg_header);
        return 0;
    }

    printf("ERROR: Received unknown message.\n");
    char response[4] = {1, 10, 0, 0};
    SSL_write(sslConn, response, sizeof(char) * 4);
    return 0;
}

void handleAssignAgvRequest(SSL *sslConn, char *msg_header)
{
    int data_size = msg_header[2] + msg_header[3] * 256;

    char msg_data[data_size];

    if (SSL_read(sslConn, msg_data, data_size) < 1)
    {
        printf("ERROR: Could not read the expected content of the message.\n");
        char response[4] = {1, 10, 0, 0};
        SSL_write(sslConn, response, sizeof(char) * 4);
        return;
    }
    write(STDOUT_FILENO, msg_data, data_size);
}

void getWarehousePlant(sim_engine *mem)
{
    // SSL *sslConn = createAgvManagerConnection(AGV_MANAGER_IP, AGV_MANAGER_PORT);

    int matrix[MATRIX_WIDTH][MATRIX_LENGTH] = {{0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0},
                                               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                               {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
                                               {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                               {0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0},
                                               {0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0},
                                               {0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0},
                                               {0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0},
                                               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                               {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6},
                                               {4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                               {0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0}};


    memcpy(mem->warehouse, matrix, MATRIX_LENGTH * MATRIX_WIDTH * sizeof(int));


    // mem->warehouse = matrix;

    return;
}

void getAGVsLocations(int warehouse_plant[MATRIX_WIDTH][MATRIX_LENGTH], int id, int coordinates[2])
{
    for (int i = 0; i < MATRIX_WIDTH; i++)
    {
        for (int j = 0; j < MATRIX_LENGTH; j++)
        {
            if (warehouse_plant[i][j] == id)
            {
                coordinates[0] = j;
                coordinates[1] = i;
                return;
            }
        }
    }
    return;
}

void *agvStartup(void *arg)
{
    int p = *((int *)arg);
    pthread_t threads[AGV_THREADS];
    char shm_name[10];
    sprintf(shm_name, "%s%d", SHM_AGV, p);
    shm_unlink(shm_name);
    int fd = shm_open(shm_name, O_RDWR, S_IRUSR | S_IWUSR);
    shared_mem *s;
    s = (shared_mem *)mmap(NULL, sizeof(shared_mem), PROT_READ | PROT_WRITE,
                           MAP_SHARED, fd, 0);

    pthread_create(&threads[0], NULL, currentPosition, &p);         // positioning of agv
    pthread_create(&threads[1], NULL, batteryManagement, shm_name); // battery of agv
    pthread_create(&threads[2], NULL, agvSensors, shm_name);        // sensors of agv
    pthread_create(&threads[3], NULL, agvRoutePlanner, s);          // route planner for agv

    while(1){
    pthread_mutex_lock(&(mutexes[p][0]));

     int xpos = s->xPos, ypos = s->yPos, stat = s->status;

     float battery = s->batterySOC;

         writeComms(xpos,ypos,stat,battery);

        pthread_mutex_unlock(&(mutexes[p][0]));

        int alert = 3;

        if(pthread_mutex_trylock(&(mutexes[p][1])) != 0){
                if(errno != EBUSY){
                    alert = s->sensorStatus;
                }
            }
        if (alert == 1){
            s->go_status = 0;
        }
        else if(alert == 2){
            s->go_status = 1;
        }
        else{ s->go_status = 2;
        }
     sleep(0.1);
    }
    for (int k = 0; k < AGV_THREADS; k++)
    {
        pthread_join(threads[k], NULL);
    }
    return NULL;
}

void *currentPosition(void *arg)
{
    int ns = *((int *)arg);
    int n = ns -1;
    char shm_name[10];
    sprintf(shm_name, "%s%d", SHM_AGV, ns);
    shm_unlink(shm_name);
    int fd = shm_open(shm_name, O_RDWR, S_IRUSR | S_IWUSR);
    shared_mem *s;
    s = (shared_mem *)mmap(NULL, sizeof(shared_mem), PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    pthread_mutex_init(&mutexes[n][0], NULL);
    struct route_t *pointer;
    pointer = &(s->route);
    while (1)
    {
        if (s->go_status == 2)
        {
            pthread_mutex_lock(&mutexes[n][0]);
            s->speed = 2;
            pthread_mutex_unlock(&mutexes[n][0]);
        }
        else if (s->go_status == 1)
        {
            pthread_mutex_lock(&mutexes[n][0]);
            s->speed = 1;
            pthread_mutex_unlock(&mutexes[n][0]);
        }
        else if (s->go_status == 0)
        {
            pthread_mutex_lock(&mutexes[n][0]);
            s->speed = 0;
            pthread_mutex_unlock(&mutexes[n][0]);
        }

        // if speed is 0, agv dont move

        if (s->speed != 0)
        {

            if (pointer != NULL)
            {

                if (pointer->coordinates[0] == s->xPos)
                {
                    if (pointer->coordinates[1] > s->yPos)
                    {
                        pthread_mutex_lock(&mutexes[n][0]);
                        strcpy(s->direction, "w+");
                        pthread_mutex_unlock(&mutexes[n][0]);
                    }
                    else
                    {
                        pthread_mutex_lock(&mutexes[n][0]);
                        strcpy(s->direction, "w-");
                        pthread_mutex_unlock(&mutexes[n][0]);
                    }
                }
                else
                {
                    if (pointer->coordinates[0] > s->xPos)
                    {
                        pthread_mutex_lock(&mutexes[n][0]);
                        strcpy(s->direction, "l+");
                        pthread_mutex_unlock(&mutexes[n][0]);
                    }
                    else
                    {
                        pthread_mutex_lock(&mutexes[n][0]);
                        strcpy(s->direction, "l-");
                        pthread_mutex_unlock(&mutexes[n][0]);
                    }
                }

                // new position

                pthread_mutex_lock(&mutexes[n][0]);
                s->xPos = pointer->coordinates[0];
                s->yPos = pointer->coordinates[1];
                pthread_mutex_unlock(&mutexes[n][0]);
            }
            else
            {
                pthread_mutex_lock(&mutexes[n][0]);
                s->speed = 0;
                pthread_mutex_unlock(&mutexes[n][0]);
            }
        }
        if (pointer->next != NULL)
            pointer = pointer->next;
        else
            return NULL;

        if (s->speed == 2)
            usleep(500000);
        else if (s->speed == 1)
            sleep(1);
    }
    return NULL;
}

void *batteryManagement(void *arg)
{
    int n = *((int *)arg);
    char shm_name[10];
    sprintf(shm_name, "%s%d", SHM_AGV, n);
    shm_unlink(shm_name);
    int fd = shm_open(shm_name, O_RDWR, S_IRUSR | S_IWUSR);
    shared_mem *s;
    s = (shared_mem *)mmap(NULL, sizeof(shared_mem), PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    pthread_mutex_init(&mutexes[n][0], NULL);

    if (s->speed == 0)
    {
        pthread_mutex_lock(&mutexes[n][0]);
        s->batterySOC -= 0.1;
        pthread_mutex_unlock(&mutexes[n][0]);
    }
    else if (s->speed == 1 && s->sqr_travelled >= 20)
    {
        pthread_mutex_lock(&mutexes[n][0]);
        s->batterySOC -= 1;
        s->sqr_travelled = s->sqr_travelled - 20;
        pthread_mutex_unlock(&mutexes[n][0]);
    }
    else if (s->speed == 1 && s->sqr_travelled >= 20)
    {
        pthread_mutex_lock(&mutexes[n][0]);
        s->batterySOC -= 0.5;
        s->sqr_travelled = s->sqr_travelled - 20;
        pthread_mutex_unlock(&mutexes[n][0]);
    }

    sleep(5);
    pthread_exit(NULL);

}

void *agvSensors(void *arg)
{
    char direction[2];

    char up[2] = "w-";
    char down[2] = "w+";
    char left[2] = "l-";
    char right[2] = "l+";

    int agvPositionUP = 0;
    int agvPositionRight = 2;
    int agvPositionDown = 4;
    int agvPositionLeft = 6;

    int n = *((int *)arg);
    char shm_name[10];
    sprintf(shm_name, "%s%d", SHM_AGV, n);
    shm_unlink(shm_name);
    int fd = shm_open(shm_name, O_RDWR, S_IRUSR | S_IWUSR);
    shared_mem *s;
    s = (shared_mem *)mmap(NULL, sizeof(shared_mem), PROT_READ | PROT_WRITE,
                           MAP_SHARED, fd, 0);

    // while(1){
    // semaforo aqui

    strcpy(direction, s->direction);

    // pthread_mutex_t acessAgvData;
    // pthread_mutex_init(&mutexes[n][0], NULL);

    printf("Direction:%s\n", direction);
    int stop = 0;
    int slow = 0;
    if (strcmp(direction, up))
    {
        printf("Going up!\n");
        if (s->sensors[agvPositionUP] == 1)
        {
            pthread_mutex_lock(&mutexes[n][0]);
            s->sensorStatus = 1;
            pthread_mutex_unlock(&mutexes[n][0]);
            stop = 1;
        }
    }
    if (strcmp(direction, down))
    {
        printf("Going down!\n");
        if (s->sensors[agvPositionDown] == 1)
        {
            pthread_mutex_lock(&mutexes[n][0]);
            s->sensorStatus = 1;
            pthread_mutex_unlock(&mutexes[n][0]);
            stop = 1;
        }
    }

    if (strcmp(direction, left))
    {
        printf("Going left!\n");
        if (s->sensors[agvPositionLeft] == 1)
        {
            pthread_mutex_lock(&mutexes[n][0]);
            s->sensorStatus = 1;
            pthread_mutex_unlock(&mutexes[n][0]);
            stop = 1;
        }
    }
    if (strcmp(direction, right))
    {
        if (s->sensors[agvPositionRight] == 1)
        {
            printf("Going right!\n");
            pthread_mutex_lock(&mutexes[n][0]);
            s->sensorStatus = 1;
            pthread_mutex_unlock(&mutexes[n][0]);
            stop = 1;
        }
    }

    if (stop == 0)
    {
        for (int o = 0; o < 8; o++)
        {
            if (s->sensors[o] == 2)
            {

                pthread_mutex_lock(&mutexes[n][0]);
                s->sensorStatus = 2;
                pthread_mutex_unlock(&mutexes[n][0]);
                slow = 1;
                break;
            }
        }
    }
    if (slow == 0 && stop == 0)
    {
        printf("No obstacles\n");
        pthread_mutex_lock(&mutexes[n][0]);
        s->sensorStatus = 3;
        pthread_mutex_unlock(&mutexes[n][0]);
    }
    // libertar semaforo
    // }
    pthread_exit(NULL);

}

void *simEngine(void *mem)
{
	printf("ckcsodpkodksdAJIAJAIJ\n");
    sim_engine *shr_mem = mem;
    pthread_mutex_init(&acessWarehouse, NULL);
    int mat[MATRIX_WIDTH][MATRIX_LENGTH];
    memcpy(mat, shr_mem->warehouse, sizeof(shr_mem->warehouse));

    sem = sem_open("semafloww", O_CREAT);

    for (int i = 0; i < MATRIX_LENGTH; i++)
    {
        for (int j = 0; j < MATRIX_WIDTH; j++)
        {
            if (mat[i][j] != 0 && mat[i][j] != 7 && mat[i][j] != 8)
            {
				sem_wait(sem);
                char shm_name[10];
                if(mat[i][j] > 0 && mat[i][j] <= 6){
				int pp = mat[i][j];
				printf("cuckold%d\n",pp);
                sprintf(shm_name, "%s%d", SHM_AGV, mat[i][j]);
                shm_unlink(shm_name);
                int fd = shm_open(shm_name, O_RDWR, S_IRUSR | S_IWUSR);
                if(fd < 0)
                    perror("ERROR: ");
                shared_mem *s;
                s = (shared_mem *)mmap(NULL, sizeof(shared_mem), PROT_READ | PROT_WRITE,
                                       MAP_SHARED, fd, 0);

                s->xPos = i;
                s->yPos = j;
                close(fd);
                shm_unlink(shm_name);}
            }
        }
    }


    pthread_t threads[6];
    int y = 0;
    for (y = 0; y < 6; y++)
    {
        pthread_create(&threads[y], NULL, agvThread, (void *)&y);
    }
    pthread_exit(NULL);

}

int isCornered(int x, int y, int i)
{
    if (x == 0 && y == 0)
        return 6;
    if (x == 0 && y == MATRIX_WIDTH)
        return 7;
    if (x == MATRIX_LENGTH && y == 0)
        return 8;
    if (x == MATRIX_LENGTH && y == MATRIX_WIDTH)
        return 9;
    if ((y + 1) >= MATRIX_WIDTH)
        return 1;
    if (x + 1 >= MATRIX_LENGTH)
        return 2;
    if (y - 1 >= MATRIX_WIDTH)
        return 3;
    if (x - 1 >= MATRIX_LENGTH)
        return 4;
    if ((x < MATRIX_LENGTH - 1) && (y < MATRIX_WIDTH - 1))
        return 0;
    return 0;
}

void *agvThread(void *arg)
{
    int i = *((int *)arg);
    char shm_name[10];
    int z = i + 1;
    sprintf(shm_name, "%s%d", SHM_AGV, z);
    shm_unlink(shm_name);
    int fd = shm_open(shm_name, O_RDWR, S_IRUSR | S_IWUSR);
    shared_mem *s;
    s = (shared_mem *)mmap(NULL, sizeof(shared_mem), PROT_READ | PROT_WRITE,
                           MAP_SHARED, fd, 0);
    shm_unlink("sim_engine");
    int fd1 = shm_open("sim_engine", O_RDWR, S_IRUSR | S_IWUSR);
    sim_engine *sim;
    sim = (sim_engine *)mmap(NULL, sizeof(sim_engine), PROT_READ | PROT_WRITE,
                             MAP_SHARED, fd1, 0);
    while (1)
    {
        pthread_mutex_lock(&(mutexes[i][0]));
        int xPosition = s->xPos;
        int yPosition = s->yPos;
        int lastPosx = sim->xLastPos[i];
        int lastPosy = sim->yLastPos[i];
        if (!(xPosition == lastPosx && yPosition == lastPosy))
        {
            pthread_mutex_lock(&acessWarehouse);
            sim->warehouse[lastPosx][lastPosy] = 0;
            sim->warehouse[xPosition][yPosition] = i + 1;
            pthread_mutex_unlock(&acessWarehouse);
        }
        pthread_mutex_unlock(&(mutexes[i][0]));

        int mat[MATRIX_WIDTH][MATRIX_LENGTH];
        memcpy(mat, s->warehouse, MATRIX_LENGTH * MATRIX_WIDTH * sizeof(int));
        int pos = isCornered(xPosition, yPosition, i);

        if (pos == 0)
        { // no cornered
            obstacleCheck(1, 8, xPosition, yPosition, 0, mat, i);
        }

        if (pos == 1)
        { // cornered top side
            obstacleCheck(3, 7, xPosition, yPosition, 1, mat, i);
        }

        if (pos == 2)
        { // cornered right side
            obstacleCheck(5, 8, xPosition, yPosition, 2, mat, i);
        }
        if (pos == 3)
        { // cornered bottom side
            obstacleCheck(5, 8, xPosition, yPosition, 3, mat, i);
        }
        if (pos == 4)
        { // cornered left side
            obstacleCheck(1, 5, xPosition, yPosition, 4, mat, i);
        }
        if (pos == 6)
        { // cornered lower left corner
            obstacleCheck(1, 3, xPosition, yPosition, 0, mat, i);
        }
        if (pos == 8)
        { // cornered lower right corner
            obstacleCheck(7, 8, xPosition, yPosition, 0, mat, i);
        }
        if (pos == 7)
        { // cornered upper left corner
            obstacleCheck(3, 5, xPosition, yPosition, 0, mat, i);
        }
        if (pos == 9)
        { // cornered upper right corner
            obstacleCheck(5, 7, xPosition, yPosition, 0, mat, i);
        }
        usleep(100000);
    }
}

void obstacleCheck(int min, int max, int x, int y, int type, int mat[MATRIX_WIDTH][MATRIX_LENGTH], int is)
{
    char shm_name[10];
    sprintf(shm_name, "%s%d", SHM_AGV, is);
    shm_unlink(shm_name);
    int fd = shm_open(shm_name, O_RDWR, S_IRUSR | S_IWUSR);
    shared_mem *s;
    s = (shared_mem *)mmap(NULL, sizeof(shared_mem), PROT_READ | PROT_WRITE,
                           MAP_SHARED, fd, 0);

    if (type == 2)
    {
        if (mat[x][y + 1] != 0)
            s->sensors[0] = 1;
        else if (mat[x][y + 2] != 0)
            s->sensors[0] = 2;
        else
            s->sensors[0] = 3;
        for (int i = min; i <= max; i++)
        {
            if (i == 5)
            {
                if (mat[x][y - 1] != 0)
                    s->sensors[i - 1] = 1;
                else if (mat[x][y - 2] != 0)
                    s->sensors[i - 1] = 2;
                else
                    s->sensors[i - 1] = 3;
            }
            if (i == 6)
            {
                if (mat[x - 1][y - 1] != 0)
                    s->sensors[i - 1] = 1;
                else if (mat[x - 1][y - 2] != 0 || mat[x - 2][y - 2] != 0 || mat[x - 2][y - 1] != 0)
                    s->sensors[i - 1] = 2;
                else
                    s->sensors[i - 1] = 3;
            }
            if (i == 7)
            {
                if (mat[x - 1][y] != 0)
                    s->sensors[i - 1] = 1;
                else if (mat[x - 2][y] != 0)
                    s->sensors[i - 1] = 2;
                else
                    s->sensors[i - 1] = 3;
            }
            if (i == 8)
            {
                if (mat[x - 1][y + 1] != 0)
                    s->sensors[i] = 1;
                else if (mat[x - 1][y + 2] != 0 || mat[x - 2][y + 2] != 0 || mat[x - 2][y + 1] != 0)
                    s->sensors[i] = 2;
                else
                    s->sensors[i] = 3;
            }
        }
        s->sensors[1] = 1;
        s->sensors[2] = 1;
        s->sensors[3] = 1;
    }
    else if (type == 3)
    {
        if (mat[x][y + 1] != 0)
            s->sensors[0] = 1;
        else if (mat[x][y + 2] != 0)
            s->sensors[0] = 2;
        else
            s->sensors[0] = 3;

        if (mat[x + 1][y + 1] != 0)
            s->sensors[1] = 1;
        else if (mat[x + 1][y + 2] != 0 || mat[x + 2][y + 2] != 0 || mat[x + 2][y + 1] != 0)
            s->sensors[1] = 2;
        else
            s->sensors[1] = 3;

        if (mat[x + 1][y] != 0)
            s->sensors[2] = 1;
        else if (mat[x + 2][y] != 0)
            s->sensors[2] = 2;
        else
            s->sensors[2] = 3;

        if (mat[x - 1][y] != 0)
            s->sensors[6] = 1;
        else if (mat[x - 2][y] != 0)
            s->sensors[6] = 2;
        else
            s->sensors[6] = 3;

        if (mat[x - 1][y + 1] != 0)
            s->sensors[7] = 1;
        else if (mat[x - 1][y + 2] != 0 || mat[x - 2][y + 2] != 0 || mat[x - 2][y + 1] != 0)
            s->sensors[7] = 2;
        else
            s->sensors[7] = 3;

        s->sensors[3] = 1;
        s->sensors[4] = 1;
        s->sensors[5] = 1;
    }
    else
    {

        for (int i = min; i <= max; i++)
        {
            if (i == 1)
            {
                if (mat[x][y + 1] != 0)
                    s->sensors[i - 1] = 1;
                else if (mat[x][y + 2] != 0)
                    s->sensors[i - 1] = 2;
                else
                    s->sensors[i - 1] = 3;
            }
            if (i == 2)
            {
                if (mat[x + 1][y + 1] != 0)
                    s->sensors[i - 1] = 1;
                else if (mat[x + 1][y + 2] != 0 || mat[x + 2][y + 2] != 0 || mat[x + 2][y + 1] != 0)
                    s->sensors[i - 1] = 2;
                else
                    s->sensors[i - 1] = 3;
            }
            if (i == 3)
            {
                if (mat[x + 1][y] != 0)
                    s->sensors[i - 1] = 1;
                else if (mat[x + 2][y] != 0)
                    s->sensors[i - 1] = 2;
                else
                    s->sensors[i - 1] = 3;
            }
            if (i == 4)
            {
                if (mat[x + 1][y - 1] != 0)
                    s->sensors[i - 1] = 1;
                else if (mat[x + 2][y - 1] != 0 || mat[x + 2][y - 2] != 0 || mat[x + 1][y - 2] != 0)
                    s->sensors[i - 1] = 2;
                else
                    s->sensors[i - 1] = 3;
            }
            if (i == 5)
            {
                if (mat[x][y - 1] != 0)
                    s->sensors[i - 1] = 1;
                else if (mat[x][y - 2] != 0)
                    s->sensors[i - 1] = 2;
                else
                    s->sensors[i - 1] = 3;
            }
            if (i == 6)
            {
                if (mat[x - 1][y - 1] != 0)
                    s->sensors[i - 1] = 1;
                else if (mat[x - 1][y - 2] != 0 || mat[x - 2][y - 2] != 0 || mat[x - 2][y - 1] != 0)
                    s->sensors[i - 1] = 2;
                else
                    s->sensors[i - 1] = 3;
            }
            if (i == 7)
            {
                if (mat[x - 1][y] != 0)
                    s->sensors[i - 1] = 1;
                else if (mat[x - 2][y] != 0)
                    s->sensors[i - 1] = 2;
                else
                    s->sensors[i - 1] = 3;
            }
            if (i == 8)
            {
                if (mat[x - 1][y + 1] != 0)
                    s->sensors[i - 1] = 1;
                else if (mat[x - 1][y + 2] != 0 || mat[x - 2][y + 2] != 0 || mat[x - 2][y + 1] != 0)
                    s->sensors[i - 1] = 2;
                else
                    s->sensors[i - 1] = 3;
            }
        }
    }
}

void writeComms(int xpos, int ypos, float bat, int status)
{
}