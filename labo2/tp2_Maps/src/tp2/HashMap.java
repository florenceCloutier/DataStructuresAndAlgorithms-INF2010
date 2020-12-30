/*
 * Implementation de la classe HashMap
 * file HashMap.java
 * authors Alexis Foulon et Florence Cloutier
 *
 * Ce programme contient les methodes qui ont pour but la construction de la classe
 * HashMap et l'implementation des methodes de cette derniere.
 */

package tp2;

public class HashMap<KeyType, DataType> {

    private static final int DEFAULT_CAPACITY = 20;
    private static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final int CAPACITY_INCREASE_FACTOR = 2;

    private Node<KeyType, DataType>[] map;
    private int size = 0;
    private int capacity;
    private final float loadFactor; // Compression factor

    /**
     * Constructeur par défaut
     */
    public HashMap() { this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR); }

    /**
     * Constructeur par parametre
     * @param initialCapacity
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity > 0 ? initialCapacity : DEFAULT_CAPACITY,
                DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructeur par parametres
     * @param initialCapacity
     * @param loadFactor
     */
    public HashMap(int initialCapacity, float loadFactor) {
        capacity = initialCapacity;
        this.loadFactor = 1 / loadFactor;
        map = new Node[capacity];
    }

    /**
     * Finds the index attached to a particular key
     * This is the hashing function ("Fonction de dispersement")
     * @param key Value used to access to a particular instance of a DataType within map
     * @return Index value where this key should be placed in attribute map
     */
    private int hash(KeyType key){
        int keyHash = key.hashCode() % capacity;
        return Math.abs(keyHash);
    }

    /**
     * @return if map should be rehashed
     */
    private boolean needRehash() {
        return size * loadFactor > capacity;
    }

    /**
     * @return Number of elements currently in the map
     */
    public int size() {
        return size;
    }

    /**
     * @return Current reserved space for the map
     */
    public int capacity(){ return capacity; }

    /**
     * @return if map does not contain any element
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Increases capacity by CAPACITY_INCREASE_FACTOR (multiplication) and
     * reassigns all contained values within the new map
     */
    private void rehash() {
        capacity *= CAPACITY_INCREASE_FACTOR;
        Node<KeyType, DataType>[] old = map;
        Node<KeyType, DataType>[] newMap = new Node[capacity];
        map = newMap;
        size = 0;

        for (Node<KeyType, DataType> nodeMap: old) {
            for (; nodeMap != null; nodeMap = nodeMap.next) {
                put(nodeMap.key, nodeMap.data);
            }
        }
    }

    /**
     * Finds if map contains a key
     * @param key Key which we want to know if exists within map
     * @return if key is already used in map
     */
    public boolean containsKey(KeyType key) {
        for (Node<KeyType, DataType> element = map[hash(key)]; element != null; element = element.next) {
            if (key.equals(element.key))
                return true;
        }

        return false;
    }

    /**
     * Finds the value attached to a key
     * @param key Key which we want to have its value
     * @return DataType instance attached to key (null if not found)
     */
    public DataType get(KeyType key) {
        for (Node<KeyType, DataType> element = map[hash(key)]; element != null; element = element.next) {
            if (key.equals(element.key))
                return element.data;
        }

        return null;
    }

    /**
     * Assigns a value to a key
     * @param key Key which will have its value assigned or reassigned
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType put(KeyType key, DataType value) {
        int hash = hash(key);


        for (Node<KeyType, DataType> element = map[hash]; element != null; element = element.next) {
            if (key.equals(element.key)) {
                DataType old = element.data;
                element.data = value;
                return old;
            }
            else if (element.next == null) {
                element.next = new Node<KeyType, DataType>(key, value);
                size++;
                return null;
            }
        }

        map[hash] = new Node<KeyType, DataType>(key, value); //Add new element to de HashMap
        size++;                                              //Increase the size

        if (size * loadFactor > capacity) //Check if we need to rehash
            rehash();

        return null;
    }

    /**
     * Removes the node attached to a key
     * @param key Key which is contained in the node to remove
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType remove(KeyType key) {
        int hash = hash(key);
        Node<KeyType, DataType> element = map[hash];

        //If there are no element at this hash return null
        if (element == null)
            return null;

        //If the first element is to be removed change where map[hash] is pointing
        if (key.equals(element.key)) {
            DataType old = element.data;
            map[hash] = element.next;
            size--;
            return old;
        }

        //Look through the list if items match the key and remove them
        for(;element.next != null; element = element.next) {
            if (key.equals(element.next.key)) {
                DataType old = element.next.data;
                element.next = element.next.next;
                size--;
                return old;
            }
        }

        return null;
    }

    /**
     * Removes all nodes contained within the map
     */
    public void clear() {
        map = new Node[capacity];
        size = 0;
    }

    /**
     * Definition et implementation de la classe Node
     */
    static class Node<KeyType, DataType> {
        final KeyType key;
        DataType data;
        Node<KeyType, DataType> next; // Pointer to the next node within a Linked List

        Node(KeyType key, DataType data)
        {
            this.key = key;
            this.data = data;
            next = null;
        }
    }
}