import java.util.ArrayList;

public class MyHashTable<K, V> {
    ArrayList<Bucket> buckets;
    int size;
    int bucketCount;

    MyHashTable(int capacity) {
        size = capacity;
        buckets = new ArrayList<>(capacity);
        bucketCount = 0;
    }


    void put(K searchKey, V newValue) {
        int hashCode = hash(searchKey);
        //update or add the newValue to the bucket hash(searchKey)
        //if hash(key) is full use linear probing to find the next available bucket

    }
    V get(K searchKey) {
        //return a value for the specified key from the bucket hash(searchKey)
        //if hash(searchKey) doesn’t contain the value use linear probing to find the
        //appropriate value
    }
    boolean containsKey(K searchKey) {
        int hashCode = hash(searchKey);
        //return true if there is a value stored for searchKey
    }
    void stats() {

    }
    private int hash(K key) {
        return Math.abs(key.hashCode() % size);
    }
    String toString() {

    }

    class Bucket<K, V> {
        final int hashCode;
        K key;
        V value;

        Bucket(K theKey, V theValue, int theHashCode) {
            key = theKey;
            value = theValue;
            hashCode = theHashCode;
        }
    }
}
