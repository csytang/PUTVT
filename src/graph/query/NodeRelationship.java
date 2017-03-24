package graph.query;


import graph.pycharm.console.GraphRelationship;
import graph.visualization.api.GraphNode;
import graph.visualization.api.GraphPropertyContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NodeRelationship implements GraphRelationship {

    private final String id;
    private Integer weight;
    private final List<String> types;
    private final GraphPropertyContainer propertyContainer;
    private final String startNodeId;
    private final String endNodeId;
    private GraphNode startNode;
    private GraphNode endNode;

    public NodeRelationship(String id) {
        this.id = String.valueOf(id);
        this.types = new ArrayList<>();
        this.propertyContainer = new ResultsPropertyContainer();

        this.startNodeId = "STARTNODE";
        this.endNodeId = "ENDNODE";
    }

    @Override
    public String getStartNodeId() {
        return startNodeId;
    }

    @Override
    public String getEndNodeId() {
        return endNodeId;
    }

    public void setStartNode(GraphNode startNode) {
        this.startNode = startNode;
    }

    public void setEndNode(GraphNode endNode) {
        this.endNode = endNode;
    }

    @Override
    public String getId() {
        return String.valueOf(id);
    }

    @Override
    public List<String> getTypes() {
        return types;
    }

    @Override
    public String getTypesName() {
        return "type";
    }

    @Override
    public boolean isTypesSingle() {
        return true;
    }

    @Override
    public GraphPropertyContainer getPropertyContainer() {
        return propertyContainer;
    }

    @Override
    public boolean hasStartAndEndNode() {
        return startNode != null && endNode != null;
    }

    @Override
    public Integer relationWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(Integer weight){
        this.weight = weight;
    }

    @Override
    public GraphNode getStartNode() {
        return startNode;
    }

    @Override
    public GraphNode getEndNode() {
        return endNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NodeRelationship that = (NodeRelationship) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
