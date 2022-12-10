package io.fluent.metadata.db.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConnectionConfig {
  /**
   * 数据库url
   */
  String url;
  /**
   * 数据库
   */
  String schema;
  /**
   * 数据库用户名
   */
  String userName;
  /**
   * 数据库密码
   */
  String password;
  /**
   * 选择表，如为null则获取全部
   */
  List<String> tables = new ArrayList<>();
}
