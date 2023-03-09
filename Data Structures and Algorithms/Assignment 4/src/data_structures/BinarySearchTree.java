/*
 *  Program 4
 *  Kevin Mai
 *  cssc0918
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class BinarySearchTree<K extends Comparable<K>, V> implements DictionaryADT<K, V> {
	
	private class Node<K,V> {
		private K key;					 // data item (key)
		private V value;				 // data item
		private Node<K,V> leftChild;	 // this node's left child
		private Node<K,V> rightChild;    // this node's right child
		
		public Node(K k, V v){
			key = k;
			value = v;
			leftChild = rightChild = null;
		}
	}   // end class node
	
	private Node<K,V> root;		
	private int currSize;
	private long modCounter;
	
	public BinarySearchTree(){			 // constructor
		root = null;
		currSize = 0;
		modCounter = 0;
	}
	
	// Returns true if the dictionary has an object identified by
	// key in it, otherwise false.
	public boolean contains(K key){
		if (getValue(key) != null)
			return true;
		return false;
	}
	
	// Adds the given key/value pair to the dictionary.  Returns 
	// false if the dictionary is full, or if the key is a duplicate.
	// Returns true if addition succeeded.
	public boolean add(K key, V value){
		//checks for duplicates
		if(contains(key)) return false; 
		if(root == null)
			root = new Node<K,V>(key, value);
		else
			insert(key, value, root, null, false);
		currSize++;
		modCounter++;
		return true;
	}
	
	// recursive insertion helper
	private void insert(K k, V v, Node<K,V> n, Node<K,V> parent, boolean wasLeft){
		if(n == null){			// at a left node so do insert.
			if(wasLeft) parent.leftChild = new Node<K,V>(k,v);
			else parent.rightChild = new Node<K,V> (k,v);
		}
		else if(((Comparable<K>) k).compareTo((K)n.key) < 0)
			insert(k,v,n.leftChild, n, true); 	// go left
		else
			insert(k,v,n.rightChild, n, false); // go right
	}

	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	public boolean delete(K key){
		// zero children case
		//		do something
		// one children case 
		//		do something
		// two children case
		//		do something
		currSize--;
		modCounter++;
		return false;
	}

	// Returns the value associated with the parameter key.  Returns
	// null if the key is not found or the dictionary is empty.
	public V getValue(K key){
		return find(key, root);
	}
	
	private V find(K key, Node<K,V> n){
		if(n == null) return null;
		if(key.compareTo(n.key) < 0)
			return find(key, n.leftChild);	// go left
		if(key.compareTo(n.key) > 0)
			return find(key, n.rightChild);	// go right
		return (V) n.value;
	}

	// Returns the key associated with the parameter value.  Returns
	// null if the value is not found in the dictionary.  If more 
	// than one key exists that matches the given value, returns the
	// first one found. 
	public K getKey(V value){
		Node<K,V> current;
		current = root;
		while (current != null) {
			if ( ((Comparable<V>)value).compareTo(current.value) == 0)
				break;
			else if (((Comparable<V>)value).compareTo(current.value) < 0)
				current = current.leftChild;
			else 
				current = current.rightChild;
		}
		if (current == null) return null;
		return current.key;
	}

	// Returns the number of key/value pairs currently stored 
	// in the dictionary
	public int size(){
		return currSize;
	}

	// Returns true if the dictionary is at max capacity
	// BST returns false due to being a linked structure
	public boolean isFull(){
		return false;
	}

	// Returns true if the dictionary is empty
	public boolean isEmpty(){
		return currSize == 0;
	}

	// Returns the Dictionary object to an empty state.
	public void clear(){
		root = null;
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
		protected Node<K,V>[] nodes; 
		protected int idx;
		protected long modCheck;
		
		public IteratorHelper () {
			
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