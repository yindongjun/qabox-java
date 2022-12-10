package io.fluentqa.mitmrecorder;

import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class MessageCache<K, T> {

    private long timeToLive;
    private final LinkedHashMap<K, MessageCacheObject<T>> cacheMap;

    @Data
    protected static class MessageCacheObject<T> {
        private long lastAccessed = System.currentTimeMillis();
        private T value;

        protected MessageCacheObject(T value) {
            this.value = value;
        }
    }

    public MessageCache(long timeToLive, final long timeInterval, int max) {
        this.timeToLive = timeToLive * 2000;

        cacheMap = new LinkedHashMap<>(max);

        if (timeToLive > 0 && timeInterval > 0) {

            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(timeInterval * 1000);
                        cleanup();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });

            t.setDaemon(true);
            t.start();
        }
    }

    // PUT method
    public void put(K key, T value) {
        synchronized (cacheMap) {
            cacheMap.put(key, new MessageCacheObject<>(value));
        }
    }

    // GET method
    @SuppressWarnings("unchecked")
    public T get(K key) {
        synchronized (cacheMap) {
            MessageCacheObject c = cacheMap.get(key);

            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return (T) c.value;
            }
        }
    }

    // GET method
    @SuppressWarnings("unchecked")
    public T getLast() {
        synchronized (cacheMap) {
            if (cacheMap.size() < 1) {
                return null;
            }
            MessageCacheObject c = ((Entry<K, MessageCacheObject<T>>) cacheMap.entrySet().toArray()[cacheMap.size() - 1]).getValue();

            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return (T) c.value;
            }
        }
    }

    // GET method
    @SuppressWarnings("unchecked")
    public T getLastByKeyPart(K keyPart) {
        synchronized (cacheMap) {
            K foundKey = null;
            for (K key : cacheMap.keySet()) {
                if (key instanceof String && keyPart instanceof String) {
                    if (((String) key).contains((String) keyPart)) {
                        foundKey = key;
                    }
                } else {
                    if (key.toString().contains(keyPart.toString())) {
                        foundKey = key;
                    }
                }
            }
            MessageCacheObject c = cacheMap.get(foundKey);

            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return (T) c.value;
            }
        }
    }

    // GET method
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        synchronized (cacheMap) {
            if (cacheMap.size() < 1) {
                return null;
            }
            List<T> all = new ArrayList<>();
            for (Entry<K, MessageCacheObject<T>> messageCacheObjectEntry : cacheMap.entrySet()) {
                all.add(messageCacheObjectEntry.getValue().value);
                messageCacheObjectEntry.getValue().lastAccessed = System.currentTimeMillis();
            }
            return all;
        }
    }

    // GET method
    @SuppressWarnings("unchecked")
    public List<T> getAllByKeyPart(K keyPart) {
        synchronized (cacheMap) {
            if (cacheMap.size() < 1) {
                return null;
            }
            List<T> all = new ArrayList<>();
            for (Entry<K, MessageCacheObject<T>> messageCacheObjectEntry : cacheMap.entrySet()) {
                if (messageCacheObjectEntry.getKey() instanceof String && keyPart instanceof String) {
                    if (!((String) messageCacheObjectEntry.getKey()).contains((String) keyPart)) {
                        continue;
                    }
                } else {
                    if (!messageCacheObjectEntry.getKey().toString().contains(keyPart.toString())) {
                        continue;
                    }
                }
                all.add(messageCacheObjectEntry.getValue().value);
                messageCacheObjectEntry.getValue().lastAccessed = System.currentTimeMillis();
            }
            return all;
        }
    }

    // GET method
    @SuppressWarnings("unchecked")
    public String getList() {
        synchronized (cacheMap) {
            if (cacheMap.size() < 1) {
                return null;
            }
            StringBuilder list = new StringBuilder();
            for (Entry<K, MessageCacheObject<T>> messageCacheObjectEntry : cacheMap.entrySet()) {
                MitmInterceptedMessage mitmInterceptedMessage = (MitmInterceptedMessage) messageCacheObjectEntry.getValue().value;
                list.append(mitmInterceptedMessage.getRequest().getMethod());
                list.append(" ");
                list.append(mitmInterceptedMessage.getRequest().getUrl());
                list.append("\n");
                messageCacheObjectEntry.getValue().lastAccessed = System.currentTimeMillis();
            }
            return list.toString();
        }
    }

    // GET method
    @SuppressWarnings("unchecked")
    public String getListByKeyPart(K keyPart) {
        synchronized (cacheMap) {
            StringBuilder list = new StringBuilder();
            for (Entry<K, MessageCacheObject<T>> messageCacheObjectEntry : cacheMap.entrySet()) {
                if (messageCacheObjectEntry.getKey() instanceof String && keyPart instanceof String) {
                    if (!((String) messageCacheObjectEntry.getKey()).contains((String) keyPart)) {
                        continue;
                    }
                } else {
                    if (!messageCacheObjectEntry.getKey().toString().contains(keyPart.toString())) {
                        continue;
                    }
                }
                MitmInterceptedMessage mitmInterceptedMessage = (MitmInterceptedMessage) messageCacheObjectEntry.getValue().value;
                list.append(mitmInterceptedMessage.getRequest().getMethod());
                list.append(" ");
                list.append(mitmInterceptedMessage.getRequest().getUrl());
                list.append("\n");
                messageCacheObjectEntry.getValue().lastAccessed = System.currentTimeMillis();
            }
            return list.toString();
        }
    }

    // REMOVE method
    public void remove(K key) {
        synchronized (cacheMap) {
            cacheMap.remove(key);
        }
    }

    // REMOVE ALL method
    public void removeAll() {
        synchronized (cacheMap) {
            cacheMap.clear();
        }
    }

    // Get Cache Objects Size()
    public int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    public void printKeys() {
        cacheMap.forEach((url, message) -> System.out.println(url));
    }

    // CLEANUP method
    public void cleanup() {

        long now = System.currentTimeMillis();
        ArrayList<K> deleteKeyList;

        synchronized (cacheMap) {
            Iterator<Entry<K, MessageCacheObject<T>>> itr = cacheMap.entrySet().iterator();

            deleteKeyList = new ArrayList<>((cacheMap.size() / 2) + 1);
            MessageCacheObject<T> messageCacheObject;
            while (itr.hasNext()) {
                Entry<K, MessageCacheObject<T>> entry = itr.next();
                K key = entry.getKey();
                messageCacheObject = entry.getValue();
                if (messageCacheObject != null
                        && (now > (timeToLive + messageCacheObject.lastAccessed))) {
                    deleteKeyList.add(key);
                }
            }
        }

        for (K key : deleteKeyList) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }

            Thread.yield();
        }
    }

}
