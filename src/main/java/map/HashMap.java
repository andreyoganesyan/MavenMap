package map;

import java.util.*;
import map.exceptions.*;

/**
 * Created by andre_000 on 28-Feb-17.
 */
public class HashMap<K, V> extends AbstractMap<K, V> {

    private int capacity = MIN_CAPACITY;
    private int size = 0;
    private static final double LOAD_FACTOR = 0.75;
    private static final int MIN_CAPACITY = 256;
    private static final int MAX_CAPACITY = 1073741824;


    List<HashMapEntry<K, V>>[] buckets = new LinkedList[capacity];

    private class HashMapEntry<K, V> extends SimpleEntry<K, V> {
        private HashMapEntry(K key, V value) {
            super(key, value);
        }
    }


    @Override
    public Set<Entry<K, V>> entrySet() {
        return new Set<Entry<K, V>>() {
            @Override
            public int size() {
                return size;
            }

            @Override
            public boolean isEmpty() {
                return size == 0;
            }

            @Override
            public boolean contains(Object o) {
                if (!(o instanceof Entry)) return false;
                Entry oEntry = (Entry) o;
                if (buckets[getIndex(oEntry.getKey())] == null) return false;
                for (Entry<K, V> entry : buckets[getIndex(oEntry.getKey())]) {
                    if (entry.equals(o)) return true;
                }
                return false;
            }

            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new Iterator<Entry<K, V>>() {
                    int bucket = 0;
                    Iterator<? extends Entry<K, V>> currentBucketIterator = getNextBucketIterator();

                    private Iterator<HashMapEntry<K, V>> getNextBucketIterator() {
                        for (; bucket < buckets.length; bucket++) {
                            if (buckets[bucket] != null && !buckets[bucket].isEmpty()) {
                                return buckets[bucket++].iterator();
                            }
                        }
                        return null;
                    }

                    @Override
                    public void remove() {
                        if (currentBucketIterator == null)
                            throw new IllegalEntrySetStateException();
                        try {
                            currentBucketIterator.remove();
                        } catch (IllegalStateException e) {
                            throw new IllegalEntrySetStateException();
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        if (currentBucketIterator == null) return false;
                        if (currentBucketIterator.hasNext()) return true;
                        currentBucketIterator = getNextBucketIterator();
                        return hasNext();
                    }

                    @Override
                    public Entry<K, V> next() {
                        return currentBucketIterator.next();
                    }
                };
            }

            @Override
            public Object[] toArray() {
                Object[] result = new Object[size()];
                int i = 0;
                for (Entry<K, V> entry : entrySet()) {
                    result[i] = entry;
                }
                return result;

            }

            @Override
            @SuppressWarnings("unchecked")
            public <T> T[] toArray(T[] a) {
                return (T[]) toArray();

            }

            @Override
            public boolean add(Entry<K, V> kvEntry) {
                V oldValue = put(kvEntry.getKey(), kvEntry.getValue());
                if (oldValue != kvEntry.getValue()) return true;
                return false;
            }

            @Override
            public boolean remove(Object o) {
                if (!(o instanceof Entry)) return false;
                Entry oEntry = (Entry) o;
                if (buckets[getIndex(oEntry.getKey())] == null) return false;
                Iterator<? extends Entry<K, V>> entryIterator = buckets[getIndex(oEntry.getKey())].iterator();
                while (entryIterator.hasNext()) {
                    Entry<K, V> entry = entryIterator.next();
                    if (entry == null ? oEntry == null : entry.equals(o)) {
                        entryIterator.remove();
                        size--;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                for (Object o : c) {
                    if (!contains(o)) return false;
                }
                return true;
            }

            @Override
            public boolean addAll(Collection<? extends Entry<K, V>> c) {
                boolean result = false;
                for (Entry<K, V> entry : c) {
                    if (add(entry)) result = true;
                }
                return result;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                boolean result = false;
                Iterator<Entry<K, V>> entryIterator = iterator();
                while (entryIterator.hasNext()) {
                    Entry<K, V> entry = entryIterator.next();
                    if (!c.contains(entry)) {
                        entryIterator.remove();
                        result = true;
                    }
                }
                return result;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                boolean result = false;
                for (Object o : c) {
                    if (remove(o)) result = true;
                }
                return result;
            }

            @Override
            public void clear() {
                buckets = new LinkedList[capacity];
                size = 0;
            }
        };
    }

    @Override
    public V put(K key, V value) {
        int index = getIndex(key);
        if (buckets[index] == null) {
            buckets[index] = new LinkedList<HashMapEntry<K, V>>();
        }
        for (Entry<K, V> entry : buckets[index]) {
            if (entry.getKey() == null ? key == null : entry.getKey().equals(key)) {
                V oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }
        buckets[index].add(new HashMapEntry<K, V>(key, value));
        size++;
        if (size > capacity * LOAD_FACTOR) {
            resize();
        }
        return null;
    }

    private void resize() {
        if (capacity == MAX_CAPACITY) return;

        capacity *= 2;
        List<HashMapEntry<K, V>>[] newBuckets = new LinkedList[capacity];
        for (Entry<K, V> entry : entrySet()) {
            int index = getIndex(entry.getKey());
            if (newBuckets[index] == null) {
                newBuckets[index] = new LinkedList<HashMapEntry<K, V>>();
            }
            newBuckets[index].add(new HashMapEntry<K, V>(entry.getKey(), entry.getValue()));
        }

        buckets = newBuckets;


    }

    private int getIndex(int keyHashCode) {
        return keyHashCode % capacity;
    }

    private int getIndex(Object key) {
        return key == null ? 0 : getIndex(key.hashCode());
    }
}
