package Hashing.HashSet;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashSet<E> implements Collection<E> {

    private static int DEFAULT_INITIAL_CAPACITY=4/* Define the default hash-table size. Must be a power of 2*/;


    private static int MAXIMUM_CAPACITY = 1 << 30/* Define the maximum hash-table size. 1 << 30 same as 2^30 */;


    private int capacity/*Current hash-table capacity. Capacity is a power of 2 */;


    private static float DEFAULT_MAX_LOAD_FACTOR = 0.75f/* Define default load factor */;


    private float loadFactorThreshold/* Specify a load-factor threshold used in the hash table */;


    private int size=0/* The number of elements in the set */;


    private LinkedList<E>[] table/* Hash table is an array with each cell being a linked list */;

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}
