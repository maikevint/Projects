/*
	Name: Kevin Mai
	CS 570
	July 19, 2020
	Program 1
	mulproc.c - this file forks two children running two programs from proj 0 in parallel
 */

#include <errno.h>     
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

/*
 This program will fork two child processes running the two programs generated in programming zero in parallel.
 Hint: You mant need to use fork(), exec() family, wait(), exit(), getpid() and etc ...
 
 Requirements:
 
 1) Exactly two child processes are created, one to run testspecial program and the other is to run testalphabet program;
 2) When a program starts to run, print a message to the output screen showing which process (with PID) is running which program, for example:
    "CHILD <16741> process is executing testalphabet program!"
    
 3) When a program is done, the process exits and at the same time, a message should be print to the output screen showing which  process (with PID) is done with the program, for example:
    "CHILD <16741> process has done with testalphabet program !"
    
 4) The messages should match the real execution orders, i.e. when the testspecial program starts/ends, the right message should be print out. So you need to figure out how to get the starting/ending time of each 
 process.
   
   
 The expected screen print out should be similar as follows:
 
 CHILD <16741> process is executing testalphabet program!
 CHILD <16742> process is executing testspecial program!
, -> 745668
. -> 798072
... ...

CHILD <16742> process has done with testspecial program !
a -> 2861232
b -> 494472
... ...

CHILD <16741> process has done with testalphabet program !
 */


int main(void) {


	pid_t child_al, child_sp;						// process id type initiation for both children

	char *alpha[] = { "./testalphabet",NULL };		// contains path for testalphabet executable
	char *special[] = { "./testspecial",NULL };		// contains path for testspecial executable
	
	// fork to run testalphabet
	child_al = fork();
	
	// fork failed
	if (child_al < 0) {
		fprintf(stderr, "fork failed, error %d\n", errno);
		exit(EXIT_FAILURE);
	}

	// child alphabettest code
	else if (child_al == 0) {	
		printf("CHILD <%d> process is executing testalphabet program\n", getpid());
		execvp(alpha[0], alpha);		
	}

	// parent code
	else {		
		
		// fork to run testspecial
		child_sp = fork();	
		
		// fork failed
		if (child_sp < 0) {
			fprintf(stderr, "fork failed, error %d\n", errno);
			exit(EXIT_FAILURE);
		}

		// child specialtest code
		else if (child_sp == 0) {
			printf("CHILD <%d> process is executing testspecial program\n", getpid());
			execvp(special[0], special);
		}
		// parent code
		else {			
			wait(1);
			printf("CHILD <%d> process has done with testspecial program! See the results above!\n", child_sp);
			
		}		
		wait(1);		
		printf("CHILD <%d> process has done with testalphabet program! See the results above!\n", child_al);
	}

	// end of program
	return 0;
}
