package io.fluentqa.qabox.excel.core;

/**
 * @author
 * @version 1.0
 */
@FunctionalInterface
interface CiConsumer<T, F, U> {

    void accept(T t, F f, U u);
}
