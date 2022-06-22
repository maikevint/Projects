/*  This driver goes in the level above data_structures
    Driver for program #1.  This simple tester/driver is just a sample.
    The fact that your program might run without errors with this driver
    does not in any way guarantee that it is error free.  You are responsible
    for testing your program.
    Alan Riggins
    CS310 Fall 2017
*/    


import data_structures.*;

public class P1Driver {
    private LinearListADT<Integer> list;
    
    public P1Driver() {
        list = new ArrayLinearList<Integer>(10);
        runTests();
        }
        
    private void runTests() {
        for(int i=1; i <= 10; i++) 
            list.addFirst(i);
        System.out.println("Should now print 10 .. 1");
        for(Integer i : list)
            System.out.println(i);
        for(int i=1; i <= 10; i++) 
            if(list.removeFirst() == null)
                throw new RuntimeException("ERROR with removeFirst");
        for(Integer i : list)
            System.out.println(i); 
            
        if(!list.isEmpty())
            throw new RuntimeException("ERROR in inEmpty");  
        if(list.size() != 0)
            throw new RuntimeException("ERROR in size");
          
        for(int i=1; i <= 1000; i++) {
            for(int j=1; j <= 7; j++)
                list.addFirst(j);
            for(int j=1; j <= 7; j++)                
                list.removeLast();
            }
        for(Integer i : list)
            System.out.print(i + ", ");
        System.out.println("Front is " + list.peekFirst());
        System.out.println("Last is " + list.peekLast());
        System.out.println("List size is " + list.size());
        list.addFirst(-1);
        System.out.println("Front is " + list.peekFirst());
        System.out.println("Last is " + list.peekLast());
        System.out.println("List size is " + list.size());
        if(list.peekLast() != -1)   
            throw new RuntimeException("ERROR in peekLast");
        list.clear();
        list.addLast(-1);
        if(list.peekFirst() != -1)   
            throw new RuntimeException("ERROR in peekLast"); 
            
        list.clear();
        for(int i=1; i <= 10; i++)
            list.addFirst(i);
            
        for(int i=1; i <= 10; i++)
            if(list.find(i) != i)
                throw new RuntimeException("ERROR in find"); 
                
        for(int i=1; i <= 10; i++)
            if(!list.contains(i))
                throw new RuntimeException("ERROR in find");                           
        
                                
       }
        
    public static void main(String [] args) {
        new P1Driver();
        }
    }