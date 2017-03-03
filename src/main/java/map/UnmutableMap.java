package map;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import map.exceptions.*;

/**
 * Created by andre_000 on 02-Mar-17.
 */
public class UnmutableMap<K, V> implements Map<K, V> {

    private Map<K, V> actualMap;

    public UnmutableMap(Map map) {
        actualMap = map;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return Collections.unmodifiableSet(actualMap.entrySet());
    }

    @Override
    public Set<K> keySet() {
        return Collections.unmodifiableSet(actualMap.keySet());
    }

    @Override
    public int hashCode() {
        return actualMap.hashCode();
    }

    @Override
    public boolean isEmpty() {
        return actualMap.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        return actualMap.equals(o);
    }

    @Override
    public void clear() {
        throw new UnsupportedMapOperationException();
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedMapOperationException();
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedMapOperationException();
    }

    @Override
    public V get(Object key) {
        return actualMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedMapOperationException();
    }

    @Override
    public boolean containsKey(Object key) {
        return actualMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return actualMap.containsValue(value);
    }

    @Override
    public Collection<V> values() {
        return Collections.unmodifiableCollection(actualMap.values());
    }

    @Override
    public int size() {
        return actualMap.size();
    }

    @Override
    public String toString() {
        return actualMap.toString();
    }

}
