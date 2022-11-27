package io.fluentqa.qabox.freemind;


import io.fluentqa.qabox.freemind.model.Map;
import io.fluentqa.qabox.freemind.model.Node;
import io.fluentqa.qabox.mindmapping.MindMappingAccessor;
import io.fluentqa.qabox.mindmapping.MindMappingModel;
import io.fluentqa.qabox.mindmapping.xml.XmlHelper;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Freemind MindMapping Accessor:
 * putting every method here to manipulation mindmapping
 */

public class FreemindAccessor implements MindMappingAccessor {
    private Map root;
    private final Document xmlStr;
    private final List<MindMappingModel<Node>> allPaths = new ArrayList<>();

    public FreemindAccessor(String freemindFilePath) {
        readFile(freemindFilePath);
        this.xmlStr = XmlHelper.getDocument(freemindFilePath);
        this.generateAllPaths();
    }

    public FreemindAccessor(Document xmlDoc) {
        this.xmlStr = xmlDoc;
        readDocument(xmlDoc);
        this.generateAllPaths();
    }


    private void readFile(String filePath) {

        this.root = XmlHelper.readXmlToObject(filePath, Map.class);
    }

    private void readDocument(Document xmlDoc) {

        this.root = XmlHelper.readToObject(xmlDoc, Map.class);
    }


    public Map getRoot() {
        return root;
    }

    /**
     * Generate MindMap Tree into a list of @MindmapPath Entity includes all the node path
     * from root to leaf
     */
    private void generateAllPaths() {
        FreeMindModel rootPath = new FreeMindModel(this.root.getNode());
        List<FreeMindModel> nextLevel = populateNext(rootPath);
        populateAll(nextLevel);
    }

    /**
     * populate current node's child nodes into the mindmap path
     *
     * @param current: current FreeMindPath
     * @return
     */
    public List<FreeMindModel> populateNext(FreeMindModel current) {
        Node lastNode = current.getNodes().getLast();
        List<FreeMindModel> mms = new ArrayList<>();
        if (lastNode.getArrowlinkOrAttributeOrAttributeLayout().size() == 0) {
            mms = List.of(current);
        } else {
            for (Object o : lastNode.getArrowlinkOrAttributeOrAttributeLayout()) {
                FreeMindModel path = copyPath(current);
                if (o instanceof Node) {
                    path.getNodes().add((Node) o);
                }
                mms.add(path);
            }
        }
        return mms;
    }

    /**
     * Deep Copy Current Path
     *
     * @param current
     * @return
     */
    private FreeMindModel copyPath(FreeMindModel current) {
        FreeMindModel path = new FreeMindModel(current.getRoot());
        path.setNodes(new LinkedList<>());
        for (Node node : current.getNodes()) {
            path.getChildrenNodes().add(node);
        }
        return path;
    }

    /**
     * populate all mindmapping path
     * 1. if the latest node has no child node, add to collection to go out of the recursive call
     * 2. if the latest node has child nodes, populate recursively until the latest node has no child node
     *
     * @param paths
     */
    public void populateAll(List<FreeMindModel> paths) {
        for (FreeMindModel mindmapPath : paths) {
            if (mindmapPath.getChildrenNodes().getLast().getArrowlinkOrAttributeOrAttributeLayout().size() == 0) {
                this.allPaths.add(mindmapPath);
            } else {
                populateAll(populateNext(mindmapPath));
            }
        }
    }

    /**
     * Get ALl the Mindmapping Path
     *
     * @return
     */
    @Override
    public List<MindMappingModel<Node>> getFullPathNodes() {
        return allPaths;
    }


    public Document getXmlStr() {
        return xmlStr;
    }


}
