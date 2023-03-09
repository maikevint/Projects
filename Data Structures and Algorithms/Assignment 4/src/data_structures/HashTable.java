/*
 *  Program 4
 *  Kevin Mai
 *  cssc0918
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class HashTable<K extends Comparable<K>, V> implements DictionaryADT<K, V> {
	private long modCounter; 
	private int currSize, maxSize, tableSize;
	private UnorderedLinkedList<DictionaryNode<K,V>> [] hashTable;

    // Code taken from UnorderedLinkedListpriorityQueue program
	private class UnorderedLinkedList<E extends Comparable<E>> implements Iterable<E> {
		private Node<E> head;
		private int currentSize;
		//private long modCounter;

		protected class Node<T> {
			T data;
			Node<T> next;

			public Node(T obj) {
				data = obj;
				next = null;
			}
		} // end class Node

		// constructor
		public UnorderedLinkedList() {
			head = null;
			currentSize = 0;
			//modCounter = 0;
		}

		// Inserts a new object into the priority queue. Returns true if
		// the insertion is successful. If the PQ is full, the insertion
		// is aborted, and the method returns false.
		public boolean insert(E object) {
			Node<E> newNode = new Node<E>(object);
			if (head == null)
				head = newNode; // Empty Case
			else {
				newNode.next = head;
				head = newNode;
			}
			currentSize++;
			//modCounter++;
			return true;
		}

		// Removes the object of highest priority that has been in the
		// PQ the longest, and returns it. Returns null if the PQ is empty.
		public E remove() {
			if (isEmpty())
				return null;
			Node<E> priority = null, previous = null, current = head;
			E tmp1 = current.data;
			while (current != null) { // traverse the list
				if (current.data.compareTo(tmp1) <= 0) { // find highest priority
					priority = previous;     // stores the location for removal
					tmp1 = current.data;
				}
				previous = current;
				current = current.next;
			}
			if (priority == previous)  
				priority = current; // tail case
			else if (priority == current) // head case
				head = head.next;
			else
				priority.next = priority.next.next;
			currentSize--;
			//modCounter++;
			return tmp1;
		}

		// Deletes instance of the parameter obj from the PQ if found, and
		// returns true. Returns false if no match to the parameter obj is found.
		public boolean delete(E obj) {
			Node<E> previous = null, current = head;		
			if (!contains(obj))
				return false;
			while (current != null && ((Comparable<E>) obj).compareTo(current.data) != 0) {
				previous = current;
				current = current.next;
			}
			// if current != null, then node was found
			if (current == null) {
				return false; // ran off the end, node not found or empty list
			}
			if (current.next == null) {   // tail 
				previous.next = null;
				current = previous;
			} else if (current == head){  // is in the head		
				head = head.next;
				current = head;
			}
			else{ // the node to remove is in the middle
				previous.next = current.next;
				current = previous;
			}
			currentSize--;
			//modCounter++;
			return true;
		}

		// Returns true if the priority queue contains the specified element
		// false otherwise.
		public boolean contains(E obj) {
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

		// Returns the number of objects currently in the PQ.
		public int size() {
			return currentSize;
		}

		// Returns the PQ to an empty state.
		public void clear() {
			head = null;
			currentSize = 0;
			//modCounter = 0;
		}

		// Returns true if the PQ is empty, otherwise false
		public boolean isEmpty() {
			return currentSize == 0;
		}

		// Returns true if the PQ is full, otherwise false. List based
		// implementations should always return false.
		public boolean isFull() {
			return false;
		}
		
		public Iterator<E> iterator() {
			return new IteratorHelper();
		}

		class IteratorHelper implements Iterator<E> {
			private Node<E> iterPtr;
			//private long modCheck;

			public IteratorHelper() {
				//modCheck = modCounter;
				iterPtr = head;
			}

			public boolean hasNext() {
				//if (modCheck != modCounter)
					//throw new ConcurrentModificationException();
				return iterPtr != null;
			}

			public E next() {
				if (!hasNext())
					throw new NoSuchElementException();
				E tmp = iterPtr.data;
				iterPtr = iterPtr.next;
				return tmp;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		}
	}
	
	// Wrapper class for chaining
	private class DictionaryNode<k,v> implements Comparable<DictionaryNode<K,V>>{
		K key;
		V value;
		
		
		public DictionaryNode(K k, V v){
			key = k;
			value = v;
		}
		public int compareTo(DictionaryNode<K, V> node) {
			if(node.key != null) return((Comparable<K>)key).compareTo((K)node.key);
			return((Comparable<V>) value).compareTo((V)node.value);
		}
	}

		
	// constructor
	public HashTable(int size){
		maxSize = size;
		modCounter = 0;
        tableSize = (int)(maxSize * 1.3f);
        hashTable = new UnorderedLinkedList[tableSize];
        for(int i = 0; i < tableSize; i++)
            hashTable[i] = new UnorderedLinkedList<>();
	}
	
	// gets index from the hashCode
	private int getIndex(K key){
	       return (key.hashCode() & 0x7FFFFFFF) % maxSize;
	    }

	// Returns true if the dictionary has an object identified by
	// key in it, otherwise false.
	public boolean contains(K key){
		DictionaryNode<K,V> tmp = new DictionaryNode<>(key,null);
        return hashTable[getIndex(key)].contains(tmp);
	}

	// Adds the given key/value pair to the dictionary.  Returns 
	// false if the dictionary is full, or if the key is a duplicate.
	// Returns true if addition succeeded.
	public boolean add(K key, V value){
        DictionaryNode<K,V> tmp = new DictionaryNode<>(key, value);
        DictionaryNode<K,V> dupeValue = hashTable[getIndex(key)].find(tmp); //checks for duplicate
        if(dupeValue != null) return false;
        hashTable[getIndex(key)].insert(tmp);
        currSize++;
        modCounter++;
        return true;
	}

	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	public boolean delete(K key){
		if(contains(key)){
			DictionaryNode<K,V> tmp = new DictionaryNode<>(key,null);
           	hashTable[getIndex(key)].delete(tmp);
			currSize--;
			modCounter++;
			return true;
		}
		return false;
	}

	// Returns the value associated with the parameter key.  Returns
	// null if the key is not found or the dictionary is empty.
	public V getValue(K key){
		int index = getIndex(key);
        DictionaryNode<K,V> tmp = hashTable[index].find(new DictionaryNode<>(key,null));
        if(tmp == null) return null;
        return tmp.value;
	}

	// Returns the key associated with the parameter value.  Returns
	// null if the value is not found in the dictionary.  If more 
	// than one key exists that matches the given value, returns the
	// first one found. 
	public K getKey(V value){
		for(int i = 0; i < tableSize; i++) {
            for(DictionaryNode<K,V> tmp : hashTable[i]) {
                if(((Comparable<V>)tmp.value).compareTo(value) == 0)
                    return tmp.key;
            }
        }
        return null;
	}

	// Returns the number of key/value pairs currently stored 
	// in the dictionary
	public int size(){
		return currSize;
	}

	// Returns true if the dictionary is at max capacity
	public boolean isFull(){
		return currSize == maxSize;
	}

	// Returns true if the dictionary is empty
	public boolean isEmpty(){
		return currSize == 0;
	}

	// Returns the Dictionary object to an empty state.
	public void clear(){
		// makes the array empty
		hashTable = new UnorderedLinkedList[tableSize];
		// ensures the linked list in ea array element empty
		for(int i = 0; i < tableSize; i++)
            hashTable[i].clear();
        currSize = 0;
        modCounter = 0;
	}

	// Returns an Iterator of the keys in the dictionary, in ascending
	// sorted order.  The iterator must be fail-fast.
	public Iterator<K> keys(){
		return new KeyIteratorHelper();
	}

	// Returns an Iterator of the values in the dictionary.  The
	// order of the values must match the order of the keys. 
	// The iterator must be fail-fast. 
	public Iterator<V> values(){
		return new ValueIteratorHelper();
	}
	
	// inheritance table iterator
	abstract class IteratorHelper<E> implements Iterator<E> {
		protected DictionaryNode<K,V>[] nodes; 
		protected int idx;
		protected long modCheck;
		
		public IteratorHelper () {
			nodes = new DictionaryNode[currSize];
			idx = 0;
			int j = 0;
			modCheck = modCounter;
			for(int i=0; i < maxSize; i++)
				for(DictionaryNode n : hashTable[i])
					nodes[j++] = n;
			//nodes = (DictionaryNode<K,V>[]) ObjectSorter.quickSort(nodes);
			shellSort(nodes);
		}
		
		private DictionaryNode<K,V>[] shellSort(DictionaryNode<K,V>[] nodes) {
			DictionaryNode<K,V>[] n = nodes;
			DictionaryNode<K,V> tmp;
			int in, out, h=1;	
			
			while(h <= currSize/3)  //calculate gaps 
				h = h*3 + 1;
			while( h > 0){
				for(out = h; out < currSize; out++){
					tmp = n[out];
					in = out;           
					while(in > h-1 && (((Comparable<K>)n[in].key).compareTo(tmp.key) >= 0 )){
						n[in] = n[in-h];
						in -= h;
					}
					n[in] = tmp;
				}  // end for
				h = (h-1)/3;
			}  // end while
			return n;
		}
		
		public boolean hasNext () {
			if(modCheck != modCounter)
				throw new ConcurrentModificationException();
			return idx < currSize;
		}
		
		public abstract E next();
		
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
	
	class KeyIteratorHelper<K> extends IteratorHelper<K>{
		public KeyIteratorHelper(){
			super();
		}
		
		public K next(){
			return (K) nodes[idx++].key;
		}
	}
	
	class ValueIteratorHelper<V> extends IteratorHelper<V>{
		public ValueIteratorHelper(){
			super();
		}
		
		public V next(){
			return (V) nodes[idx++].value;
		}
	}	
}
