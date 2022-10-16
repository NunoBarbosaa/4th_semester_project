#if !defined(_HEADER_H)
#define _HEADER_H

#include <pthread.h>

#define MATRIX_WIDTH 18
#define MATRIX_LENGTH 20

extern pthread_mutex_t mutexes[6][2];
extern pthread_mutex_t acessWarehouse;
extern pthread_mutex_t create_new_route;
extern pthread_cond_t create_new_route_cond;
extern int warehouse_width;
extern int warehouse_length;

typedef struct{
	int begin[2]; //x and y 
	int end[2]; // x and y 
	int depth[2]; 
	char access[2]; //Direction which the aisle is accessed Check json File for syntax

} aisle_t;

typedef struct route_t
{
    int coordinates[2];
    struct route_t *next;
} route_t;

typedef struct{
	int begin[2];  // x and y 
	int end[2]; // x and y 
	int depth[2];
	char access[2]; //Direction which the aisle is accessed Check jsonFile for syntax

} dock_t;

typedef struct{
	int xPos;
	int yPos;
	char direction[2]; //Direction check json file
	int speed; //Squares per second --> 2 by default
	int sqr_travelled;// Square travelled (For BMS)
	float batterySOC;	 
	int sensors[8];
	int status;
	int go_status;
	int sensorStatus;
	int is_to_calculate_route;
	int route_end[2];
	int *warehouse;
	route_t route; 

} shared_mem;

typedef struct{
	int length; //Number of squares length
	int width; //Number of squares width
	float square; //Size of square in meters
	int aisleCount; 
	int dockCount;
	aisle_t *aisles; //Position of aisles
	dock_t*docks; //Position of docks

} warehouse;

typedef struct{
    int xLastPos[6];
    int yLastPos[6];
    int warehouse[MATRIX_WIDTH][MATRIX_LENGTH];
} sim_engine;

#endif // _HEADER_H
