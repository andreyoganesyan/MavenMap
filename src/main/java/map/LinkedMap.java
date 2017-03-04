package map;

import java.util.*;
import map.exceptions.*;

/**
 * Created by andre_000 on 25-Feb-17.
 */
public class LinkedMap<K, V> extends AbstractMap<K, V> {

    private int size = 0;
    private LinkedEntry<K, V> head = null;
    private LinkedEntry<K, V> tail = null;

    @SuppressWarnings("hiding")
    private class LinkedEntry<K, V> extends SimpleEntry<K, V> {
        private LinkedEntry(K key, V value) {
            super(key, value);
        }

        private LinkedEntry<K, V> prev;
        private LinkedEntry<K, V> next;
    }

    public Set<Entry<K, V>> entrySet() {
        return new Set<Entry<K, V>>() {
            public Iterator<Entry<K, V>> iterator() {
                return new Iterator<Entry<K, V>>() {
                    LinkedEntry<K, V> lastReturned = null;
                    LinkedEntry<K, V> current = head;

                    public boolean hasNext() {
                        return current != null;
                    }

                    public Entry<K, V> next() {
                        lastReturned = current;
                        current = current.next;
                        return lastReturned;
                    }

                    public void remove() {
                        if (lastReturned == null) throw new IllegalEntrySetStateException();
                        if (lastReturned == head) head = lastReturned.next;
                        if (lastReturned == tail) tail = lastReturned.prev;
                        if (current != null) current.prev = lastReturned.prev;
                        if (lastReturned.prev != null) lastReturned.prev.next = current;
                        size--;
                    }
                };
            }

            public int size() {
                return LinkedMap.this.size;
            }

            public boolean isEmpty() {
                return LinkedMap.this.size == 0;
            }

            public boolean contains(Object o) {

                Iterator<Entry<K, V>> entryIterator = iterator();
                while (entryIterator.hasNext()) {
                    Entry<K, V> entry = entryIterator.next();
                    if (entry.equals(o)) return true;
                }
                return false;
            }

            public Object[] toArray() {
                Object[] result = new Object[size];
                Iterator<Entry<K, V>> entryIterator = iterator();
                for (int i = 0; i < size; i++) result[i] = entryIterator.next();
                return result;
            }

            @SuppressWarnings("unchecked")
            public <T> T[] toArray(T[] a) {
                T[] result;
                if (a.length <= size) {
                    result = a;
                } else {
                    result = (T[]) (new Object[size]);
                }
                Arrays.fill(result, null);
                int i = 0;
                Iterator<Entry<K, V>> entryIterator = iterator();
                while (entryIterator.hasNext()) {
                    result[i++] = (T) entryIterator.next();
                }
                return null;
            }

            public boolean add(Entry<K, V> kvEntry) {
                V oldValue = put(kvEntry.getKey(), kvEntry.getValue());
                if (oldValue != kvEntry.getValue()) return true;
                return false;
            }

            public boolean remove(Object o) {
                Iterator<Entry<K, V>> entryIterator = iterator();
                while (entryIterator.hasNext()) {
                    Entry<K, V> entry = entryIterator.next();
                    if (entry.equals(o)) {
                        entryIterator.remove();
                        return true;
                    }
                }
                return false;
            }

            public boolean containsAll(Collection<?> c) {
                for (Object o : c) {
                    if (!contains(o)) return false;
                }
                return true;
            }

            public boolean addAll(Collection<? extends Entry<K, V>> c) {
                boolean result = false;
                for (Entry<K, V> entry : c) {
                    if (add(entry)) result = true;
                }
                return result;
            }

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

            public boolean removeAll(Collection<?> c) {
                boolean result = false;
                for (Object o : c) {
                    if (remove(o)) result = true;
                }
                return result;
            }

            public void clear() {
                head = tail = null;
            }
        };
    }

    public V put(K key, V value) {
        V result;
        Iterator<Entry<K, V>> entryIterator = entrySet().iterator();
        while (entryIterator.hasNext()) {
            Entry<K, V> entry = entryIterator.next();
            if (entry.getKey() == null ? key == null : entry.getKey().equals(key)) {
                result = entry.getValue();
                entry.setValue(value);
                return result;
            }
        }
        LinkedEntry<K, V> newEntry = new LinkedEntry<K, V>(key, value);
        if (tail == null) {
            tail = head = newEntry;
        } else {
            newEntry.prev = tail;
            tail = tail.next = newEntry;
        }
        size++;
        return null;
    }
}
