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

int main(){
	int offset = 0;


	char * strings[] =
	{
		"The boy walked towards the girl",
		"C is a beautiful language",
		"How now brown cow"
	};


	char ** strings2 = strings;


	printf("size : %d\n", sizeof(strings));
	for (; offset < 3; ++offset)
		printf("%s\n", strings2[offset]);


	return 0;
}



