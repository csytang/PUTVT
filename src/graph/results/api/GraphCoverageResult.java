package graph.results.api;

import graph.pycharm.api.GraphRelationship;
import graph.visualization.api.GraphNode;

import java.util.List;

public interface GraphCoverageResult {

    List<GraphNode> getNodes();

    List<GraphRelationship> getRelationships();
}
