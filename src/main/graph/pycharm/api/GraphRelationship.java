package main.graph.pycharm.api;

import main.graph.visualization.api.GraphEntity;
import main.graph.visualization.api.GraphNode;

public interface GraphRelationship extends GraphEntity {

    boolean hasStartAndEndNode();

    double relationWeight();

    void setWeight(double weight);

    String getStartNodeId();

    String getEndNodeId();

    GraphNode getStartNode();

    GraphNode getEndNode();

    default String getRepresentation() {
        return "Relationship[" + getId() + "]";
    }

    String getCallsCount();

    double getWeight();
}
