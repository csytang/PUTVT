package graph.query;



import graph.pycharm.console.GraphRelationship;
import graph.visualization.api.GraphNode;

import java.util.List;

public interface GraphQueryResult {

    long getExecutionTimeMs();

    String getResultSummary();



    List<GraphNode> getNodes();

    List<GraphRelationship> getRelationships();



    boolean hasPlan();

    boolean isProfilePlan();


}
