package io.fluent.qabox.dao;


import cn.hutool.db.ds.DSFactory;
import cn.hutool.setting.Setting;
import io.fluentqa.qabox.config.QConfig;

import javax.sql.DataSource;

import static io.fluent.qabox.dao.constants.DefaultConstants.DB_GROUP;

public class FDaoConfig {

  QConfig config;


  public FDaoConfig() {
    this.config = QConfig.create();
  }

  public FDaoConfig(String configPath) {
    this.config = QConfig.create(configPath);
  }

  public DataSource getDataSource(String settingName) {
    Setting config = QConfig.create().getConfigSet(settingName);
    return DSFactory.create(config).getDataSource();
  }

  public DataSource getDataSource() {
    return getDataSource(DB_GROUP);
  }
}
