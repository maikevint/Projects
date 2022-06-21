/*
 * 	Name: Kevin Mai
 *	CS 570
 *	Aug 2, 2020
 *	Program 3
 *
 *  bbuffer.h
 *  This header file is for programming #3.  
 */

#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#define BUFFER_SIZE 5   // rounded buffer size

#define PRODUCERS 3  // number of producers
#define CONSUMERS 5  // number of consumers

#define ITERATIONS 60   // number of actions producers/consumers perform 
#define PRODUCER_ITERATIONS (ITERATIONS / PRODUCERS)  //number of actions each producer performs
#define CONSUMER_ITERATIONS (ITERATIONS / CONSUMERS) //number of actions each consumer performs

typedef struct {
    int value[BUFFER_SIZE];	// holds the item values 
    int next_in, next_out;	// points to the next index for insert for next_in and removal for next_out
} buffer_t;    // struct of bounded buffer


buffer_t buffer;   //global variable: rounded buffer

pthread_t consumer_tid[CONSUMERS], producer_tid[PRODUCERS]; // producer and consumer threads


//TODO: You may need to add more global variables here ...
// semaphores for empty, full, and lock conditions
sem_t empty;	
sem_t full;		
sem_t mutex;	

//intialize golbal variables if you need, such as semaphore, mutex
void initilization();

/**
 * consumer - will iterate CONSUMER_ITERATION times and call the corresponding
 * function to remove an integer from the bounded buffer
 * @param param an integer id of the producer used to distinguish between the
 * multiple consumer threads
 * @return nothing
 */
void * consumer(void *param);

/**
 * producer - will iterate PRODUCER_ITERATION times and call the corresponding
 * function to insert an integer to the bounded buffer
 * @param param an integer id of the producer used to distinguish between the
 * multiple producer threads
 * @return nothing
 */
void * producer(void *param);