package testers;

import data_structures.*;

public class TestStack {

	/**
	 * Test the stack interface. The stack should have these methods:
	 *     
	 * public void push(E obj) {}   
	 * public E pop() {}   
	 * public int size() {}
	 * public boolean isEmpty() {}
	 * public E peek() {}
	 * public boolean contains(E obj) {}
	 * public void makeEmpty() {} 
	 * public Iterator iterator() {}
	 */

	public static void main(String args[]) {

		boolean error = false;

		// add 18 things to the stack
		Stack<Integer> s = new Stack<Integer>();

		for (int i = 0; i < 18; i++)
			s.push(i);

		// the size should be 18!
		if (s.size() != 18) {
			System.err.println("The stack has size " + s.size() + " but it should be 18");
			error = true;
		}

		// now try and pop things in the right order
		// stack is first in, last out so it should be 18 -> 1

		for (int i = 17; i > 7; i--) {
			int r = s.pop();
			if (r != i) {
				System.err.println("Pulled " + r + " from the stack but should have pulled " + i);
				error = true;
			}
		}

		// size should be 8
		if (s.size() != 8) {
			System.err.println("After removing 10 things the size should be 8 but is " + s.size());
			error = true;
		}

		if (s.isEmpty()) {
			System.err.println("The stack thinks it is empty, but it is not");
			error = true;
		}

		for (int i = 0; i< 5; i++) {
			int p = s.peek();
			if (p != 7) {
				System.err.println("When we peek at the stack we see " + p + " but should see 7");
				error=true;
			}
		}

		if (s.contains(25)) {
			System.err.println("The stack thinks it contains a 25 but it should not");
			error=true;
		}
		
		if (!s.contains(5)) {
			System.err.println("The stack does not think it contains a 5 but it should");
			error = true;
		}

		s.makeEmpty();
		if (s.size() != 0) {
			System.err.println("The stack has size of " + s.size() + " but it should be empty");
			error = true;
		}

		Integer r = s.pop();
		if (r != null) {
			System.err.println("pop from an empty stack returned " + r + " but should return null");
			error = true;
		}

		// add 25 things in order:
		for (int i = 1; i<=25; i++)
			s.push(i);
		//now print them out
		System.out.println("This should print 25 -> 1");
		for (int i = 1; i<=25; i++){
			//System.out.print(i);
			System.out.print(s.pop() + " ");
		}
		System.out.println();

		if (error) 
			System.out.println("FAIL. There was one or more errors in your code. Please check them");
		else
			System.out.println("SUCCESS. All tests ran");

	}
}
