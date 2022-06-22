/*
 *  Program 3
 *  Kevin Mai
 *  cssc0922
*/

import data_structures.*;

public class MazeSolver {
	private MazeGrid grid;
	private Queue<GridCell> queue;
	private Stack<GridCell> stack;
	private int dimension;

	// The constructor. Takes a single argument, the number of rows and columns
	// in the grid. Suggested values are 25 .. 50.
	public MazeSolver(int dimension) {
		grid = new MazeGrid(this, dimension);
		queue = new Queue<GridCell>();
		stack = new Stack<GridCell>();
		this.dimension = dimension;
	}

	/*
	 * This method runs the breadth first traversal, and marks each reachable
	 * cell in the grid with its distance from the origin. Solving the puzzle is
	 * a two step process. First, you must flag each GridCell that is reachable
	 * with its distance from the origin. Then starting at the exit point and
	 * working back toward the origin, you select the available cell with the
	 * minimum distance. For the first part, you will use a breadth first
	 * traversal algorithm to visit and mark each cell: 
	 * 
	 * enqueue cell(0,0) 
	 * while(the queue is not empty ) { 
	 * 		dequeue a GridCell from the queue.
	 * 		mark each adjacent neighboring cell and enqueue it 
	 * }
	 * 
	 * note1: need to mark 4 cells and enqueue them
	 */
	public void mark() {
		// enqueue origin cell(0,0)
		GridCell originCell = grid.getCell(0,0);
		originCell.setDistance(0);
		queue.enqueue(originCell);
		grid.markDistance(originCell);
				
		while (!queue.isEmpty()) { 
			// dequeue a GridCell from the queue.
			GridCell currCell = queue.dequeue();
			int x = currCell.getX();
			int y = currCell.getY();
			int distance = currCell.getDistance() + 1;
			
			// mark each adjacent neighboring cell and enqueue it
			GridCell adjacentCell = grid.getCell(x,y+1);  //North cell
			if(grid.isValidMove(adjacentCell) && !adjacentCell.wasVisited()){
				adjacentCell.setDistance(distance);
				grid.markDistance(adjacentCell);
				queue.enqueue(adjacentCell);
			}
			
			adjacentCell = grid.getCell(x,y - 1);         // South cell
			if(grid.isValidMove(adjacentCell) && !adjacentCell.wasVisited()){
				adjacentCell.setDistance(distance);
				grid.markDistance(adjacentCell);
				queue.enqueue(adjacentCell);
			}
			
			adjacentCell = grid.getCell(x + 1,y);         // East cell
			if(grid.isValidMove(adjacentCell) && !adjacentCell.wasVisited()){
				adjacentCell.setDistance(distance);
				grid.markDistance(adjacentCell);
				queue.enqueue(adjacentCell);
			}
			
			adjacentCell = grid.getCell(x - 1,y);         // West cell
			if(grid.isValidMove(adjacentCell) && !adjacentCell.wasVisited()){
				adjacentCell.setDistance(distance);
				grid.markDistance(adjacentCell);
				queue.enqueue(adjacentCell);
			}
		}
	}
		

	/*
	 * Does part two, indicates in the GUI the shortest path found. For the
	 * second part, begin at the green exit point, then check each adjacent
	 * neighboring cell, and push the one with minimum distance onto the stack.
	 * The stack then contains all of the cells in the shortest path:
	 * 
	 * distance = terminalCell.getDistance(); 
	 * if(distance == -1) return false;
	 * // unreachable, puzzle has no solution 
	 * push terminal cell onto the stack
	 * while(distance != 0) { 
	 * 		get distance from each cell adjacent to the top of the stack 
	 * 		select the cell with smallest distance and push on the stack 
	 * }
	 * while( stack is not empty ) {
	 *  	pop grid cell off the stack and mark it 
	 *  }
	 *  
	 *  note1: grid is square so dimension by dimension, thus exit is at the point
	 *  (dimension - 1, dimension -1)
	 *  note2: need to check 4 adjacent cells
	 */
	public boolean move() {
		GridCell terminalCell = grid.getCell(dimension - 1, dimension - 1);
		int distance = terminalCell.getDistance();
		if (distance == -1) return false; // unreachable so the puzzle has no solution		
		stack.push(terminalCell);
		
		// get distance from each cell adjacent to the top of the stack 
		// select the cell with smallest distance and push on the stack 
		while (distance != 0) {
			
			GridCell currCell = stack.peek();
			int x = currCell.getX();
			int y = currCell.getY();
			distance = currCell.getDistance();
				
			GridCell adjacentCell = grid.getCell(x,y+1);  // North cell
			if(grid.isValidMove(adjacentCell) && adjacentCell.getDistance() < distance){
				stack.push(adjacentCell);
				continue;
			}
			
			adjacentCell = grid.getCell(x,y - 1);         // South cell
			if(grid.isValidMove(adjacentCell) && adjacentCell.getDistance() < distance){
				stack.push(adjacentCell);
				continue;
			}
			
			adjacentCell = grid.getCell(x + 1,y);         // East cell
			if(grid.isValidMove(adjacentCell) && adjacentCell.getDistance() < distance){
				stack.push(adjacentCell);
				continue;
			}
			
			adjacentCell = grid.getCell(x - 1,y);         // West cell
			if(grid.isValidMove(adjacentCell) && adjacentCell.getDistance() < distance){
				stack.push(adjacentCell);
				continue;
			}	
		}
		
		//pop grid cell off the stack and mark it 
		while (!stack.isEmpty()) { 
			grid.markMove(stack.pop());
		}
		
		return true;
	}

	// Reinitializes the puzzle. Clears the stack and queue (calls makeEmpty()).
	public void reset() {
		stack.makeEmpty();
		queue.makeEmpty();
	}

	public static void main(String[] args) {
		new MazeSolver(25);
	}
}
