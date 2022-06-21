/*
	Name: Kevin Mai
	CS 570 
	July 8, 2020
	Program 0
	alphabetcount.c - this file implements the alphabetlettercount function.
 */

#include <ctype.h>
#include <dirent.h> 
#include <stdio.h> 
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "count.h"

 /**
   The alphabetlettercount function counts the frequency of each alphabet letter (A-Z a-z, case sensitive) in all the .txt files under
   directory of the given path and write the results to a file named as filetowrite.

   Input:
	   path - a pointer to a char string [a character array] specifying the path of the directory; and
	   filetowrite - a pointer to a char string [a character array] specifying the file where results should be written in.
	   alphabetfreq - a pointer to a long array storing the frequency of each alphabet letter from A - Z a - z:
	   alphabetfreq[0]: the frequency of 'A' 65 - 65
	   alphabetfreq[1]: the frequency of 'B'
		  ... ...
	   alphabetfreq[25]:the frequency of 'Z' 90 - 65
	   alphabetfreq[26]:the frequency of 'a' 97 - 71
		  ...  ...
	   alphabetfreq[51]:the frequency of 'z' 122 - 71

   Output: a new file named as filetowrite with the frequency of each alphabet letter written in

   Steps recommended to finish the function:
   1) Find all the files ending with .txt and store in filelist.
   2) Read all files in the filelist one by one and count the frequency of each alphabet letter only (A-Z a - z). The array
   long alphabetfreq[] always has the up-to-date frequencies of alphabet letters counted so far.
   3) Write the result in the output file: filetowrite in following format:

	  letter -> frequency

	  example:
	  A -> 200
	  B -> 101
	  ... ...

   Assumption:
   1) You can assume there is no sub-directory under the given path so you don't have to search the files
   recursively.
   2) Only .txt files are counted and other types of files should be ignored.

 */

void alphabetlettercount(char *path, char *filetowrite, long alphabetfreq[])
{
	int ch;							// holds the character in the file
	int numfile = 0;				// keeps track of files in data folder

	char filelist[MAXFILES][30];	// keeps track of the files to be tested

	// open directory to data where the files are kept
	struct dirent *dirpath;			
	DIR *dir = opendir(path);

	// Directory found
	while (dirpath = readdir(dir))
	{
		// store the file name only if it has the .txt extension
		size_t len = strlen(dirpath->d_name);
		if (len > 4 && strcmp(dirpath->d_name + len - 4, ".txt") == 0) {
			strcat(filelist[numfile++], dirpath->d_name);
		}
	}	

	// close directory path as all qualifying files are found
	closedir(dir);

	// for each file in the file list count the characters
	for (int i = 0; i < numfile + 1; i++) 
	{
		// append the file name to the proper file path in order to open the stream
		char filename[40] = "../data/";

		strcat(filename, filelist[i]);

		FILE *f = fopen(filename, "r");

		// test for alpha characters for upper or lower case and count it
		while ((ch = getc(f)) != EOF)
		{
			if (!isalpha(ch))
				continue;
			else if (isupper(ch))
			{
				alphabetfreq[ch - 65]++;
			}
			else
			{
				alphabetfreq[ch - 71]++;
			}

		}

		// close stream to file as the count has been completed
		fclose(f);
	}

	// Print to a new or overrwirte a old results.txt in result folder
	FILE *results = fopen(filetowrite, "w+");

	for (int j = 0; j < (ALPHABETSIZE/2); j++) 
	{	
		fprintf(results, "%c -> %d\n", j+65, alphabetfreq[j]);
	}
	for (int j = 26; j < (ALPHABETSIZE); j++)
	{
		fprintf(results, "%c -> %d\n", j+71, alphabetfreq[j]);
	}

	// close stream and end of function
	fclose(results);
}



