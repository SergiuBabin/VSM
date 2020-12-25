import java.util.*;

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
        if (entries == null) {
            entries = new AbstractSet<>() {
                public void clear() {
                    list.clear();
                }
                @Override
                public Iterator iterator() {
                    return list.iterator();
                }

                @Override
                public int size() {
                    return list.size();
                }
            };
        }
        return entries;
    }

    public String toString() {
        return list.toString();
    }
}

class Prob1 {
    public static void main(String args[]) {
        ArrayMap<Integer, String> map = new ArrayMap<>();
        map.put(1, "aaa");
        map.put(2, "saa");
        map.put(3, "saas");
        System.out.println("Verificam...");
        System.out.println("Continutul colectiei: " + map);
        if (map.size() != 3) {
            System.err.println("ArrayMap.size() (" + map.size() + ") a fost implementata gresit.");
        }

        Set<Map.Entry<Integer, String>> entries = map.entrySet();
        if (entries.size() != 3) {
            System.err.println("ArrayMap.entrySet() a fost implementata gresit.");
        }

        for (Map.Entry<Integer, String> e : entries) {
            map.put(e.getKey(), new StringBuffer(e.getValue()).reverse().toString());
            if (!map.entrySet().contains(e)) {
                System.err.println("ArrayMap.put() nu inlocuieste vechea valoare.");
            }
            if ((!e.toString().contains(e.getKey().toString()) || (!e.toString().contains(e.getValue())))) {
                System.err.println("ArrayMap.ArrayMapEntry.toString() a fost implementata gresit.");
            }
        }

        for (Map.Entry<Integer, String> e1 : entries) {
            for (Map.Entry<Integer, String> e2 : entries) {
                if ((e1 == e2) != (e1.equals(e2))) {
                    System.err.println("ArrayMap.ArrayMapEntry.equals() a fost implementata gresit.");
                }
                if ((e1 == e2) != (e1.hashCode() == e2.hashCode())) {
                    System.err.println("ArrayMap.ArrayMapEntry.hashCode() a fost implementata gresit.");
                }
            }
        }

    }

}
