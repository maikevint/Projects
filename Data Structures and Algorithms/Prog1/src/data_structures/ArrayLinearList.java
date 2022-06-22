/*
 *  Program 1
 *  Kevin Mai
 *  cssc0922
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayLinearList<E> implements LinearListADT<E> {
	private int currentSize, maxSize, front, rear;
	private E[] storage;

	// Constructor with no arguments that creates an array of
	// DEFAULT_MAX_CAPACITY.
	public ArrayLinearList() {
		this(DEFAULT_MAX_CAPACITY);
	}

	@SuppressWarnings({ "unchecked" })
	// Constructor that takes a maxCapacity parameter,
	public ArrayLinearList(int size) {
		maxSize = size;
		front = rear = currentSize = 0;
		storage = (E[]) new Object[maxSize];
	}

	// Adds the Object obj to the beginning of list and returns true if the list
	// is not full.
	// returns false and aborts the insertion if the list is full.
	public boolean addFirst(E obj) {
		if (isFull())
			return false;
		if (!isEmpty()) {
			front--;
			if (front < 0)
				front = maxSize - 1;
		}
		// empty case
		storage[front] = (E) obj;
		currentSize++;
		return true;
	}

	// Adds the Object obj to the end of list and returns true if the list is
	// not full.
	// returns false and aborts the insertion if the list is full..
	public boolean addLast(E obj) {
		if (isFull()) return false;
		if (!isEmpty()) {
			rear++;
			if (rear == maxSize - 1)
				rear = 0;
		}
		// empty case
		storage[rear] = (E) obj;
		currentSize++;
		return true;
	}

	// Removes and returns the parameter object obj in first position in list if
	// the list is not empty,
	// null if the list is empty.
	public E removeFirst() {
		if (isEmpty()) return null;
		E first = storage[front];
		if (front == rear)front = rear = 0; // one element case
		else {
			front++;
			if (front == maxSize)
				front = 0;
		}
		currentSize--;
		return first;
	}

	// Removes and returns the parameter object obj in last position in list if
	// the list is not empty,
	// null if the list is empty.
	public E removeLast() {
		if (isEmpty()) return null;
		E last = storage[rear];
		// one element case
		if (rear == front) rear = front = 0;
		else {
			rear--;
			if (rear < 0)
				rear = maxSize - 1;
		}
		currentSize--;
		return last;
	}

	// Removes and returns the parameter object obj from the list if the list
	// contains it, null otherwise.
	// The ordering of the list is preserved. The list may contain duplicate
	// elements. This method
	// removes and returns the first matching element found when traversing the
	// list from first position.
	// Note that you may have to shift elements to fill in the slot where the
	// deleted element was located.
	@SuppressWarnings({ "unchecked" })
	public E remove(E obj) {
		if (isEmpty())return null;
		if (find(obj) == null) return null;    // element does not exist
		int where = -1;
		int start = front;
		for (int i = 0; i < currentSize; i++) {
			if (((Comparable<E>) obj).compareTo(storage[start]) == 0) {
				where = start;
				break;
			}
			start++;
			if (start == maxSize - 1 )
				start = 0;
		}
		E tmp = storage[where];
		if (storage[where] == storage[front]) removeFirst();
		else if (storage[where] == storage[rear]) removeLast();
		else {
			while (where != rear) {
				if (where == maxSize - 1) {
					storage[where] = storage[0];
					where = 0;
				}
				storage[where] = storage[where + 1];
				where++;
			}
			rear--;
			if (rear < 0)
				rear = maxSize - 1;
			currentSize--;
		}
		return tmp;
	}

	// Returns the first element in the list, null if the list is empty.
	// The list is not modified.
	public E peekFirst() {
		if (isEmpty())
			return null;
		return storage[front];
	}

	// Returns the last element in the list, null if the list is empty.
	// The list is not modified.
	public E peekLast() {
		if (isEmpty())
			return null;
		return storage[rear];
	}

	// Returns true if the parameter object obj is in the list, false otherwise.
	// The list is not modified.
	// REMOVE: uses iterator
	public boolean contains(E obj) {
		if (isEmpty())
			return false;
		return find(obj) != null;
	}

	// Returns the element matching obj if it is in the list, null otherwise.
	// In the case of duplicates, this method returns the element closest to
	// front.
	// The list is not modified.
	@SuppressWarnings({ "unchecked" })
	public E find(E obj) {
		if (isEmpty())
			return null;
		for (E tmp : this)
			if (((Comparable<E>) obj).compareTo(tmp) == 0)
				return tmp;
		return null;
	}

	// The list is returned to an empty state.
	public void clear() {
		currentSize = front = rear = 0;
	}

	// Returns true if the list is empty, otherwise false
	public boolean isEmpty() {
		return currentSize == 0;
	}

	// Returns true if the list is full, otherwise false
	public boolean isFull() {
		return currentSize == maxSize;
	}

	// Returns the number of Objects currently in the list.
	public int size() {
		return currentSize;
	}

	// Returns an Iterator of the values in the list, presented in
	// the same order as the underlying order of the list. (front first, rear
	// last)
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}

	class IteratorHelper implements Iterator<E> {
		private int count, index;

		public IteratorHelper() {
			index = front;
			count = 0;
		}

		public boolean hasNext() {
			return count != currentSize;
		}

		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			E tmp = storage[index++];
			if (index == maxSize)
				index = 0;
			count++;
			return tmp;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
