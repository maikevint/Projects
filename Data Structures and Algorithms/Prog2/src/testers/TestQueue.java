package testers;

import data_structures.*;

public class TestQueue {

    /**
     * Test the queue interface. The queue should have these methods:
     * <p>
     * public void enqueue(E obj) {}
     * public E dequeue() {}
     * public int size() {}
     * public boolean isEmpty() {}
     * public E peek() {}
     * public boolean contains(E obj) {}
     * public void makeEmpty() {}
     * public Iterator iterator() {}
     */

    public static void main(String args[]) {

        boolean error = false;

        // add 18 things to the queue
        Queue<Integer> q = new Queue<Integer>();

        for (int i = 0; i < 18; i++)
            q.enqueue(i);

        // the size should be 18!
        if (q.size() != 18) {
            System.err.println("The queue has size " + q.size() + " but it should be 18");
            error = true;
        }

        // now try and dequeue things in the right order
        // queue is first in, first out so it should be 1 -> 18

        for (int i = 0; i < 10; i++) {
            int r = q.dequeue();
            if (r != i) {
                System.err.println("Pulled " + r + " from the queue but should have pulled " + i);
                error = true;
            }
        }

        // size should be 8
        if (q.size() != 8) {
            System.err.println("After removing 10 things the size should be 8 but is " + q.size());
            error = true;
        }

        if (q.isEmpty()) {
            System.err.println("The queue thinks it is empty, but it is not");
            error = true;
        }
        
        for (int i = 0; i < 5; i++) {
            int p = q.peek();
            if (p != 10) {
                System.err.println("When we peek at the queue we see " + p + " but should see 10");
                error = true;
            }
        }

        if (q.contains(25)) {
            System.err.println("The queue thinks it contains a 25 but it should not");
            error = true;
        }

        if (!q.contains(15)) {
        	System.out.println("contains is " + q.contains(15));
            System.err.println("The queue does not think it contains a 15 but it should");
            error = true;
        }
        q.makeEmpty();
        if (q.size() != 0) {
            System.err.println("The queue has size of " + q.size() + " but it should be empty");
            error = true;
        }

        Integer r = q.dequeue();
        if (r != null) {
            System.err.println("Dequeue from an empty queue returned " + r + " but should return null");
            error = true;
        }

        // add 25 things in order:
        for (int i = 1; i <= 25; i++)
            q.enqueue(i);
        //now print them out
        System.out.println("This should print 1 -> 25");
        for (int i = 1; i <= 25; i++)
            System.out.print(q.dequeue() + " ");
        System.out.println();

        if (error)
            System.out.println("FAIL. There was one or more errors in your code. Please check them");
        else
            System.out.println("SUCCESS. All tests ran");

    }
}