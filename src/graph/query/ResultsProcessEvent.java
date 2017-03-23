package graph.query;

import com.intellij.util.messages.Topic;
import graph.query.graph.GraphCoverageResult;

public interface ResultsProcessEvent {

    Topic<ResultsProcessEvent> QUERY_EXECUTION_PROCESS_TOPIC =
            Topic.create("GraphDatabaseConsole.QueryExecutionProcessTopic", ResultsProcessEvent.class);

    void executionStarted();

    void resultReceived( GraphCoverageResult result);

    void postResultReceived();

    void handleError( Exception exception);

    void executionCompleted();
}
