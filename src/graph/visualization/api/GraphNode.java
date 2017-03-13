package graph.visualization.api;


public interface GraphNode extends GraphEntity {

    default String getRepresentation() {
        return "Node[" + getId() + "]";
    }
}
