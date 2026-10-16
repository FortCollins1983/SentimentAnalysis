import java.util.LinkedList;

public class HashTable<Key, Value> {

    // One entry stored in a bucket
    private static class Entry<K, V> {
        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<Entry<Key, Value>>[] buckets;  // array of linked-list buckets
    private int capacity;                             // current number of buckets
    private int size;                                 // number of key-value pairs
    private static final double LOAD_FACTOR_THRESHOLD = 0.5;

    @SuppressWarnings("unchecked")
    public HashTable(int initialCapacity) {
        this.capacity = Math.max(1, initialCapacity);
        this.size = 0;
        buckets = (LinkedList<Entry<Key, Value>>[]) new LinkedList[capacity];

        // initialise every bucket with an empty linked list
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    /** Insert / update a key-value pair. Triggers rehash when needed. */
    public void put(Key key, Value value) {
        int index = hash(key);

        // Update an existing key without increasing the size.
        for (Entry<Key, Value> entry : buckets[index]) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        buckets[index].add(new Entry<>(key, value));
        size++;

        // Keep the table at most half full.
        if ((double) size / capacity > LOAD_FACTOR_THRESHOLD) {
            rehash();
        }
    }

    /** Convenience alias used by some versions of the assignment */
    public void insert(Key key, Value value) {
        put(key, value);
    }

    /** Return the value associated with key, or null if absent. */
    public Value get(Key key) {
        int index = hash(key);
        for (Entry<Key, Value> e : buckets[index]) {
            if (e.key.equals(key)) {
                return e.value;
            }
        }
        return null;
    }

    /** Alias for get() to match the original HashTable API */
    public Value lookup(Key key) {
        return get(key);
    }

    /** Double the number of buckets and rehash every existing entry. */
    @SuppressWarnings("unchecked")
    private void rehash() {
        int newCapacity = capacity * 2;
        LinkedList<Entry<Key, Value>>[] newBuckets =
                (LinkedList<Entry<Key, Value>>[]) new LinkedList[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new LinkedList<>();
        }

        // Recalculate each entry's index using the larger capacity.
        for (LinkedList<Entry<Key, Value>> bucket : buckets) {
            for (Entry<Key, Value> entry : bucket) {
                int index = Math.floorMod(entry.key.hashCode(), newCapacity);
                newBuckets[index].add(entry);
            }
        }

        buckets = newBuckets;
        capacity = newCapacity;
    }

    private int hash(Key key) {
        return Math.floorMod(key.hashCode(), capacity);
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }
}