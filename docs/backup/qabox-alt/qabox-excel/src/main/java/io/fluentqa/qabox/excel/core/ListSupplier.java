package io.fluentqa.qabox.excel.core;

import java.util.List;

/**
 * @author
 * @version 1.0
 */
@FunctionalInterface
public interface ListSupplier<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    List<T> getAsList();
}
