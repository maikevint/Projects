package testers;

import data_structures.*;

/**
 * @author Rob Edwards
 * <p>
 * This code is designed to help you test your Linked List code. It will create a generic linked list,
 * add some elements, and remove some elements. It is designed to work with a linked list program
 * called LinkedList.java that is in the data_structures package.
 * <p>
 * You should confirm that this tests all the edge cases for your linked list. Remember the five situations
 * we discussed in class? Does this code test all of them, or should other methods be added?
 * <p>
 * Does this code test all methods in ListI?
 * <p>
 * Remember, that per the instructions, your LinkedList code should not print any extra information - just
 * what is printed by this tester.
 */
public class TestLinkedList {

    static LinearListADT<Integer> llist = null;

    /**
     * @param args The main method. In this case, we don't accept any arguments. We create a new linked list object
     *             and fill it with Integers.
     */
    public static void main(String[] args) {

        boolean error = false;
        try {
            llist = new LinearList<Integer>();

            // add n elements to the list
            int n = 25;
            for (int i = 1; i <= n; i++)
                llist.addFirst(i);

            // the last thing in the list should be 1
            if (llist.peekLast() != 1) {
                System.err.println("The last item in the list should be a 1 but is a " + llist.peekLast());
                error = true;
            }

            // the size should be n
            if (llist.size() != n) {
                System.err.println("We added " + n + " elements to the start of the list, but the size reported was " + llist.size());
                error = true;
            }

            // check the list with the iterator. If n = 25, this should print
            // 25 24 23 22 21 ... 5 4 3 2 1
            System.out.println("Using the iterator to print from 25-1");
            for (Integer i : llist)
                System.out.print(i + " ");
            System.out.println();

            // remove from the front of the list ... should be in the order n .. 1
            for (int i = n; i > 0; i--) {
                int removed = llist.removeFirst();           
                if (removed != i) {
                    System.err.println("Remove first returned " + removed + " but expected " + i);
                    error = true;
                }
            }

            // the size should be 0
            if (llist.size() != 0) {
                System.err.println("We removed everything from the list, but the size is still " + llist.size());
                error = true;
            }

            // now test the add last methods
            for (int i = 1; i <= n; i++)
                llist.addLast(i);

            if (llist.size() != n) {
                System.err.println("We added " + n + " things to the list, but the size is " + llist.size());
                error = true;
            }
            // iterate through and print out the results
            // these should be in the opposite order from before.
            System.out.println("Using the iterator to print from 1-25");
            for (Integer i : llist)
                System.out.print(i + " ");
            System.out.println();

            // and remove last
            for (int i = n; i > 0; i--) {
                int removed = llist.removeLast();
                if (removed != i) {
                    System.err.println("Remove last returned " + removed + " but expected " + i);
                    error = true;
                }
            }

            // add some things
            for (int i = 1; i <= n; i++)
                llist.addFirst(i);

            // now make the list empty
            llist.clear();

            // is the size 0?
            if (llist.size() != 0) {
                System.err.println("We made the list empty, but the size is still " + llist.size());
                error = true;
            }

            // now add one thing to the list
            llist.addLast(n + 1);
            System.out.println("last is " + llist.peekLast() + " size is " + llist.size());
            // this should be the only thing in the list
            for (int i : llist)
                if (i != (n + 1)) {
                    System.err.println("There should be only one thing in the list, but we got " + i);
                    error = true;
                }

            // make empty again
            llist.clear();

            // now play at adding/removing
            llist.addFirst(n + 2);
            llist.removeFirst();
            llist.addLast(n + 3);

            // this should be the only thing in the list
            for (int i : llist)
                if (i != (n + 3)) {
                    System.err.println("There should be only one thing in the list, but we got " + i);
                    error = true;
                }

            if (llist.removeFirst() != n + 3) {
                System.err.println("We should have removed " + (n + 3) + " from the list but didn't");
                error = true;
            }

            if (llist.removeFirst() != null) {
                System.err.println("The list should be empty and not be able to remove anything and should return null");
                error = true;
            }

            if (llist.removeLast() != null) {
                System.err.println("The list should be empty and not be able to remove anything and should return null");
                error = true;
            }

            llist.clear();
            for (int i = 1; i < 26; i++)
                llist.addFirst(i);
            System.out.println("Printing the list from 25-1: ");
            for (int i : llist)
                System.out.print(i + " ");
            System.out.println();

        } catch (Exception e) {
            System.err.println("Your linked list code threw an exception. You need to fix this." +
                    " Take a look at this stack trace to see what is wrong:");
            e.printStackTrace();
            System.exit(-1);
        }
        if (error)
            System.out.println("FAIL. There were one or more errors while running the code");
        else
            System.out.println("SUCCESS. All the tests ran");
    }
}
