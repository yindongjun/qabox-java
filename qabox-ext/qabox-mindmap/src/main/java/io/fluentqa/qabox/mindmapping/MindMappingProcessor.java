package io.fluentqa.qabox.mindmapping;


import cn.hutool.core.io.FileUtil;
import io.fluentqa.qabox.exception.MindMapException;
import io.fluentqa.qabox.freemind.FreemindAccessor;
import io.fluentqa.qabox.xmind.XmindAccessor;

import java.util.List;
import java.util.function.Function;

public class MindMappingProcessor {

    public <R,T> List<R> process(String mindMapFilePath, Class<R> beanType, Function<T, String> extractFunc) {
        List<MindMappingModel<T>> mindMappingModels = readMindMapFile(mindMapFilePath);
        return MindMappingTransformer.transferAll(mindMappingModels, beanType, extractFunc);
    }

    private <T> List<MindMappingModel<T>> readMindMapFile(String mindMapFilePath) {
        MindMappingAccessor accessor = selectAccessor(mindMapFilePath);
        return accessor.getFullPathNodes();
    }

    private MindMappingAccessor selectAccessor(String mindMapFilePath) {
        String suffix = FileUtil.getSuffix(mindMapFilePath);
        if (suffix.equalsIgnoreCase("mm")) return new FreemindAccessor(mindMapFilePath);
        if (suffix.equalsIgnoreCase("xmind")) return new XmindAccessor(mindMapFilePath);
        throw new MindMapException("No selector Support");
    }
}
