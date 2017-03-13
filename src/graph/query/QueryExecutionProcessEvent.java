package graph.query;

import com.intellij.util.messages.Topic;

public interface QueryExecutionProcessEvent {

    Topic<QueryExecutionProcessEvent> QUERY_EXECUTION_PROCESS_TOPIC =
            Topic.create("GraphDatabaseConsole.QueryExecutionProcessTopic", QueryExecutionProcessEvent.class);

    void executionStarted(ExecuteQueryPayload payload);

    void resultReceived(ExecuteQueryPayload payload, GraphQueryResult result);

    void postResultReceived(ExecuteQueryPayload payload);

    void handleError(ExecuteQueryPayload payload, Exception exception);

    void executionCompleted(ExecuteQueryPayload payload);
}
