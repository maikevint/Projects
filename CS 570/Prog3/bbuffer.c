/*
 * 	Name: Kevin Mai
 *	CS 570
 *	Aug 2, 2020
 *	Program 3
 *
 *  bbuffer.c
 *  The code is not part of the real application, and just used to 
 *  illustrate the bounded-buffer problem using Semaphore and/or mutexes. 
 *  Detailed requirements please refer to the assignment documentation.
 * 
 */

#include <stdio.h> 
#include <stdlib.h> 
#include "bbuffer.h"

void initilization()
{
    /* TODO: intialize golbal variables if you need, such as semaphore, mutex and etc. Leave it empyty if you
     * do not need it.
     */     
	sem_init(&empty, 0, BUFFER_SIZE);   // empty indicator
	sem_init(&full, 0, 0);				// full indicator
	sem_init(&mutex, 0, 1);				// mutex lock
}

/* *
 * insert_item - thread safe(?) function to insert items to the bounded buffer
 * @param item the value to be inserted
 * @return 0 in case of sucess -1 otherwise
 */
  
int insert_item(int item, long int id)
{
    /* TODO: Check and wait if the buffer is full. Ensure exclusive
     * access to the buffer and may use the existing code to insert an item.
     */   
	
	sem_wait(&empty);		// check if buffer is full when sem empty is at 0
	sem_wait(&mutex);		// ensure exclusive access to global variables


    buffer.value[buffer.next_in] = item;
    
      
    printf("producer %ld: inserted item %d into buffer index %d\n", id, item, buffer.next_in);
       
    buffer.next_in = (buffer.next_in + 1) % BUFFER_SIZE;

	sem_post(&mutex);		// finished with exclusive access
	sem_post(&full);		// indicates buffer has filled an index
	
    return 0;
}

/**
 * remove_item - thread safe(?) function to remove items to the bounded buffer
 * @param item the address of the variable that the removed value will be written
 * @return 0 in case of sucess -1 otherwise
 */
int remove_item(int *item, long int id)
{
    /* TODO: Check and wait if the buffer is empty. Ensure exclusive
     * access to the buffer and use the existing code to remove an item.
     */
	
	sem_wait(&full);	// check if buffer is empty when sem full is at 0
	sem_wait(&mutex);	// ensure exclusive access to global variables

    *item = buffer.value[buffer.next_out];
    buffer.value[buffer.next_out] = -1;    
    
   
    printf("consumer %ld: removed item %d from buffer index %d\n", id, *item, buffer.next_out);
       
    buffer.next_out = (buffer.next_out + 1) % BUFFER_SIZE;

	sem_post(&mutex);	 // finished with exclusive access
	sem_post(&empty);    // indicates buffer has a "empty" spot
	
    return 0;
}

/**
 * producer - will iterate PRODUCER_ITERATION times and call the corresponding
 * function to insert an integer to the bounded buffer
 * @param param an integer id of the producer used to distinguish between the
 * multiple producer threads
 * @return nothing
 */
void * producer(void *param)
{
    int item, i;
    long int id = (long int)param;

    printf("producer %d started!\n", id);
    i = PRODUCER_ITERATIONS;
    while (i--) {
	  sleep(rand() % 3);

	item = rand() % 10000;
	if (insert_item(item, id))
	    printf("Error while inserting to buffer\n");
    }

    pthread_exit(0);
}

/**
 * consumer - will iterate CONSUMER_ITERATION times and call the corresponding
 * function to remove an integer from the bounded buffer
 * @param param an integer id of the producer used to distinguish between the
 * multiple consumer threads
 * @return nothing
 */
void * consumer(void *param)
{
    int item, i;
    long int id = (long int)param;

    printf("consumer %d started!\n", id);
    i = CONSUMER_ITERATIONS;
    while (i--) {
	sleep(rand() % 3);

	if (remove_item(&item, id))
	    printf("Error while removing from buffer\n");
    }

    pthread_exit(0);
}
