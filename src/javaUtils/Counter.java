package javaUtils;

class Counter<K> extends EHashMap<K, Long> {
    public Counter() {
        super();
    }

    public void add(K key) {
        put(key, get(key) + 1);
    }

    public Long get(Object key) {
        if (containsKey(key))
            return super.get(key);
        return 0L;
    }
}
