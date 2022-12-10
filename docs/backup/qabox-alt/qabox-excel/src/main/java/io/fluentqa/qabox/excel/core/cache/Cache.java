
package io.fluentqa.qabox.excel.core.cache;

/**
 * 缓存接口

 */
public interface Cache<E, T> {

    void cache(E key, T value);

    T get(E key);

    void clearAll();
}
