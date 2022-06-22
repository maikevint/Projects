The Mechanics
Plan is to flag each GridCell that is reachable within distance of origin and then work from the goal towards the origin to choose the available cell with the minimum distance. 
Which should be like a countdown

enqueue cell (0,0) //origin
while( queue is not empty) ){
	dequeue a GridCell from the queue
	mark each adjacent neighboring cell and enqueue it
	}
results in every valid cell being marked

distance = goal cell distance from origin
if (distance == - 1) return false // unreachable,puzzle ahas no solution
push terminal cell onto stack
while (distance != 0) {
	get distance from each cell adjacent to the top of the stack
	select the cell with smallest distance and push on the stack
	}
while (stack is not empty){
	pop grid cell off the stack and mark it
}
shows the shortest path from origin to goal


Run MazeSolver.java

Buttons
-Reset Clears the grid and creates a new puzzle.
-Mark Slowly Marks each available (and reachable) cell with its distance to the starting cell. There is a delay so that you can see the workings of the breadth first algorithm. 
The cells being flagged temporarily turn red.
-Stop Halts the breadth first search cell flagging operation. Only useful for stopping a "Mark Slowly" operation. Just stops, does not reset the puzzle. 
This is so you can examine the actions that have occurred. You cannot restart the operation, but must manually reset before continuing.
-Mark Runs the breadth first algorithm, and marks the distance from the origin in each reachable cell.
-Show Shortest Path Turns the background color of the cells in the shortest path to a light blue color. (must use mark first)

