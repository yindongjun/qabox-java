package io.fluent.qabox.dao;


import io.fluent.qabox.dao.constants.DefaultConstants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * All Db Dao Accessor:
 * - Hold all the DaoTemplates
 * - Hold all the datasource
 * - Hold all the DbConfiguration
 * - Hold all the ActiveEntity
 */
public class FDaoAccessor {
  FDaoConfig config;
  Map<String, FDao> daoMap = new ConcurrentHashMap<>();

  public FDaoAccessor(FDaoConfig config) {
    this.config = config;
  }

  public static FDaoAccessor create(FDaoConfig config) {
    return new FDaoAccessor(config);
  }

  public FDao getDao(String dbName) {
    if (daoMap.get(dbName) == null) {
      FDao dao = FDao.create(this.config, dbName);
      daoMap.put(dbName, dao);
      return dao;
    }
    return daoMap.get(dbName);
  }

  public FDao getDao() {
    if (daoMap.get(DefaultConstants.POSTGRESQL) == null) {
      FDao dao = FDao.create(this.config, DefaultConstants.POSTGRESQL);
      daoMap.put(DefaultConstants.POSTGRESQL, dao);
      return dao;
    }
    return daoMap.get(DefaultConstants.POSTGRESQL);
  }
}