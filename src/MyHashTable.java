import java.util.ArrayList;

public class MyHashTable<K, V> {
    ArrayList<Bucket<K,V>> bucketList;
    ArrayList<Integer> probes = new ArrayList<>();
    int buckets;
    int entries;

    MyHashTable(int capacity) {
        buckets = capacity;
        bucketList = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            bucketList.add(null);
        }
        entries = 0;
    }
    void mapProbe(int probeCount) {
        if (probeCount + 1 > probes.size()) {
            while (probeCount + 1 > probes.size()) {
                probes.add(0);
            }
        }
        probes.set(probeCount, probes.get(probeCount) + 1);
    }

    void put(K searchKey, V newValue) {
        int hashCode = hash(searchKey);
        int probeCount = 0;
        //update or add the newValue to the bucket hash(searchKey)
        //if hash(key) is full use linear probing to find the next available bucket

        while (bucketList.get(hashCode) != null) {
            if (bucketList.get(hashCode).key.equals(searchKey)) {
                bucketList.get(hashCode).value = newValue;
                mapProbe(probeCount);
                return;
            }
            hashCode++;
            probeCount++;
        }
        bucketList.set(hashCode, new Bucket<>(searchKey, newValue));
        entries++;
        mapProbe(probeCount);
    }
    V get(K searchKey) {
        //return a value for the specified key from the bucket hash(searchKey)
        //if hash(searchKey) doesn’t contain the value use linear probing to find the
        //appropriate value
        int hashCode = hash(searchKey);
        int probeCount = 0;
        if (bucketList.get(hashCode).key.equals(searchKey)) {
            mapProbe(probeCount);
            return bucketList.get(hashCode).value;
        } else {
            while (hashCode < buckets) {
                hashCode++;
                probeCount++;
                if (bucketList.get(hashCode).key.equals(searchKey)) {
                    mapProbe(probeCount);
                    return bucketList.get(hashCode).value;
                }
            }
            return null;
        }
    }
    boolean containsKey(K searchKey) {
        int hashCode = hash(searchKey);
        //return true if there is a value stored for searchKey

        if (bucketList.get(hashCode).key.equals(searchKey)) {
            return true;
        } else {
            while (hashCode < buckets) {
                hashCode++;
                if (bucketList.get(hashCode).key.equals(searchKey)) {
                    return true;
                }
            }
            return false;
        }
    }
    void stats() {
        System.out.println("Hash Table Stats");
        System.out.println("================");
        System.out.println("Number of Entries: " + entries);
        System.out.println("Number of Buckets: " + buckets);
        System.out.println("Histogram of Probes: " + probes.toString());
        double fillPercent = (double) entries/buckets;
        System.out.println("Fill percentage: " + String.format("%.5f",fillPercent * 100) + "%");
        System.out.println("Max Linear Probe: " + (probes.size() - 1));
        int probeSum = 0;
        int probeIndex = 0;
        int totalProbes = 0;
        for (int i : probes) {
            totalProbes += i;
            probeSum += (i* probeIndex);
            probeIndex++;
        }
        double avgProbe = (double) probeSum/totalProbes;
        System.out.println("Average Linear Probe: " + String.format("%.5f", avgProbe));
    }
    private int hash(K key) {
        return Math.abs(key.hashCode() % buckets);
    }
    public String toString() {
        StringBuilder string = new StringBuilder();
        int index = 0;
        for (Bucket b : bucketList) {
            if (b != null) {
                String str = "[" + index + "] Key: " + b.key + " | Value: " + b.value + "\n";
                string.append(str);
            }
            index++;
        }
        return String.valueOf(string);
    }

    class Bucket<K, V> {
        K key;
        V value;

        Bucket(K theKey, V theValue) {
            key = theKey;
            value = theValue;
        }
    }
}
