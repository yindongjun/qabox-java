package io.fluentqa.qabox.xmind;

import io.fluentqa.qabox.mindmapping.MindMappingLevel;
import lombok.Data;

@Data
public class TestCaseModel {
    private String productName;
    @MindMappingLevel(value = 1)
    private String moduleName;
    @MindMappingLevel(value = 2)
    private String functionality;
    @MindMappingLevel(value = 3)
    private String feature;
    @MindMappingLevel(value = 4)
    private String summary;
    private String priority = "P2"; //check it
    private String precondition;
    private String steps;
    @MindMappingLevel(value = 5)
    private String expectedResult;
}
