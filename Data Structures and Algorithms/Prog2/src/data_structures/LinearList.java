/*
 *  Program 2
 *  Kevin Mai
 *  cssc0922
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class LinearList<E extends Comparable <E>> implements LinearListADT<E> {
	private Node<E> head, tail;
	private int currentSize;
	private long modCounter; 
	
	protected class Node<T> {
		T data;
		Node<T> next, prev;
		
		public Node(T obj){
			data = obj;
			prev = next = null;
			}
		}  // end class Node
	
	// constructor
	public LinearList(){
			head = tail = null;
			currentSize = 0;
			modCounter = 0;
	}
	
//  Adds the Object obj to the beginning of list and returns true if the list is not full.
//  returns false and aborts the insertion if the list is full.(new nodes are made)
    public boolean addFirst(E obj){
    	Node<E>  newNode = new Node <E> (obj);
    	if(head == null) head = tail = newNode;  //Empty Case
    	else {
    		newNode.next = head;
    		head.prev = newNode;
    		head = newNode;
    	}
    	currentSize++;
    	modCounter++;
    	return true;
    }
    
//  Adds the Object obj to the end of list and returns true if the list is not full.
//  returns false and aborts the insertion if the list is full..  
    public boolean addLast(E obj){
    	Node<E>  newNode = new Node <E> (obj);
    	if(head == null) head = tail = newNode;  //Empty Case
    	else {
    		newNode.prev = tail;
    		tail.next = newNode;
    		tail = newNode;
    	}
    	currentSize++;
        modCounter++; 	
    	return true;
    }
    
//  Removes and returns the parameter object obj in first position in list if the list is not empty,  
//  null if the list is empty. 
    public E removeFirst(){
    	if(isEmpty() == true) return null; 	
    	if(head == null) return null;	
    	E tmp1 = head.data;  	
    	if(head == tail) head = tail = null;
    	else head = head.next;
    	currentSize--;
    	modCounter++;
    	return tmp1;
    }
    
//  Removes and returns the parameter object obj in last position in list if the list is not empty, 
//  null if the list is empty. 
    public E removeLast(){
    	//if(isEmpty() == true) return null;
    	if(head == null) return null;
    	E tmp2 = tail.data;
    	if(tail == head) head = tail = null;
    	else {
    		tail = tail.prev;
    		tail.next = null;
    	}
    	currentSize--;
    	modCounter++;
    	return tmp2;
    }
    
//  Removes and returns the parameter object obj from the list if the list contains it, null otherwise.
//  The ordering of the list is preserved.  The list may contain duplicate elements.  This method
//  removes and returns the first matching element found when traversing the list from first position.
//  Note that you may have to shift elements to fill in the slot where the deleted element was located.
    public E remove(E obj){
    	if(isEmpty() == true) return null;
    	Node<E> current = head;
    	while(current != null && obj.compareTo(current.data) != 0)
    		current = current.next;
    	if(current == null) return null;
    	if(current == head) return removeFirst();
    	if(current == tail) return removeLast();
    	current.prev.next = current.next;
    	current.prev.prev = current.prev;
    	currentSize--;
    	modCounter++;
    	return current.data;
    }
    
//  Returns the first element in the list, null if the list is empty.
//  The list is not modified.
    public E peekFirst(){
    	if (isEmpty())return null;
    	return head.data;
    }
    
//  Returns the last element in the list, null if the list is empty.
//  The list is not modified.
    public E peekLast(){
    	if (isEmpty())return null;
    	return tail.data;
    }

//  Returns true if the parameter object obj is in the list, false otherwise.
//  The list is not modified.
    public boolean contains(E obj){
    	if (isEmpty())return false;
		return find(obj) != null;
    }
    
//  Returns the element matching obj if it is in the list, null otherwise.
//  In the case of duplicates, this method returns the element closest to front.
//  The list is not modified.
    public E find(E obj){
    	if (isEmpty())return null;
    	Node<E> current = head;
    	while(current != null){
    		if(obj.compareTo(current.data) == 0) return current.data;
    		current = current.next;	
    	}    	
    	return null;
    }

//  The list is returned to an empty state.
    public void clear(){
    	head = tail = null; 
    	currentSize = 0;
    	modCounter = 0;
    }

//  Returns true if the list is empty, otherwise false
    public boolean isEmpty(){
    	return currentSize == 0;
    }
    
//  Returns true if the list is full, otherwise false
    public boolean isFull(){
    	return false;
    }

//  Returns the number of Objects currently in the list.
    public int size(){
    	return currentSize;
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
