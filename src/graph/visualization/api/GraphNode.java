package graph.visualization.api;


public interface GraphNode extends GraphEntity {

    int getColor();
    default String getRepresentation() {
        return "Node[" + getId() + "]";
    }

    Integer getCoverage();
}