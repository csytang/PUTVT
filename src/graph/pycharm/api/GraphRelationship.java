package graph.pycharm.api;

import graph.visualization.api.GraphEntity;
import graph.visualization.api.GraphNode;

public interface GraphRelationship extends GraphEntity {

    boolean hasStartAndEndNode();

    float relationWeight();

    void setWeight(float weight);

    String getStartNodeId();

    String getEndNodeId();

    GraphNode getStartNode();

    GraphNode getEndNode();

    default String getRepresentation() {
        return "Relationship[" + getId() + "]";
    }

    String getCallsCount();
}
