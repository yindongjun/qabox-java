package io.fluentqa.qabox.mindmapping;

import java.util.List;

public interface MindMappingAccessor {

   <T> List<MindMappingModel<T>> getFullPathNodes();
}
