package graph.pycharm.console;

import graph.visualization.api.GraphEntity;
import graph.visualization.api.GraphNode;

public interface GraphRelationship extends GraphEntity {

    boolean hasStartAndEndNode();

    Integer relationWeight();

    void setWeight(Integer weight);

    String getStartNodeId();

    String getEndNodeId();

    GraphNode getStartNode();

    GraphNode getEndNode();

    default String getRepresentation() {
        return "Relationship[" + getId() + "]";
    }
}
