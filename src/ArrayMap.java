import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ArrayMap<K, V> extends AbstractMap<K, V> {

    class ArrayMapEntry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        public ArrayMapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return this.value;
        }

        public String toString() {
            return "Key = " + this.key + "  |  Value = " + this.value;
        }

        public boolean equals(Object o) {
            ArrayMapEntry<K, V> map = (ArrayMapEntry<K, V>) o;
            return (this.getKey() == null ?
                    map.getKey() == null : this.getKey().equals(map.getKey())) &&
                    (this.getValue() == null ?
                            map.getValue() == null : this.getValue().equals(map.getValue()));
        }

        public int hashCode() {
            return (this.getKey() == null ? 0 : this.getKey().hashCode()) ^
                    (this.getValue() == null ? 0 : this.getValue().hashCode());
        }
    }

    private Set<Map.Entry<K, V>> entries = null;
    private ArrayList<ArrayMapEntry<K, V>> list = null;

    public ArrayMap() {
        list = new ArrayList<>();
    }

    public V put(K key, V value) {
        int size = list.size();
        Map.Entry<K, V> entry = null;
        int i = 0;
        if (key == null) {
            for (i = 0; i < size; i++) {
                entry = list.get(i);
                if (entry.getKey() == null) {
                    break;
                }
            }
        } else {
            for (i = 0; i < size; i++) {
                entry = list.get(i);
                if (entry.getKey().equals(key)) {
                    break;
                }
            }
        }

        V oldValue = null;
        if (i < size) {
            oldValue = entry.getValue();
            entry.setValue(value);
        } else {
            list.add(new ArrayMapEntry<>(key, value));
        }
        return oldValue;
    }

    public boolean containsKey(Object key) {
        for (ArrayMapEntry<K, V> i : list) {
            if (i.getKey() == key) {
                return true;
            }
        }
        return false;
    }

    public V get(Object key) {
        for (ArrayMapEntry<K, V> i : list) {
            if (i.getKey() == key) {
                return i.getValue();
            }
        }
        return null;
    }

    public int size() {
        return list.size();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
