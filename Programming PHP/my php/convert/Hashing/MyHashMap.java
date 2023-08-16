package Hashing.HashMap;

import Hashing.Map.MyMap;

import java.util.LinkedList;
import java.util.Set;

public class MyHashMap<K,V> implements MyMap<K,V> {
    private static int DEFAULT_INITIAL_CAPACITY = 4;
    private static int MAXIMUM_CAPACITY=1<<30;
    private int capacity;
    private static float DEFAULT_MAX_LOAD_FACTOR=0.75F;
    private float loadFactorThreshold;
    private int size=0;
    LinkedList<MyMap.Entry<K,V>>[] table;

    public MyHashMap()
    {
        this(DEFAULT_INITIAL_CAPACITY,DEFAULT_MAX_LOAD_FACTOR);
    }
    public MyHashMap(int initialCapacity)
    {
        this(initialCapacity,DEFAULT_MAX_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity, float loadFactorThreshold)
    {
        if(initialCapacity > MAXIMUM_CAPACITY)
            this.capacity = MAXIMUM_CAPACITY;
        else
            this.capacity = trimToPowerOf2(initialCapacity);

        this.loadFactorThreshold=loadFactorThreshold;
        table=new LinkedList[capacity];
    }


    @Override
    public void clear() {
    size =0;
    removeEnrties();
    }
    public boolean containsKey(K key)
    {
        return get(key) != null;
    }


    @Override
    public boolean containsValues(V value) {
        for (int i = 0; i < capacity; i++) {
            if(table[i] != null)
            {
                LinkedList<Entry<K,V>> bucket = table[i];

                for(Entry<K,V> entry: bucket)
                    if(entry.getValue().equals(value))
                        return true;
            }
        }

        return false;
    }

    @Override
    public java.util.Set<Entry<K, V>> entrySet() {
           java.util.Set<MyMap.Entry<K,V>> set = new java.util.HashSet<>();

           for(int i=0; i<capacity; i++)
           {
               if(table[i] != null)
               {
                   LinkedList<Entry<K,V>> bucket = table[i];
                   for(Entry<K,V> entry: bucket)
                       set.add(entry);
               }
           }
        return set;
    }

    @Override
    public V get(K key) {
        int bucketIndex = hash(key.hashCode());
        if(table[bucketIndex] != null)
        {
            LinkedList<Entry<K,V>> bucket = table[bucketIndex];

            for(Entry<K,V> entry: bucket)
                if(entry.getKey().equals(key))
                    return entry.getValue();
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public java.util.Set<K> keySet() {
        java.util.Set<K> set = new java.util.HashSet<>();

        for (int i = 0; i < capacity; i++) {
            if(table[i] != null)
            {
                LinkedList<Entry<K,V>> bucket = table[i];

                for(Entry<K,V> entry: bucket)
                    set.add(entry.getKey());
            }
        }
        return set;
    }

    @Override
    public V put(K key, V value) {
        if(get(key) != null)
        {
            int bucketIndex = hash(key.hashCode());
            LinkedList<Entry<K,V>> bucket = table[bucketIndex];

            for(Entry<K,V> entry: bucket)
                if(entry.getKey().equals(key)){
                    V oldValue = entry.getValue()/*Replace old value with new value*/;

                    entry.setValue(value);/* Return the old value for the key*/;

                    return oldValue;
                }
        }

        if(size >= capacity * loadFactorThreshold /*Check load factor */)
        {
            if(capacity == MAXIMUM_CAPACITY)
                throw new RuntimeException("Exceeding maximum capacity");
            rehash();
        }
    int bucketIndex = hash(key.hashCode());

        if(table[bucketIndex] == null)/*Create a linked list for bucket */
        {
            table[bucketIndex] = new LinkedList<Entry<K,V>>()/*Create a linked list for the bucket */;

        }

        table[bucketIndex].add(new MyMap.Entry<K,V>(key,value))/*Add a bew entry (key, value) to hashTable[index] */;

        size++/*Increase size*/;
        return value;
    }

    @Override
    public void remove(K key/*Remove the entries for the specified key */) {
    int bucketIndex = hash(key.hashCode());

    // Remove the first entry that matches the key from a bucket

        if (table[bucketIndex] != null) {
            LinkedList<Entry<K,V>> bucket = table[bucketIndex];

            for(Entry<K,V> entry: bucket)
                if(entry.getKey().equals(key))
                {
                    bucket.remove(entry);
                    size--;
                    break /*Remove just one entry that matches the key*/;
                }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public java.util.Set<V> values() {
        java.util.Set<V> set = new java.util.HashSet<>();

        for (int i = 0; i < capacity; i++) {
            if(table[i] != null)
            {
                if(table[i] != null)
                {
                    LinkedList<Entry<K,V>> bucket = table[i];

                    for(Entry<K,V> entry : bucket)
                        set.add(entry.getValue());
                }
            }

        }
        return set;
    }

    private int hash(int hashCode)
    {
        /*WTF IS THIS, RETURNING 2 VARS?*/
        return supplementalHash (hashCode) & (capacity - 1);
    }
    private static int supplementalHash(int h) /*Ensure the hashing is evenly distributed */
    {
        /* wow, that's beautiful */
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private int trimToPowerOf2(int initialCapacity)
    {
        int capacity=1;

        while(capacity < initialCapacity)
        {
            capacity <<=1/*Same as capacity  *= 2 <= is more efficient*/;
        }
        return capacity;
    }

    private void removeEnrties()/*Remove all entries from each bucket */
    {
        for(int i=0; i<capacity; i++)
        {
            if(table[i] != null) {
                table[i].clear();
            }
        }
    }

    private void rehash()/*Rehash the map */
    {
        java.util.Set<Entry<K,V>> set = entrySet()/*Get entries */;

        capacity <<=1 /*Same as capacity*=2. <<= is more efficient */;
        table = new LinkedList[capacity] /*Create a new hash table */;

        size=0/*Reset size to 0 */;

        for(Entry<K,V> entry : set)
        {
            put(entry.getKey(), entry.getValue())/*Store to new table */;
        }
    }

    public String toString()
    {
        //* Omg this is such a fast 2 string statement  */
        /* Super cool */
        StringBuilder builder = new StringBuilder("[");

        for(int i=0; i<capacity; i++)
            if(table[i] != null && table[i].size() > 0)
                for(Entry<K,V> entry: table[i])
                    builder.append(entry.toString()).append(", ");
        if (builder.length() > 1)
            builder.delete(builder.length() - 2, builder.length())/* Remove the trailing comma and space */;
        builder.append("]");
        return builder.toString();
    }
}
