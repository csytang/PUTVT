package graph.query;

import com.intellij.util.messages.Topic;
import graph.query.graph.GraphQueryResult;


public interface QueryPlanEvent {

    Topic<QueryPlanEvent> QUERY_PLAN_EVENT = Topic.create("GraphDatabaseConsole.QueryPlanEvent", QueryPlanEvent.class);

    void queryPlanReceived(String query, GraphQueryResult result);
}
