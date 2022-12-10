package io.fluent.metadata.db.model;

import lombok.Data;

@Data
public class FieldInfo {

    boolean isAutoIncrement;

    String fieldName;

    String typeName;

    int displaySize;

    String className;

    boolean isNullable;

    boolean isCurrency;

    JdbcJavaFieldMapping jdbcJavaFieldMapping;
}
