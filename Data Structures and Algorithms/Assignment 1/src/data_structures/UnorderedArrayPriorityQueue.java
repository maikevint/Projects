/*
 *  Program 1
 *  Kevin Mai
 *  cssc0918
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnorderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {

	private int currentSize, maxSize;
	private E storage[];

	// Constructor that takes a maxCapacity parameter,
	public UnorderedArrayPriorityQueue(int max) {
		maxSize = max;
		currentSize = 0;
		storage = (E[]) new Comparable[maxSize];
	}
	
	// Constructor with no arguments that creates an array of
	// DEFAULT_MAX_CAPACITY.
	public UnorderedArrayPriorityQueue() {
		this(DEFAULT_MAX_CAPACITY);
	}

	/*
	 * Inserts a new object into the priority queue. Returns true if the
	 * insertion is successful. If the PQ is full, the insertion is aborted, and
	 * the method returns false.
	 */
	public boolean insert(E object) {
		if(isFull()) return false;
		storage[currentSize++] = object;	
		return true;
	}

	/*
	 * Removes the object of highest priority that has been in the PQ the
	 * longest, and returns it. Returns null if the PQ is empty.
	 */
	public E remove() {
		if(isEmpty()) return null;
		int where = searchPriority();
		E tmp = storage[where];
		for(int i = where; i < currentSize - 1; i++){
			storage[i] = storage[i + 1];
		}	
		currentSize--;
		return tmp;
	}

	/*
	 * Deletes all instances of the parameter obj from the PQ if found, and
	 * returns true. Returns false if no match to the parameter obj is found.
	 */
	public boolean delete(E obj) {
		if(!contains(obj)) return false;
		while(contains(obj)){
			find(obj);
		}
		return true;
	}

	/*
	 * Returns the object of highest priority that has been in the PQ the
	 * longest, but does NOT remove it. Returns null if the PQ is empty.
	 */
	public E peek() {
		if (isEmpty()) return null;
		return storage[searchPriority()];
	}

	/*
	 * Returns true if the priority queue contains the specified element false
	 * otherwise.
	 */
	public boolean contains(E obj) {
		for (E tmp : this)
			if (((Comparable<E>) obj).compareTo(tmp) == 0)
				return true;
		return false;
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

	private int searchPriority(){
		int tmp = 0; 
		for(int i = 1; i < currentSize; i++){
			if(storage[tmp].compareTo((storage[i])) > 0)
				tmp = i;
		}
		return tmp;
	}
	
	private void find(E obj){
		for (int i = 0; i < currentSize; i++){
			if(((Comparable<E>)obj).compareTo(storage[i]) == 0)
				shift(i);
		}		
	}
	
	private void shift(int where){
		for(int i = where; i < currentSize - 1; i++)
			storage[i] = storage[i + 1];                   
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
