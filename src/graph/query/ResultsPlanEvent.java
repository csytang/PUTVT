package graph.query;

import com.intellij.util.messages.Topic;
import graph.query.graph.GraphCoverageResult;


public interface ResultsPlanEvent {

    Topic<ResultsPlanEvent> QUERY_PLAN_EVENT = Topic.create("GraphDatabaseConsole.ResultsPlanEvent", ResultsPlanEvent.class);

    void queryPlanReceived(String query, GraphCoverageResult result);
}
