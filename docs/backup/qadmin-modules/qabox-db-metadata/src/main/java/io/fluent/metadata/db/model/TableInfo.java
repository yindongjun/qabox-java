package io.fluent.metadata.db.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TableInfo {

    String tableName;

    List<FieldInfo> fieldInfos = new ArrayList<FieldInfo>();

}
