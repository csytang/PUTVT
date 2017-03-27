package graph.results;

import com.intellij.util.messages.Topic;
import graph.results.graph.GraphCoverageResult;


public interface ResultsPlanEvent {

    Topic<ResultsPlanEvent> QUERY_PLAN_EVENT = Topic.create("GraphDatabaseConsole.ResultsPlanEvent", ResultsPlanEvent.class);

    void queryPlanReceived(String query, GraphCoverageResult result);
}
