package main.graph.visualization.api;

import java.util.List;

public interface GraphPath {

    /**
     * Return nodes and relationships.
     */
    List<Object> getComponents();
}
