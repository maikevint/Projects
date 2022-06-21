/*
 * 	Name: Kevin Mai
 *	CS 570
 *	Aug 9, 2020
 *	Program 4
 *  Implementation of FIFO and LRU page replacement algorithm
 *  Please add appropriate level of comments in this file 
 */

#include "vmalgorithm.h"


/* Generate an access pattern
 * Example: 3, 2, 2, 1, 1  indicates the page 3, 2, 2, 1, 1 in order
 */
void generateAccessPattern()
{
   int i;
   srand(time(0));
   accessPattern = (int *)malloc( sizeof(int) * AccessPatternLength);   
   printf("The randomized Access Pattern: ");
   for(i=0; i< AccessPatternLength; i++)
   {
		     accessPattern[i] = rand() % ReferenceSZ;
        printf("%d ", accessPattern[i]);       
   }
   printf("\n");
}

/*
 * Initialize the parameters of simulated memory
 */
void initializePageFrame()
{
   int i;
   memory.PageFrameList = (int *)malloc( sizeof(int)* FrameNR );    // dynamic allocated FrameNR frames to be used in memory
   memory.nextToReplaced =0;          // indicate the new frame to be replaced as 0
   for(i=0; i< FrameNR; i++)
   {
			memory.PageFrameList[i] = -1;  // initialization to -1 indicating all frames are unused 
   }

}

// Print the pages loaded in memory
void printPageFrame()
{
   int i;
   for(i=0; i< FrameNR; i++)
   {
			printf("%2d ",memory.PageFrameList[i]);       
   }
   printf("\n");
}


/*
 *  Print the access pattern in order
 */
void printAccessPattern()
{
   int i;
   printf("The Same Access Pattern: ");
   for(i=0; i< AccessPatternLength; i++)
   {
        printf("%d ", accessPattern[i]);       
   }
   printf("\n");

}

/*
 * check to see if the page frame is stored in memory
 * return 1 for found, -1 for not found. 
 */
int findPage(int pg) 
{
	for (j = 0; j < FrameNR; j++) {
		if (memory.PageFrameList[j] == pg) 
		{
			return 1;
		}
	}
	return -1;
}

/*
 * Return: number of the page fault using FIFO page replacement algorithm
 */
int FIFO()
{
	int count, found = 0;   // count is for page fault count, found for if the page frame exists in memory.

	// page replacement 
	for (int i = 0; i < AccessPatternLength; i++) 
	{
		// check if page frame is in memory
		found = findPage(accessPattern[i]);
		
		// page exists
		if (found == 1) {
			printPageFrame();
			continue;
		}

		// page does not exist in memory, do swap with FIFO.
		count++;		

		// remove first in first out
		memory.PageFrameList[memory.nextToReplaced++] = accessPattern[i];
		memory.nextToReplaced %= FrameNR;
		
		printPageFrame();
	 }  
	return count;
}



/*
 * Return: number of the page fault using LRU page replacement algorithm
 */
int LRU()
{
	int count, found, clock = 0;	// count is for page fault count, found for if the page frame exists in memory, clock for logical clock
	int lru[FrameNR];				// holds the logical clock for the memory list

	// initialize lru with -1 for each index. 
	for (int n = 0; n < FrameNR; n++) {
		lru[n] = -1;
	}

	// page replacement 
	for (int i = 0; i < AccessPatternLength; i++)
	{
		clock++;
		// check if page frame is in memory
		found = findPage(accessPattern[i]);

		// page exists
		if (found == 1)
		{
			lru[j] = clock;
			printPageFrame();
			continue;			
		}

		// page does not exist in memory
		count++;

		int min = lru[0];			
		memory.nextToReplaced = 0;

		// find least recently used page frame
		for (int k = 1; k < FrameNR; k++) {
			if (lru[k] < min)
			{
				min = lru[k];
				memory.nextToReplaced = k;
			}
		}

		// update logical clock and remove least recently used element
		lru[memory.nextToReplaced] = clock;
		memory.PageFrameList[memory.nextToReplaced] = accessPattern[i];

		printPageFrame();		
	}
	return count;
}

