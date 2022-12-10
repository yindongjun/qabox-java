package io.fluent.qabox.excel.provider;

import io.fluent.qabox.excel.model.ApiTestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class PoijiExcelProviderTest {

  @org.junit.jupiter.api.Test
  void readAsList() {
    PoijiExcelProvider reader = new PoijiExcelProvider();
    List<ApiTestCase> result = reader.read("/Users/patrick/workspace/personal/qdriven/fluentqa-way/qa-tutorials/fluentqa-apicycle/fluentqa-apiway/fluent-ext/excel/src/main/resources/test_data.xlsx",ApiTestCase.class,null);
    Assertions.assertNotNull(result);
  }

  @Test
  void readForMap() {
  }

  @Test
  void write() {
  }
}