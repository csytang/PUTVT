package main.graph.results.api;

import main.graph.pycharm.api.GraphRelationship;
import main.graph.visualization.api.GraphNode;

import java.util.List;

public interface GraphCoverageResult {

    List<GraphNode> getNodes();

    List<GraphRelationship> getRelationships();
}
