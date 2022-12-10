
package io.fluentqa.qabox.excel.core.cache;

import java.util.WeakHashMap;

public class WeakCache<K, V> implements Cache<K, V> {

    private volatile WeakHashMap<K, V> cacheMap;

    public WeakCache() {
        cacheMap = new WeakHashMap<>();
    }

    public WeakCache(int mapSize) {
        cacheMap = new WeakHashMap<>(mapSize);
    }

    @Override
    public synchronized void cache(K key, V value) {
        cacheMap.put(key, value);
    }

    @Override
    public V get(K key) {
        return cacheMap.get(key);
    }

    @Override
    public synchronized void clearAll() {
        cacheMap.clear();
    }
}
