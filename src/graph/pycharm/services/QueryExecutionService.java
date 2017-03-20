package graph.pycharm.services;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import graph.pycharm.api.DataSourceApi;
import graph.query.CoverageResults;
import graph.query.ExecuteQueryPayload;
import graph.query.QueryExecutionProcessEvent;
import graph.query.graph.GraphCoverageResult;
import org.jetbrains.annotations.NotNull;

public class QueryExecutionService {

    private final MessageBus messageBus;
    private final Project project;

    public QueryExecutionService(MessageBus messageBus, Project project) {
        this.messageBus = messageBus;
        this.project=project;
    }

    public void executeQuery(DataSourceApi dataSource, ExecuteQueryPayload payload) {
        try {
            executeInBackground(payload);
        } catch (Exception e) {

        }
    }

    private void executeInBackground( ExecuteQueryPayload payload) {
        QueryExecutionProcessEvent event = messageBus.syncPublisher(QueryExecutionProcessEvent.QUERY_EXECUTION_PROCESS_TOPIC);
        event.executionStarted(payload);
        ApplicationManager.getApplication()
                    .executeOnPooledThread(executeQuery(payload, event));

    }

    @NotNull
    private Runnable executeQuery(ExecuteQueryPayload payload, QueryExecutionProcessEvent event) {
        return () -> {
            if (payload.getQueries().size() != 1) {
                return;
            }

            try {

                GraphCoverageResult result = new CoverageResults(project);

                ApplicationManager.getApplication().invokeLater(() -> {
                    event.resultReceived(payload, result);
                    event.postResultReceived(payload);
                    event.executionCompleted(payload);

                });
            } catch (Exception e) {
                ApplicationManager.getApplication().invokeLater(() -> {
                    event.handleError(payload, e);
                    event.executionCompleted(payload);
                });
            }
        };
    }
}
