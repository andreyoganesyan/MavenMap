package Map;

import java.util.*;

/**
 * Created by andre_000 on 18-Feb-17.
 */
public abstract class AbstractMap<K,V> implements Map<K,V> {

    public abstract Set<Map.Entry<K,V>> entrySet();
    protected static abstract class SimpleEntry<K,V> implements  Map.Entry<K,V>{
        private K key;
        private V value;
        protected SimpleEntry(K key, V value){
            this.key=key;
            this.value=value;
        }
        public K getKey() {
            return key;
        }
        public V getValue() {
            return value;
        }
        public V setValue(V value) {
            return this.value=value;
        }
        public boolean equals(Object o){
            if (o instanceof Entry) {
                Object key = ((Entry) o).getKey();
                Object value = ((Entry) o).getValue();
                if (key==null ? getKey()==null : key.equals(getKey()) &&
                        (value==null ? getValue()==null : value.equals(getValue())))
                    return true;
            }
            return false;

        }
        public int hashCode(){
            return (getKey()==null ? 0 : getKey().hashCode()) ^ (getValue()==null ? 0 : getValue().hashCode());
        }
        public String toString(){
            return key+"="+value;
        }

    }
    abstract public V put(K key, V value);


    public Set<K> keySet(){
        return new AbstractSet<K>() {
            @Override
            public Iterator<K> iterator() {
                return new Iterator<K>() {
                    Iterator<Map.Entry<K,V>> entryIterator = entrySet().iterator();
                    public boolean hasNext() {
                        return entryIterator.hasNext();
                    }

                    public K next() {
                        return entryIterator.next().getKey();
                    }
                    public void remove(){
                        entryIterator.remove();
                    }
                };
            }

            @Override
            public int size() {
                return entrySet().size();
            }
        };
    }

    @Override
    public int hashCode(){
        int result = 0;
        for(Map.Entry<K,V> entry: entrySet()){
            result+=entry.hashCode();
        }
        return result;
    }

    public boolean isEmpty(){
        return entrySet().isEmpty();
    }

    @Override
    public boolean equals(Object o){
        return o instanceof Map&&size()==((Map) o).size()&&((Map) o).entrySet().equals(entrySet());
    }

    public void clear(){
        entrySet().clear();
    }

    public void putAll(Map<? extends K, ? extends V> map){
        for(Map.Entry<? extends K, ? extends V> entry : map.entrySet() ){
            put(entry.getKey(),entry.getValue());
        }
    }

    public V remove(Object key) {
        Iterator<Map.Entry<K,V>> it = entrySet().iterator();
        while (it.hasNext()){
            Entry<K,V> entry = it.next();
            if(key==null ? entry.getKey()==null : key.equals(entry.getKey())){
                it.remove();
                return entry.getValue();
            }
        }
        return null;
    }

    public V get(Object key){
        Iterator<Map.Entry<K,V>> it = entrySet().iterator();
        while (it.hasNext()){
            Entry<K,V> entry = it.next();
            if(key==null ? entry.getKey()==null : key.equals(entry.getKey())){
                return entry.getValue();
            }
        }
        return null;

    }

    public boolean containsKey(Object key){
        Iterator<Map.Entry<K,V>> it = entrySet().iterator();
        while (it.hasNext()){
            Entry<K,V> entry = it.next();
            if(key==null ? entry.getKey()==null : key.equals(entry.getKey())){
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(Object value){
        Iterator<Map.Entry<K,V>> it = entrySet().iterator();
        while (it.hasNext()){
            Entry<K,V> entry = it.next();
            if(value==null ? entry.getValue()==null : value.equals(entry.getValue())){
                return true;
            }
        }
        return false;
    }

    public Collection<V> values(){
        return new AbstractCollection<V>() {
            @Override
            public Iterator<V> iterator() {
                return new Iterator<V>() {
                    Iterator<Map.Entry<K,V>> entryIterator = entrySet().iterator();
                    public boolean hasNext() {
                        return entryIterator.hasNext();
                    }

                    public V next() {
                        return entryIterator.next().getValue();
                    }
                    public void remove(){
                        entryIterator.remove();
                    }
                };
            }

            @Override
            public int size() {
                return AbstractMap.this.size();
            }

            @Override
            public boolean isEmpty() {
                return AbstractMap.this.isEmpty();
            }

            @Override
            public void clear() {
                AbstractMap.this.clear();
            }

            @Override
            public boolean contains(Object o) {
                return AbstractMap.this.containsValue(o);
            }
        };
    }


    public int size(){
        return entrySet().size();
    }
    public String toString(){
        StringBuilder result = new StringBuilder("{");
        for(Entry<K,V> entry:entrySet()){
            result.append(entry);
            result.append(", ");
        }
        if(!result.toString().equals("{"))
            result.delete(result.length()-2, result.length());
        result.append("}");
        return result.toString();
    }
}
