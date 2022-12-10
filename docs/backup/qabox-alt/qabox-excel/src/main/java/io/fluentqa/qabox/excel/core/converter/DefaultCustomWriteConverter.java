
package io.fluentqa.qabox.excel.core.converter;

/**

 */
public class DefaultCustomWriteConverter implements CustomWriteConverter<Object, Object> {

    @Override
    public Object convert(Object originalData, CustomWriteContext customWriteContext) {
        return originalData;
    }
}
