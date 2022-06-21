/*
	Name: Kevin Mai
	CS 570 
	July 8, 2020
	Program 0
	specialcharcount.c - this file implements the specialcharcount function.
 */

#include <dirent.h> 
#include <stdio.h> 
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "count.h"

 /**
   The specialcharcount function counts the frequency of the following 5 special characters:
   ','   '.'   ':'    ';'    '!'

   in all the .txt files under directory of the given path and write the results to a file named as filetowrite.

   Input:

	   path - a pointer to a char string [a character array] specifying the path of the directory; and
	   filetowrite - a pointer to a char string [a character array] specifying the file where results should be written in.
	   alphabetfreq - a pointer to a long array storing the frequency of the above 5 special characters

	   charfreq[0]: the frequency of ',' 44
	   charfreq[1]: the frequency of '.' 46
	   charfreq[2]: the frequency of ':' 58
	   charfreq[3]: the frequency of ';' 59
	   charfreq[4]: the frequency of '!' 33


   Output: a new file named as filetowrite with the frequency of the above special characters written in

   Steps recommended to finish the function:
   1) Find all the files ending with .txt and store in filelist.
   2) Read all files in the filelist one by one and count the frequency of each alphabet letter only (a - z). The array
   long alphabetfreq[] always has the up-to-date frequencies of alphabet letters counted so far. If the letter is upper case, convert
   it to lower case first.
   3) Write the result in the output file: filetowrite in following format:

	  character -> frequency

	  example:
	  , -> 20
	  . -> 11
	  : -> 20
	  ; -> 11
	  ! -> 12

   Assumption:
   1) You can assume there is no sub-directory under the given path so you don't have to search the files
   recursively.
   2) Only .txt files are counted and other types of files should be ignored.

 */

void specialcharcount(char *path, char *filetowrite, long charfreq[])
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

		// test for the special characters ','   '.'   ':'    ';'    '!' and count it
		while ((ch = getc(f)) != EOF)
		{
			if (ch == ',')
				charfreq[0]++;
			else if (ch == '.')
				charfreq[1]++;
			else if (ch == ':')
				charfreq[2]++;
			else if (ch == ';')
				charfreq[3]++;
			else if (ch == '!')
				charfreq[4]++;
		}

		// close stream to file as the count has been completed
		fclose(f);
	}

	// Print to a new or overrwirte a old specialresult.txt in result folder
	FILE *results = fopen(filetowrite, "w+");

	fprintf(results, ", -> %d\n", charfreq[0]);
	fprintf(results, ". -> %d\n", charfreq[1]);
	fprintf(results, ": -> %d\n", charfreq[2]);
	fprintf(results, "; -> %d\n", charfreq[3]);
	fprintf(results, "! -> %d\n", charfreq[4]);

	// close stream and end of function
	fclose(results);
}


