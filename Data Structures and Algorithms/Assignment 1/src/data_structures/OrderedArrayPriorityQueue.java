/*
 *  Program 1
 *  Kevin Mai
 *  cssc0918
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
	private E storage[];
	private int currentSize, maxSize;
	
	// Constructor that takes a maxCapacity parameter,
	public OrderedArrayPriorityQueue(int max) {
		maxSize = max;
		currentSize = 0;
		storage = (E[]) new Comparable[maxSize];
	}
	
	// Constructor with no arguments that creates an array of
	// DEFAULT_MAX_CAPACITY.
	public OrderedArrayPriorityQueue() {
		this(DEFAULT_MAX_CAPACITY);
	}
	
	/*
	 * Inserts a new object into the priority queue. Returns true if the
	 * insertion is successful. If the PQ is full, the insertion is aborted, and
	 * the method returns false.
	 */
	public boolean insert(E object) {
		if(isFull()) return false;
		int where = findInsertionPoint(object, 0, currentSize - 1);
		for(int i = currentSize - 1; i >= where; i--) storage[i + 1] = storage[i];
		storage[where] = object;
		currentSize++;
		return true;
	}

	/*
	 * Removes the object of highest priority that has been in the PQ the
	 * longest, and returns it. Returns null if the PQ is empty.
	 * binary search
	 */
	public E remove() {
		if(isEmpty()) return null;
		return storage[--currentSize];
	}

	/*
	 * Deletes all instances of the parameter obj from the PQ if found, and
	 * returns true. Returns false if no match to the parameter obj is found.
	 * Shifts to remove the instances.
	 */
	public boolean delete(E obj) {
		if(!contains(obj)) return false;
		while(contains(obj)){
			shift(obj);
		}
		return true;
	}

	/*
	 * Returns the object of highest priority that has been in the PQ the
	 * longest, but does NOT remove it. Returns null if the PQ is empty.
	 */
	public E peek() {
		if(isEmpty() == true) return null;
		return storage[currentSize-1];
	}

	/*
	 * Returns true if the priority queue contains the specified element false
	 * otherwise.
	 */
	public boolean contains(E obj) {
		if(isEmpty()) return false;
		return binarySearch(obj, 0, currentSize - 1) != -1;      
	}

	// Returns the number of objects currently in the PQ.
	public int size() {
		return currentSize;
	}

	// Returns the PQ to an empty state.
	public void clear() {
		currentSize = 0;
	}

	// Returns true if the PQ is empty, otherwise false
	public boolean isEmpty() {
		return currentSize == 0;
	}

	// Returns true if the PQ is full, otherwise false. List based
	// implementations should always return false.
	public boolean isFull() {
		return currentSize == maxSize;
	}

	// Identifies the correct insertion point for new additions
	private int findInsertionPoint(E obj, int lo, int hi){
		if(hi < lo) return lo;							   // insertion point
		int mid = (lo + hi) >> 1;                          // >>1 == /2
		if(((Comparable<E>)obj).compareTo(storage[mid]) >= 0)
			return findInsertionPoint(obj, lo, mid - 1);   // go left
		return findInsertionPoint(obj, mid + 1, hi);       // go right
	}
	
	// Finds the element in the array
	private int binarySearch(E obj, int lo, int hi){
		if(hi < lo) return -1;							   // element is not present
		int mid = (lo + hi) >> 1;						   // >> == /2
		if(((Comparable<E>)obj).compareTo(storage[mid]) == 0) return mid;
		else if(((Comparable<E>)obj).compareTo(storage[mid]) > 0)   
			return binarySearch(obj, lo, mid - 1);         // go left
		else return binarySearch(obj, mid + 1, hi);		   // go right
	}
	
	private void shift(E obj){
		int target = binarySearch(obj, 0, currentSize - 1);
		for(int i = target; i < currentSize - 1; i++){
			storage[i] = storage[i + 1];       
		}
		currentSize--;
	}
	
	// Returns an iterator of the objects in the PQ, in no particular
	// order.
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}

	class IteratorHelper implements Iterator<E> {
		private int index;

		public IteratorHelper() {
			index = 0;
		}

		public boolean hasNext() {
			return index != currentSize;
		}

		public E next() {
			if (!hasNext())throw new NoSuchElementException();
			return storage[index++];
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
