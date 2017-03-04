package map;

import java.util.*;
import map.exceptions.*;

public class ArrayMap<K, V> extends AbstractMap<K, V> {

    @SuppressWarnings("hiding")
    private class ArrayMapEntry<K, V> extends AbstractMap.SimpleEntry<K, V> {
        private ArrayMapEntry(K key, V value) {
            super(key, value);
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayMapEntry<K, V>[] entryArray = new ArrayMapEntry[0];

    public Set<Entry<K, V>> entrySet() {
        return new Set<Entry<K, V>>() {

            public int size() {
                return entryArray.length;
            }

            public boolean isEmpty() {
                return entryArray.length == 0;
            }

            public boolean contains(Object o) {
                for (Entry entry : entryArray) {
                    if (entry.equals(o)) return true;
                }
                return false;
            }

            public Iterator<Entry<K, V>> iterator() {
                return new Iterator<Entry<K, V>>() {
                    int pointer = 0;

                    public boolean hasNext() {
                        return pointer < entryArray.length;
                    }

                    public Map.Entry next() {
                        return entryArray[pointer++];
                    }

                    public void remove() {
                        if (pointer == 0) throw new IllegalEntrySetStateException();
                        int prevReturned = pointer - 1;
                        for (int i = prevReturned; i < entryArray.length - 1; i++) {
                            entryArray[i] = entryArray[i + 1];
                        }
                        pointer--;
                        entryArray = Arrays.copyOf(entryArray, entryArray.length - 1);
                    }
                };
            }

            public Object[] toArray() {
                return (Object[]) entryArray;
            }

            @SuppressWarnings("unchecked")
            public <T> T[] toArray(T[] a) {
                return (T[]) entryArray;
            }

            public boolean add(Entry<K, V> kvEntry) {
                V oldValue = put(kvEntry.getKey(), kvEntry.getValue());
                if (!oldValue.equals(kvEntry.getValue())) return true;
                return false;
            }

            public boolean remove(Object o) {
                Iterator<Entry<K, V>> entryIterator = iterator();
                while (entryIterator.hasNext()) {
                    if (entryIterator.next().equals(o)) {
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
                List<Entry<K, V>> newEntries = new LinkedList<Entry<K, V>>();
                outerFor:
                for (Entry<K, V> cEntry : c) {
                    for (Entry<K, V> entry : entryArray) {
                        if (entry.getKey().equals(cEntry.getKey())) {
                            if (!entry.getValue().equals(cEntry.getValue())) {
                                result = true;
                                entry.setValue(cEntry.getValue());
                            }
                            continue outerFor;
                        }
                    }
                    newEntries.add(cEntry);
                }
                if (newEntries.size() > 0) {
                    result = true;
                    entryArray = Arrays.copyOf(entryArray, entryArray.length + newEntries.size());
                    for (int i = 0; i < newEntries.size(); i++) {
                        entryArray[entryArray.length - newEntries.size() + i] = new ArrayMapEntry<K, V>(newEntries.get(i).getKey(), newEntries.get(i).getValue());
                    }
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

            @SuppressWarnings("unchecked")
            public void clear() {
                entryArray = new ArrayMapEntry[0];
            }
        };
    }

    public V put(K key, V value) {
        for (int i = 0; i < entryArray.length; i++) {
            if (entryArray[i].getKey() == null ? key == null : entryArray[i].getKey().equals(key)) {
                V result = entryArray[i].getValue();
                entryArray[i].setValue(value);
                return result;
            }
        }

        entryArray = Arrays.copyOf(entryArray, entryArray.length + 1);
        entryArray[entryArray.length - 1] = new ArrayMapEntry<K, V>(key, value);

        return null;
    }
}
