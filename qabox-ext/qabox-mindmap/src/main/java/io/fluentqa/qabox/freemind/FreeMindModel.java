package io.fluentqa.qabox.freemind;



import io.fluentqa.qabox.freemind.model.Node;
import io.fluentqa.qabox.mindmapping.MindMappingModel;
import lombok.Data;

import java.util.LinkedList;

@Data
public class FreeMindModel implements Cloneable, MindMappingModel<Node> {
  private final Node root;
  private LinkedList<Node> nodes = new LinkedList<>();

  public FreeMindModel(Node root) {
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
  protected FreeMindModel clone() throws CloneNotSupportedException {
    return (FreeMindModel) super.clone();
  }

  @Override
  public LinkedList<Node> getChildrenNodes() {
    return this.nodes;
  }
}
