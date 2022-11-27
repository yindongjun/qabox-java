package io.fluentqa.qabox.xmind;

import cn.hutool.json.JSONUtil;
import io.fluentqa.qabox.mindmapping.MindMappingAccessor;
import io.fluentqa.qabox.mindmapping.MindMappingModel;
import io.fluentqa.qabox.xmind.model.Attached;
import io.fluentqa.qabox.xmind.model.JsonRootBean;
import io.fluentqa.qabox.xmind.model.RootTopic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class XmindAccessor implements MindMappingAccessor {
    private RootTopic root;

    private final List<MindMappingModel> allPaths = new ArrayList<>();

    public XmindAccessor(RootTopic root) {
        this.root = root;
        generateAllPaths();
    }

    public XmindAccessor(String xmindFilePath) {
        XmindRawData data = XmindUtil.readXmindFile(xmindFilePath);
        List<JsonRootBean> topic = JSONUtil.toList(data.getContentJson(), JsonRootBean.class);
        this.root = topic.get(0).getRootTopic();
        generateAllPaths();
    }


    public XmindAccessor generateAllPaths() {
        XMindModel rootPath = new XMindModel(this.root);
        List<XMindModel> nextLevel = populateNext(rootPath);
        populateAll(nextLevel);
        return this;
    }

    public List<XMindModel> populateNext(XMindModel current) {
        Attached lastNode = current.getNodes().getLast();
        List<XMindModel> mms = new ArrayList<>();
        if (lastNode.getChildren().getAttached().size() == 0) {
            mms = List.of(current);
        } else {
            for (Attached o : lastNode.getChildren().getAttached()) {
                XMindModel path = copyPath(current);
                if (o != null) {
                    path.getNodes().add(o);
                }
                mms.add(path);
            }
        }
        return mms;
    }

    private XMindModel copyPath(XMindModel current) {
        XMindModel path = new XMindModel(current.getRoot());
        path.setNodes(new LinkedList<>());
        for (Attached node : current.getNodes()) {
            path.getChildrenNodes().add(node);
        }
        return path;
    }

    public void populateAll(List<XMindModel> paths) {
        for (XMindModel mindmapPath : paths) {
            if (mindmapPath.getChildrenNodes().getLast().getChildren() == null
            ) {
                this.allPaths.add(mindmapPath);
            } else {
                populateAll(populateNext(mindmapPath));
            }
        }
    }

    @Override
    public List<MindMappingModel> getFullPathNodes() {
        return allPaths;
    }

}
