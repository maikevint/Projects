/*
 *  Program 2
 *  Kevin Mai
 *  cssc0918
*/

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedLinkedListPriorityQueue<E extends Comparable <E>> implements PriorityQueue<E>  {
	private Node<E> head;
	private int currentSize;
	private long modCounter; 
	
	protected class Node<T> {
		T data;
		Node<T> next;
		
		public Node(T obj){
			data = obj;
			next = null;
			}
		}  // end class Node
	
	// constructor
	public OrderedLinkedListPriorityQueue(){
			head = null;
			currentSize = 0;
			modCounter = 0;
	}

	 //  Inserts a new object into the priority queue.  Returns true if
    //  the insertion is successful.  If the PQ is full, the insertion
    //  is aborted, and the method returns false.
    public boolean insert(E object){
    	Node<E> newNode = new Node<E>(object);
    	Node<E> previous = null, current = head;
    	// find insertion point
    	while(current != null && ((Comparable<E>)object).compareTo(current.data) >= 0 ){
    		previous = current;
    		current = current.next;
    	}
    	
    	if(previous == null) {  // it goes in first place or list is empty
    		newNode.next = head;
    		head = newNode;
    	}
    	else{                   // in the middle or end
    		previous.next = newNode;
    		newNode.next = current;
    	}
    	currentSize++;
    	modCounter++;
    	return true;
    }
   
    //  Removes the object of highest priority that has been in the
    //  PQ the longest, and returns it.  Returns null if the PQ is empty.
    public E remove(){
        if(head == null) return null;	   // empty case
        E tmp1 = head.data;  	
        head = head.next;                  // 1 or more case, make head null if 1
        currentSize--;
        modCounter++;
        return tmp1;
    }
    
    //  Deletes all instances of the parameter obj from the PQ if found, and
    //  returns true.  Returns false if no match to the parameter obj is found.
	public boolean delete(E obj) {
		Node<E> previous = null, current = head;
		// find the node to delete first
		if (!contains(obj))
			return false;
		while (contains(obj)) {
			while (current != null && ((Comparable<E>) obj).compareTo(current.data) != 0) {
				previous = current;
				current = current.next;
			}
			// if current != null, then node was found
			if (current == null) {
				return false; // ran off the end, node not found or empty list
			}
			if (current.next == null) {
				previous.next = null;
				current = previous;
			} else if (current == head){		
				head = head.next;
				current = head;
			}
			else{ // the node to remove is in the middle
				previous.next = current.next;
				current = previous;
			}
			currentSize--;
			modCounter++;
		}
		return true;
	}
   
    //  Returns the object of highest priority that has been in the
    //  PQ the longest, but does NOT remove it. 
    //  Returns null if the PQ is empty.
    public E peek(){
    	if (isEmpty())return null;
    	return head.data;
    }
    
    //  Returns true if the priority queue contains the specified element
    //  false otherwise.
    public boolean contains(E obj){
    	Node<E> current = head;
    	// if empty, current = null and return false
    	while(current != null){
    		if(obj.compareTo(current.data) == 0) return true;
    		current = current.next;	
    	}    	
    	return false;
    }
   
    //  Returns the number of objects currently in the PQ.
    public int size(){
    	return currentSize;
    }
      
    //  Returns the PQ to an empty state.
    public void clear(){
    	head = null; 
    	currentSize = 0;
    	modCounter = 0;
    }
   
    //  Returns true if the PQ is empty, otherwise false
    public boolean isEmpty(){
    	return currentSize == 0;
    }
   
    //  Returns true if the PQ is full, otherwise false.  List based
    //  implementations should always return false.
    public boolean isFull(){
    	return false;
    }
    
//  Returns an Iterator of the values in the list, presented in
//  the same order as the underlying order of the list. (front first, rear last)
    public Iterator<E> iterator(){
    	return new IteratorHelper();
    }
    
	class IteratorHelper implements Iterator<E> {
		private Node<E> iterPtr;
		private long modCheck;

		public IteratorHelper() {
			modCheck = modCounter;
			iterPtr = head;
		}

		public boolean hasNext() {
			if(modCheck != modCounter)throw new ConcurrentModificationException();
			return iterPtr != null;
		}

		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			E tmp = iterPtr.data;
			iterPtr = iterPtr.next;
			//modCheck--;
			return tmp;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}
