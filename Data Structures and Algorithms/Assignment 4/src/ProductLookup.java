/*
 *  Program 4
 *  Kevin Mai
 *  cssc0918
*/

import data_structures.*;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ProductLookup<K extends Comparable <K>,V> {
	private int size;
	private DictionaryADT<K,V> product;
   
    // Constructor.  There is no argument-less constructor, or default size
    public ProductLookup(int maxSize){
    	size = maxSize;
    	product =  new HashTable<>(maxSize);
    			  // new BinarySearchTree<>();
    			  // new BalancedTree<>();
    }

    // Adds a new StockItem to the dictionary
    public void addItem(String SKU, StockItem item){
    	product.add((K)SKU,(V) item);
    }
          
    // Returns the StockItem associated with the given SKU, if it is
    // in the ProductLookup, null if it is not.
    public StockItem getItem(String SKU){
    	return (StockItem)product.getValue((K)SKU);
    }
       
    // Returns the retail price associated with the given SKU value.
    // -.01 if the item is not in the dictionary
    public float getRetail(String SKU) {
    	StockItem retail = (StockItem) product.getValue((K)SKU);
    	if(retail == null)
    		return -.01f;
    	return retail.getRetail();
    }
    
    // Returns the cost price associated with the given SKU value.
    // -.01 if the item is not in the dictionary
    public float getCost(String SKU) {
    	StockItem cost = (StockItem) product.getValue((K)SKU);
    	if(cost == null)
    		return -.01f;
    	return cost.getCost();
    }
    
    // Returns the description of the item, null if not in the dictionary.
    public String getDescription(String SKU) {
    	StockItem description = (StockItem) product.getValue((K)SKU);
    	return description.getDescription();
    }
       
    // Deletes the StockItem associated with the SKU if it is
    // in the ProductLookup.  Returns true if it was found and
    // deleted, otherwise false.  
    public boolean deleteItem(String SKU){
    	if(product.delete((K) SKU))
    			return true;
    	return false;
    }
    
    // Prints a directory of all StockItems with their associated
    // price, in sorted order (ordered by SKU).   
    // Prints a directory of all StockItems with their associated
    // price, in sorted order (ordered by SKU).
    public void printAll(){
    	Iterator<StockItem> iter = (Iterator<StockItem>) product.values(); 
    	StockItem tmp;
    	while(iter.hasNext()){
    		tmp = iter.next();
    		System.out.println(tmp.toString());
    	}
    }
    
    // Prints a directory of all StockItems from the given vendor, 
    // in sorted order (ordered by SKU).
       
    // Prints a directory of all StockItems from the given vendor, 
    // in sorted order (ordered by SKU).
    public void print(String vendor){
    	Iterator<StockItem> iter = (Iterator<StockItem>) product.values();
    	StockItem seller = null;
    	while(iter.hasNext()){
    		seller = iter.next();
    		if(((Comparable<String>) seller.vendor).compareTo(vendor) == 0)
    			System.out.println(seller.toString());
    	}
    }
    
    // An iterator of the SKU keys.
   
    // An iterator of the SKU keys.
    public Iterator<String> keys(){
    	if(product.size() == 0){
    		System.out.println("Empty stock.");
    		return null;
    	}
    	return (Iterator<String>) product.keys();
    }
    
    // An iterator of the StockItem values.  
    
    // An iterator of the StockItem values.    
    public Iterator<StockItem> values(){
    	if(product.size() == 0){
    		System.out.println("Empty stock.");
    		return null;
    	}
    	return (Iterator<StockItem>) product.values();
    }
}	