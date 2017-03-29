package graph.results.api;

import com.intellij.util.messages.Topic;


public interface ResultsPlanEvent {

    Topic<ResultsPlanEvent> QUERY_PLAN_EVENT = Topic.create("GraphDatabaseConsole.ResultsPlanEvent", ResultsPlanEvent.class);

    void queryPlanReceived(String query, GraphCoverageResult result);
}
