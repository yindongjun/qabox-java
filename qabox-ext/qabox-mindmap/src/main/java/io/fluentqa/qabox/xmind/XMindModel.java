package io.fluentqa.qabox.xmind;

import io.fluentqa.qabox.mindmapping.MindMappingModel;
import io.fluentqa.qabox.xmind.model.Attached;
import io.fluentqa.qabox.xmind.model.RootTopic;
import lombok.Data;

import java.util.LinkedList;

@Data
public class XMindModel implements Cloneable, MindMappingModel<Attached> {
    private RootTopic rootTopic;
    private Attached root;
    private LinkedList<Attached> nodes = new LinkedList<>();

    public XMindModel(RootTopic rootTopic) {
        Attached rootAttached = new Attached();
        rootAttached.setChildren(rootTopic.getChildren());
        rootAttached.setTitle(rootTopic.getTitle());
        this.root = rootAttached;
        this.nodes.add(root);
    }

    public XMindModel(Attached root) {
        this.root = root;
        this.nodes.add(root);
    }

    /**
     * TODO: move deep copy here or check the default implementation works
     * COPY nodes in new LinedList
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected XMindModel clone() throws CloneNotSupportedException {
        return (XMindModel) super.clone();
    }

    @Override
    public LinkedList<Attached> getChildrenNodes() {
        return this.nodes;
    }
}
