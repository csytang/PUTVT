package graph.query.api;



import graph.pycharm.console.GraphRelationship;
import graph.visualization.api.GraphNode;

import java.util.List;

public interface GraphQueryResultRow {

    Object getValue(GraphQueryResultColumn column);

    List<GraphNode> getNodes();

    List<GraphRelationship> getRelationships();
}
