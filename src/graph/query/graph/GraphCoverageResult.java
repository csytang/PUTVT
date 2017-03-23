package graph.query.graph;

import graph.pycharm.console.GraphRelationship;
import graph.query.api.ResultsPlan;
import graph.visualization.api.GraphNode;

import java.util.List;
import java.util.Optional;

public interface GraphCoverageResult {

    String getResultSummary();

    List<GraphNode> getNodes();

    List<GraphRelationship> getRelationships();

    boolean hasPlan();

    boolean isProfilePlan();

    Optional<ResultsPlan> getPlan();
}
