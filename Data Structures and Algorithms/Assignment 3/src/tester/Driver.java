package tester;

/*  Driver.java
    A simple driver to test program #1  
    CS310 Spring 2018
    Alan Riggins
    
*/

import data_structures.*;

public class Driver {
    private int [] array;
    private static final int SIZE = 100;
    private PriorityQueue<Integer> pq;
    private PriorityQueue<PrioritizedItem> pq2;    
    
    public Driver() {
        array = new int[SIZE];
        //pq = new OrderedArrayPriorityQueue<Integer>(SIZE);
        pq = new BinaryHeapPriorityQueue<Integer>(SIZE);
      
        //pq2 = new OrderedArrayPriorityQueue<PrioritizedItem>(SIZE);
        pq2 = new BinaryHeapPriorityQueue<PrioritizedItem>(SIZE);                     
        initArray();
        test1();
        test2();
        test3(); 
        test4(); 
        test5();     
        }
        
    private void initArray() {
        for(int i=0; i < SIZE; i++)
            array[i] = i+1;
        // now scramble array order
        for(int i=0; i < SIZE; i++) {
            int idx = (int) (SIZE*Math.random());
            int tmp = array[i];
            array[i] = array[idx];
            array[idx] = tmp;
            }
        }
        
    private void test1() {
        pq.clear();
        for(Integer i : pq)
            throw new RuntimeException("Failed test #1, value returned in empty iterator");
        
        for(int i=0; i < SIZE; i++) 
            if(!pq.insert(array[i]))
                throw new RuntimeException("Failed test #1");
        
//////////////////////////////////////////////////////////////////////////////                
//  Comment this block for linked list based implementations         
       if(!pq.isFull())
            throw new RuntimeException("Failed test #1, isFull reports false, but pq should be full");       
        //try to exceed the capacity
        if(pq.insert(0))
            throw new RuntimeException("Failed test1, exceeded capacity");
//////////////////////////////////////////////////////////////////////////////       
        System.out.println("Passed test #1, simple insert");
        }
        
    private void test2() {
        for(int i=0; i < SIZE; i++){
           if(pq.remove() != (i+1)) {
                throw new RuntimeException("Failed test #2, out of order removal");
           }
        }        
        if(pq.remove() != null)
            throw new RuntimeException("Failed test #2, removal from empty pq did not return null");
            
        if(!pq.isEmpty())
            throw new RuntimeException("Failed test #2, isEmpty reports false, but pq is empty");
            
        System.out.println("Passed test #2, simple removal");
        }
        
    private void test3() { // check FIFO behavior
        int size=10;
        pq2.clear();
        int sequenceNumber = 0;
        int midPoint = size >> 1;
        
        for(int i=0; i < midPoint; i++)
            pq2.insert(new PrioritizedItem(2,sequenceNumber++));
        for(int i=midPoint; i < size; i++)
            pq2.insert(new PrioritizedItem(1,sequenceNumber++));
            
        PrioritizedItem item = pq2.peek();
        if(item.getPriority() != 1 || item.getSequenceNumber() != 5)
            throw new RuntimeException("Failed test #3, peek returns wrong element");

        sequenceNumber = midPoint;
        for(int i=0; i < midPoint; i++) {
            PrioritizedItem tmp = pq2.remove();
            if(tmp.getPriority() != 1)
                throw new RuntimeException("Failed test #3, out of order removal");
            if(tmp.getSequenceNumber() != (sequenceNumber++))
                throw new RuntimeException("Failed test #3, out of order removal");
            }
            
        sequenceNumber = 0;    
        for(int i=midPoint; i < size; i++) {
            PrioritizedItem tmp = pq2.remove();
            if(tmp.getPriority() != 2)
                throw new RuntimeException("Failed test #3, out of order removal");
            if(tmp.getSequenceNumber() != (sequenceNumber++))
                throw new RuntimeException("Failed test #3, out of order removal");                
            }
        System.out.println("Passed test #3, FIFO check");                                   
                 
        }
        
    private void test4() {
        pq2.clear();
        int sequenceNumber = 0;
        System.out.println("\nNow checking iterators, output is below.");
        System.out.println("NOTE: No specific order is required for these iterators.");
        for(int i=0; i < 5; i++)
            pq2.insert(new PrioritizedItem(10,sequenceNumber++));
        for(int i=0; i < 5; i++)
            pq2.insert(new PrioritizedItem(1,sequenceNumber++));    
        for(int i=0; i < 5; i++)
            pq2.insert(new PrioritizedItem(5,sequenceNumber++));                        
            
        for(PrioritizedItem item : pq2)
            System.out.println(item);
            
        System.out.println("Now removing elements with priority=5");
        pq2.delete(new PrioritizedItem(5,100));
         
        System.out.println("\nNow removing items, they should be in proper order,\n"+
                            "with all priority=5 items removed.");
        while(!pq2.isEmpty())
            System.out.println(pq2.remove());
            
        if(pq2.size() != 0)
            System.out.println("Failed test #4, size is wrong.");
        }
        
    private void test5() {
        final int TEST_5_SIZE = 1000;
        pq = new BinaryHeapPriorityQueue<Integer>(TEST_5_SIZE);   
        //pq = new UnorderedArrayPriorityQueue<Integer>(TEST_5_SIZE);
        
        for(int i=0; i<TEST_5_SIZE; i++) {             
            int someInteger = (int) (TEST_5_SIZE*Math.random());                                
            if(!pq.insert(someInteger)) {
                System.out.println("ERROR in test 5, insertion failed!");
                System.exit(1);
                }
           // if(pq.contains(someInteger))
            	//System.out.println("true");
            }
        int removed = pq.remove();
        for(int i=1; i < TEST_5_SIZE; i++) {
            int removed2 = pq.remove();
            if(removed2 < removed) {
                System.out.println("ERROR, out of order removal in test 5");
                System.exit(1);
                }
            removed = removed2;
            }
        System.out.println("Passed test #5");
        }
 
    ///////////////////////////////////////////////////////////////////////   
    private class PrioritizedItem implements Comparable<PrioritizedItem>{
        private int priority;
        private int itemNumber;
        
        public PrioritizedItem(int p, int n) {
            priority = p;
            itemNumber = n;
            }
            
        public int compareTo(PrioritizedItem item) {
            return priority - item.priority;
            }
            
        public String toString() {
            return "Priority: " + priority + " Item Number: " + itemNumber;
            }
            
        public int getPriority() {
            return priority;
            }
            
        public int getSequenceNumber() {
            return itemNumber;
            }
        }
    ///////////////////////////////////////////////////////////////////////
        
    public static void main(String [] args) {
        new Driver();
        }
    }