package Hashing.Map;

public interface MyMap<K,V> {
    /*Remove all the entries from this map*/
    public void clear();
    /*Return true if this map contains the specified key is in the map*/
    public boolean containsKey(K key);
    /*Return true of this map contains the specified value*/
    public boolean containsValues(V value);

    /*Return a set of entries in the map */
    public java.util.Set<Entry<K,V>> entrySet();

    /*Return the value that matches the specified key*/
    public V get(K key);

    /* Return the value that matches the specified key*/
    public boolean isEmpty();
    /* Return the value that matches the specified key*/
    public java.util.Set<K> keySet();
    /*Add an entry (key, value) into map*/
    public V put(K key, V value);
    /*Remove an entry for the specified key */
    public void remove(K key);
    /* Return the number of mappings in this map*/
    public int size();
    /*Returns a set consisting of values in this map*/
    public java.util.Set<V>values();

    /*Define an inner class for Entry*/

    public static class Entry<K, V> {

        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }
}
