package lab8;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class Node {
        private K key;           // sorted by key
        private V val;         // associated data
        private Node left, right;  // left and right subtrees

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    private Node root;

    public BSTMap() {
        root = null;
    }
    /** Removes all of the mappings from this map. */
    public void clear() {
        root = null;
    };

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return containsKey(key, root);
    }

    // Helper method
    private boolean containsKey(K key, Node node) {
        if (node == null) {
            return false;
        } else if (key.equals(node.key)) {
            return true;
        } else {
            return containsKey(key, node.left) || containsKey(key, node.right);
        }
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return get(key, root);
    }

    private V get(K key, Node node) {
        if (node == null) {
            return null;
        } else if (key.equals(node.key)) {
            return node.val;
        } else {
            if (key.compareTo(node.key) < 0) {
                return get(key, node.left);
            } else {
                return get(key, node.right);
            }
        }
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + size(node.left) + size(node.right);
        }
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (root == null) {
            root = new Node(key, value);
            return;
        }
        Node node = root;
        while (node != null) {
            if (key.compareTo(node.key) < 0 && node.left == null) {
                node.left = new Node(key, value);
                return;
            } else if (key.compareTo(node.key) > 0 && node.right == null) {
                node.right = new Node(key, value);
                return;
            } else if (key.compareTo(node.key) < 0) {
                node = node.left;
            } else if (key.compareTo(node.key) > 0) {
                node = node.right;
            } else {
                node.val = value;
                return;
            }
        }
    }

    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
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

    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }
}
