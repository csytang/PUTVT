package graph.query.graph;

import graph.pycharm.console.GraphRelationship;
import graph.query.api.GraphQueryNotification;
import graph.query.api.GraphQueryPlan;
import graph.query.api.GraphQueryResultColumn;
import graph.query.api.GraphQueryResultRow;
import graph.visualization.api.GraphNode;

import java.util.List;
import java.util.Optional;

public interface GraphQueryResult {

    long getExecutionTimeMs();

    String getResultSummary();

    List<GraphQueryResultColumn> getColumns();

    List<GraphQueryResultRow> getRows();

    List<GraphNode> getNodes();

    List<GraphRelationship> getRelationships();

    List<GraphQueryNotification> getNotifications();

    boolean hasPlan();

    boolean isProfilePlan();

    Optional<GraphQueryPlan> getPlan();
}
