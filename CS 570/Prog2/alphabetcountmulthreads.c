/*
	Name: Kevin Mai
	CS 570
	July 26, 2020
	Program 2
	alphabetcountmulthreads.c - this file counts the frequency of each alphabet letter (a-z, case sensitive) in all the .txt files under
	directory of the given path and write the results to a file named as filetowrite with the implementation of multithreading.
 */

#include <ctype.h>
#include <errno.h>   
#include <dirent.h> 
#include <pthread.h>
#include <string.h>
#include <stdbool.h> 
#include <stdio.h> 
#include <stdlib.h>
#include <unistd.h> 

#include "count.h"


// global variable declarations 
typedef struct
{
	char filelist[MAXFILES][30];  // pass on file list data to threads

	int thdtotal;				// number of threads
	int extra;					// 0 for even distribution, 1 for uneven distribution over threads
	int thdnumfile;				// number of files per thread
	int filetotal;				// total number of files in file list

} FILEDATA;
FILEDATA threaddata;			// common data shared between threads

long *alphabfreq;				// to update the long alphabetfreq[ ] 

pthread_t tid[50];				// contains the thread ids 
pthread_mutex_t mutexsum;		// for the mutex usage

/**
  The alphabetcountmulthreads function counts the frequency of each alphabet letter (a-z, case insensitive) in all the .txt files under
  directory of the given path and write the results to a file named as filetowrite. Different with programming assignment#0, you need to implement
  the program using mutithreading.
  
  Input: 
      path - a pointer to a char string [a character array] specifying the path of the directory; and
      filetowrite - a pointer to a char string [a character array] specifying the file where results should be written in.
      alphabetfreq - a pointer to a long array storing the frequency of each alphabet letter from a - z, which should be already up-to-date;
      num_threads - number of the threads running in parallel to process the files
      
       
  Output: a new file named as filetowrite with the frequency of each alphabet letter written in
  
  Requirements:
1)	Multiple threads are expected to run in parallel to share the workload, i.e. suppose 3 threads to process 30 files, then each thread should 
process 10 files;
2)	When a thread is created, a message should be print out showing which files this thread will process, for example:
Thread id = 274237184 starts processing files with index from 0 to 10!
3)	When a file is being processed, a message should be print out showing which thread (thread_id = xxx) is processing this file, for example:
Thread id = 265844480 is processing file input_11.txt
4)	When a thread is done with its workload, a message should be print out showing which files this thread has done with work, for example:
      Thread id = 274237184 is done !
5)	The array: long alphabetfreq[ ] should always be up-to-date, i.e. it always has the result of all the threads counted so far.  [You may need 
to use mutexes to protect this critical region.]


You should have the screen printing should be similar as follows:

 Thread id = 274237184 starts processing files with index from 0 to 10!
 Thread id = 265844480 starts processing files with index from 11 to 22!
 Thread id = 257451776 starts processing files with index from 23 to 31!

 Thread id = 265844480 is processing file input_11.txt
 Thread id = 257451776 is processing file input_22.txt
 Thread id = 274237184 is processing file input_00.txt
  ??

 Thread id = 274237184 is done !
 Thread id = 265844480 is done !
 Thread id = 257451776 is done !

 The results are counted as follows:
 a -> 2861232
 b -> 494472
 c -> 747252
 ? ?
*/

void *alphabetcount(void *arg) {
	int ch, j, start, end; 					// ch holds the character in the file, j is a placeholder,
											// start and end dictate the length of the thread reading files
	long offset;							// thread number
	long mycount[ALPHABETSIZE] = { 0 };     // local version of long alphabetfreq[ ] 

	offset = (long)arg;

	// find where the thread has to read the file list 
	start = offset * threaddata.thdnumfile;
	end = start + threaddata.thdnumfile;

	// case of uneven distribution over threads, last thread gets leftovers.
	if (threaddata.extra == 1 && offset == threaddata.thdtotal - 1)
	{
		end = threaddata.filetotal;
	}

	printf("Thread id = %ld starts processing files with index frome %d to %d\n", tid[offset], start, end - 1);

	// for each file in the file list count the characters
	for (j = start; j < end; j++)
	{
		
		printf("Thread id = %ld is processing file %s\n", tid[offset], threaddata.filelist[j]);
		
		// append the file name to the proper file path in order to open the stream
		char filename[40] = "../data/";

		strcat(filename, threaddata.filelist[j]);
		
		FILE *f = fopen(filename, "r");

		// test for alpha characters for upper or lower case and count it
		while ((ch = getc(f)) != EOF)
		{
			if (!isalpha(ch))
				continue;
			else if (isupper(ch))
			{
				mycount[ch - 65]++;
			}
			else
			{
				mycount[ch - 71]++;
			}
		}

		// close stream to file as the count has been completed
		fclose(f);
	}

	// mutex lock to access to global variable
	pthread_mutex_lock(&mutexsum);
	// update long alphabetfreq[ ]
	for (j = 0; j < ALPHABETSIZE; j++) {
		alphabfreq[j] += mycount[j];
	}
	pthread_mutex_unlock(&mutexsum);

	
	printf("Thread id = %ld is done !\n", tid[offset]);
	pthread_exit((void*)0);
	// end of thread
}

void alphabetcountmulthreads(char *path, char *filetowrite, long alphabetfreq[], int num_threads)
{
	int numfile = 0;				// keeps track of files in data folder
	long i = 0;
	threaddata.extra = 0;
	struct dirent *dirpath;

	pthread_attr_t attr;

	DIR *dir = opendir(path);

	// make a list of qualifying files to read
	while (dirpath = readdir(dir))
	{
		// store the file name only if it has the .txt extension
		size_t len = strlen(dirpath->d_name);
		if (len > 4 && strcmp(dirpath->d_name + len - 4, ".txt") == 0) {
			strcat(threaddata.filelist[numfile++], dirpath->d_name);
		}
	}

	// close directory path as all qualifying files are found
	closedir(dir);

	// ensure threads have access to inputted data
	threaddata.thdtotal = num_threads;
	threaddata.filetotal = numfile;
	threaddata.thdnumfile = numfile / num_threads;
	if (numfile % num_threads > 0)
		threaddata.extra = 1;
	alphabfreq = alphabetfreq;
	pthread_mutex_init(&mutexsum, NULL);


	// Create threads to perform the alphabetcount
	pthread_attr_init(&attr);
	pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_JOINABLE);
	for (i = 0; i < num_threads; i++) {
		pthread_create(&tid[i], &attr, alphabetcount, (void *)i);
		//printf("Thread id = %ld starts processing files with index frome %d to %d\n", tid[i], i * threaddata.thdnumfile, i * threaddata.thdnumfile + threaddata.thdnumfile - 1);
	}

	pthread_attr_destroy(&attr);

	/* Wait on the other threads */
	for (i = 0; i < num_threads; i++)
	{
		pthread_join(tid[i], NULL);
	}

	// Print to a new or overrwirte a old results.txt in result folder
	FILE *results = fopen(filetowrite, "w+");

	for (i = 0; i < (ALPHABETSIZE / 2); i++)
	{
		fprintf(results, "%c -> %d\n", i + 65, alphabetfreq[i]);
	}
	for (i = 26; i < (ALPHABETSIZE); i++)
	{
		fprintf(results, "%c -> %d\n", i + 71, alphabetfreq[i]);
	}

	// close stream and end of function
	fclose(results);
	pthread_mutex_destroy(&mutexsum);
}
