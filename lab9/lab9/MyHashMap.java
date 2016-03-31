package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by Jesse on 3/17/2016.
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private class Pair<K, V> extends Object {

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        private K key;
        private V value;

        Pair(K k, V v) {
            key = k;
            value = v;
        }

    }

    // Array that holds linkedLists that holds Pairs that holds keys and values
    private LinkedList<Pair>[] hashMap;
    // Average number of iterations through the linkedlist on an array
    private double loadFactor = 2;
    // The amount by which the size of the array is
    private int resizeFactor = 2;
    private static final int STARTINGARRAYSIZE = 10;
    // How many items are in the map
    // needs to be incremented every time something is added or decremented when removing
    private int mapSize = 0;
    private HashSet<K> keys;

    public MyHashMap() {
        hashMap = new LinkedList[STARTINGARRAYSIZE];
        // Initializes the LinkedLists in the Array
        for (int i = 0; i < hashMap.length; i++) {
            hashMap[i] = new LinkedList<Pair>();
        }
        keys = new HashSet<K>();
    }
    public MyHashMap(int initialSize) {
        hashMap = new LinkedList[initialSize];
        // Initializes the LinkedLists in the Array
        for (int i = 0; i < hashMap.length; i++) {
            hashMap[i] = new LinkedList<Pair>();
        }
        keys = new HashSet<K>();
    }
    public MyHashMap(int initialSize, double loadFactor) {
        hashMap = new LinkedList[initialSize];
        // Initializes the LinkedLists in the Array
        for (int i = 0; i < hashMap.length; i++) {
            hashMap[i] = new LinkedList<Pair>();
        }
        this.loadFactor = loadFactor;
        keys = new HashSet<K>();
    }

    /** Removes all of the mappings from this map. */
    public void clear() {
        hashMap = new LinkedList[hashMap.length];
        // Initializes the LinkedLists in the Array
        for (int i = 0; i < hashMap.length; i++) {
            hashMap[i] = new LinkedList<Pair>();
        }
        keys.clear();
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        if (!keys.contains(key)) {
            return null;
        } else {
            int hashCode = key.hashCode();
            int insertPosition = hashCode % hashMap.length;
            LinkedList<Pair> chain = hashMap[insertPosition];
            for (Pair<K, V> pair : chain) {
                if (pair.key.equals(key)) {
                    return pair.value;
                }
            }
        }
        throw new RuntimeException("something messed up in get");
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return keys.size();
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        int hashCode = key.hashCode();
        int insertPosition = hashCode % hashMap.length;

        if (!keys.contains(key)) {
            hashMap[insertPosition].add(new Pair(key, value));
            keys.add(key);
            mapSize++;
            if (getCurrentLoadFactor() > loadFactor) { // if the load factor is too big, resize
                resize();
            }
        } else {
            LinkedList<Pair> chain = hashMap[insertPosition];
            // If the key already exists, replace the item at the key with the new value
            for (Pair pair : chain) {
                if (pair.key.equals(key)) {
                    pair.value = value;
                    return;
                }
            }
            // if the loop finishes and it doesn't replace a value at a key, something when wrong
            throw new java.lang.RuntimeException("something went wrong with put");
        }
    }

    private double getCurrentLoadFactor() {
        return ((double) mapSize) / ((double) hashMap.length);
    }

    private void resize() {
        LinkedList[] oldHashMap = hashMap;
        hashMap = new LinkedList[oldHashMap.length * resizeFactor];

        for (int i = 0; i < hashMap.length; i++) {
            hashMap[i] = new LinkedList<Pair>();
        }

        for (int i = 0; i < oldHashMap.length; i++) {
            LinkedList<Pair> chain = hashMap[i];
            if (chain.size() != 0) {
                for (Pair<K, V> pair : chain) {
                    put(pair.key, pair.value);
                }
            }
        }
    }

    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        return keys;
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public Iterator<K> iterator() {
        return keys.iterator();
    }
}
