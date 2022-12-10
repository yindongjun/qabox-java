package io.fluentqa.qabox.excel.core;

/**
 * @author
 * @version 1.0
 */
@FunctionalInterface
interface CiFunction<T, F, R, U> {

    U apply(T t, F f, R r);
}
