import java.util.*;

public class ArrayMap<K, V> extends AbstractMap<K, V> {

    private static class ArrayMapEntry<K, V> implements Map.Entry<K, V> {
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

        @Override
        public String toString() {
            return  "[" + getKey() + ", " + getValue() + "]";
        }

        @Override
        public boolean equals(Object o) {
            ArrayMapEntry<K, V> map = (ArrayMapEntry<K, V>) o;
            return (this.getKey() == null ?
                    map.getKey() == null : this.getKey().equals(map.getKey())) &&
                    (this.getValue() == null ?
                            map.getValue() == null : this.getValue().equals(map.getValue()));
        }

        @Override
        public int hashCode() {
            return 31 * (31 + key.hashCode()) + value.hashCode();
        }
    }
    private Collection<ArrayMapEntry<K, V>> entries;

    public ArrayMap() {
        entries = new ArrayList<>();
    }

    @Override
    public V put(K key, V value) {
        for (ArrayMapEntry<K, V> entry : entries) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return value;
            }
        }

        entries.add(new ArrayMapEntry<K, V>(key, value));
        return value;
    }

    @Override
    public boolean containsKey(Object key) {
        for (ArrayMapEntry<K, V> i : entries) {
            if (i.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        for (ArrayMapEntry<K, V> entry : entries) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<>();

        entrySet.addAll(entries);

        return entrySet;
    }

}

class Prob1 {
    public static void main(String args[]) {
        ArrayMap<Integer, String> map = new ArrayMap<>();
        map.put(1, "aaa");
        map.put(2, "saa");
        map.put(3, "saas");
        map.put(2, "ssaasasas");
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
