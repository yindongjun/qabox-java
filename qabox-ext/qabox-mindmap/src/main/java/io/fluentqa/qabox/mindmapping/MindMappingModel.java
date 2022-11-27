package io.fluentqa.qabox.mindmapping;


import java.util.LinkedList;


public interface MindMappingModel<T> {
    public T getRoot();

    public LinkedList<T> getChildrenNodes();
}
