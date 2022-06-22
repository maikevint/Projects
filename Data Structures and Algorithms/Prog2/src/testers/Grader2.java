package testers;


import java.util.*;
import data_structures.*;

public class Grader2 {
    private LinearListADT<Integer> list;
    private static final int MAX_SIZE = 1000000;
    
    public Grader2() {
        list = new LinearList<Integer>();
        runTests();
        }
        
    private void runTests() {
try{    
        for(int i=100; i > 0; i--) 
            list.addFirst(i);
        print("1 Should print 1 .. 100");
        print();
        for(int i=101; i <= 200; i++)
            list.addLast(i);
        print("2 Should print 1 .. 200");
        print();
        for(int i = 1; i <= 100; i++)
            if(list.removeFirst() != i)
                print("3 ERROR in removeFirst at" + i);
        for(int i=200; i > 102; i--)  // leave two elements
            if(list.removeLast() != i)
                print("4 ERROR in removeFirst at" + i);
        print("Should print 101 102");
        print();   
        for(int i=0; i < MAX_SIZE-2; i++)
            list.addFirst(-i);   
        print("5 Size is now: " + list.size());

        
        for(int i=0; i < MAX_SIZE >> 2; i++) {
            list.removeFirst();
            list.removeLast();
            }  
            
        print("6 Size is now: " + list.size());
}
catch(Exception e) {
    System.out.println("In first catch block");
    e.printStackTrace();
    }
try {    
        list.clear();
        print();
        list.addLast(5);
        if(list.peekFirst() != 5) print("7 ERROR in peekFirst");
        if(list.remove(5) != 5) print("8 ERROR in remove");
        if(list.contains(5)) print("9 ERROR in contains");        
        if(list.find(5) != null) print("10 ERROR in find");
        if(!list.isEmpty()) print("11 ERROR in isEmpty");
        list.addFirst(15);
        if(list.peekLast() != 15) print("12 ERROR in peekLast");
        if(list.remove(15) != 15) print("13 ERROR in remove");
        if(list.contains(15)) print("14 ERROR in contains");        
        if(list.find(15) != null) print("15 ERROR in find"); 
        if(list.size() != 0) print("ERROR in size, should be empty but shows " + list.size());       
        
        int [] arr = new int[100];
        for(int i=0; i < 100; i++)  {
            list.addFirst(i);
            arr[i] = i;
            }
        for(int i=0; i < 100; i++) {
            int tmp = (int) (100*Math.random());
            int x = arr[i];
            arr[i] = arr[tmp];
            arr[tmp] = x;
            }
           
       
        for(int i=0; i < 100; i++)  {
            if(list.remove(i) == null)
                print("ERROR in remove, did not remove "+i);;            
            }

for(Integer x : list) print("Remaining in structure: " + x);
            
           }
catch(Exception e) {
    System.out.println("16 In second catch block");
    e.printStackTrace();
    } 
try {
    list.clear();
    for(int i=0; i < MAX_SIZE; i++)
        list.addFirst(i);
    for(int j=0; j < 20; j++) {
        for(int i=0; i < 1000; i++)
            if(list.removeFirst() == null) print("17 ERROR in removeFirst");
        for(int i=0; i < 1000; i++)
            if(!list.addLast(i)) print("18 ERROR in addLast");
            }
            
    for(int j=0; j < 20; j++) {
        for(int i=0; i < 1000; i++)
            if(list.removeLast() == null) print("19 ERROR in removeLast");
        for(int i=0; i < 1000; i++)
            if(!list.addFirst(i)) print("20 ERROR in addFirst");
            }            

    list.clear();
    }
catch(Exception e) {
    System.out.println("24 In third catch block");
    e.printStackTrace();
    } 
        
try {    
    if(list.find(50) != null) print("24 ERROR in unsuccessful search");
    }
catch(Exception e) {}    
try {
    if(list.peekFirst() != null) print("25 ERROR in peekFirst");
    }
catch(Exception e) {} 
try {
    if(list.peekLast() != null) print("26 ERROR in peekLast");
    }
catch(Exception e) {} 
try {
    list.addFirst(10);
    Iterator<Integer> iter = list.iterator();
    list.addLast(20);
    while(iter.hasNext())
        print(iter.next()+"");
    print("ERROR, iterator is not fail-fast");
    }
catch(ConcurrentModificationException e) {
    print("OK iterator is fail-fast");
    }
catch(Exception e) {
    print("ERROR, wrong exception " + e);
    }    
       
}
                           

       
    private void print(String s) {
        System.out.println(s);
        }
       
    private void print() {
        for(Integer i : list)
            System.out.print(i + " ");
        System.out.println();
        }
        
    public static void main(String [] args) {
        new Grader2();
        }
    }