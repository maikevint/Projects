/*
 *  Program 3
 *  Kevin Mai
 *  cssc0918
*/

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryHeapPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
	int entryNumber, currentSize, maxSize;
	private long modCounter; 
	private Wrapper<E> storage[];
	public static final int DEFAULT_MAX_CAPACITY = 1000;
	
	// Constructor that takes a maxCapacity parameter,
	public BinaryHeapPriorityQueue(int max) {
		maxSize = max;
		currentSize = 0;
		modCounter = 0;
		storage = (Wrapper<E>[]) new Wrapper[maxSize];	
	}
	
	// Constructor with no arguments that creates an array of
	// DEFAULT_MAX_CAPACITY.
	public BinaryHeapPriorityQueue() {
		this(DEFAULT_MAX_CAPACITY);
	}
	
	// Allows binary heap to be stable/perserve order
	protected class Wrapper<T> implements Comparable<Wrapper<E>> {
		long seqNumber;
		E data;
		
		public Wrapper(E d) {
			seqNumber = entryNumber++; // from parent class
			data = d;
		}
		
		public int compareTo(Wrapper<E> o){
			if(((Comparable<E>)data).compareTo(o.data) == 0) // if data is equal, use sequence number 
					return (int) (seqNumber - o.seqNumber);
			return ((Comparable<E>)data).compareTo(o.data);
		}
	}
	
	//  Inserts a new object into the priority queue.  Returns true if
    //  the insertion is successful.  If the PQ is full, the insertion
    //  is aborted, and the method returns false. log(n)
	public boolean insert(E object) {
		if(isFull()) return false;
		storage[currentSize] = new Wrapper<E> (object);
		if(currentSize > 1)trickleUp();  // if more than one object
		currentSize++;
		modCounter++;
		return true;
	}

	//  Removes the object of highest priority that has been in the
    //  PQ the longest, and returns it.  Returns null if the PQ is empty.
    public E remove() {
		if(isEmpty()) return null;
    	E tmp = storage[0].data;
    	trickleDown();	
    	currentSize--;
    	modCounter++;
    	return tmp;
	}
    
    //  Deletes all instances of the parameter obj from the PQ if found, and
    //  returns true.  Returns false if no match to the parameter obj is found.
    public boolean delete(E obj) {
		BinaryHeapPriorityQueue<E> tmp = new BinaryHeapPriorityQueue<E>(currentSize);		
		if(contains(obj)){
			while(currentSize != 0){
				E x = this.remove();
				if(x.compareTo(obj) != 0) 
					tmp.insert(x);						
			}
			this.storage = tmp.storage;          // storage now contains non deletes
			this.currentSize = tmp.currentSize;  // proper currentSize
			return true;
		}                                        // not found or empty case
		return false; 
	}
    
    //  Returns the object of highest priority that has been in the
    //  PQ the longest, but does NOT remove it. 
    //  Returns null if the PQ is empty.
	public E peek() {
		if(isEmpty()) return null;
		return storage[0].data;
	}
	
    //  Returns true if the priority queue contains the specified element
    //  false otherwise.
	public boolean contains(E obj) {	
		if(isEmpty()) return false;
		for (Wrapper<E> tmp : this.storage)
			if (((Comparable<E>) obj).compareTo(tmp.data) == 0)
				return true;
		return false;
	}
	
	//  Returns the number of objects currently in the PQ.
	public int size() {
		return currentSize;
	}
	
	//  Returns the PQ to an empty state.
	public void clear() {
		currentSize = 0;
	}
	
	//  Returns true if the PQ is empty, otherwise false
	public boolean isEmpty() {
		return currentSize == 0;
	}

	//  Returns true if the PQ is full, otherwise false.  List based
    //  implementations should always return false.
	public boolean isFull() {
		return currentSize == maxSize;
	}
    
	// restore heap order after insertion
	private void trickleUp(){
		int newIndex = currentSize - 1; 
		int parentIndex  = (newIndex - 1) >> 1; 
		Wrapper<E> newValue = storage[newIndex];
		while(parentIndex >= 0 && newValue.compareTo(storage[parentIndex]) < 0){
			storage[newIndex] = storage[parentIndex];
			newIndex = parentIndex;
			parentIndex = (parentIndex - 1) >> 1;
		}
		storage[newIndex] = newValue;
	}
	
	// restore heap order after removal
	private void trickleDown(){
		int current = 0;
		int child = getNextChild(current);
		while(child != -1 && storage[current].compareTo(storage[child]) < 0
			&& storage[child].compareTo(storage[currentSize - 1]) < 0){
			storage[current] = storage[child];
			current = child;
			child = getNextChild(current);
		}
		storage[current] = storage[currentSize - 1];
	}
	
	// finds the next highest priority in the heap
	private int getNextChild(int current){
		int left = (current << 1) + 1;
		int right = left + 1;
		if(right < currentSize) {  // there are two children
			if(storage[left].compareTo(storage[right]) < 0)
				return left;       // the left child is smaller
			return right;          // the right child is smaller
		}
		if(left < currentSize)     // there is only one child
				return left;
 		return -1; // no children
	}
	
	//  Returns an Iterator of the values in the list, presented in
	//  the same order as the underlying order of the list. (front first, rear last)
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}

	class IteratorHelper implements Iterator<E> {
		private int index;
		private long modCheck;

		public IteratorHelper() {
			modCheck = modCounter;
			index = 0;
		}

		public boolean hasNext() {
			if(modCheck != modCounter)throw new ConcurrentModificationException();
			return index != currentSize;
		}

		public E next() {
			if (!hasNext())throw new NoSuchElementException();
			return storage[index++].data;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
