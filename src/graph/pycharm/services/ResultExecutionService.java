package graph.pycharm.services;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import graph.results.CoverageResults;
import graph.results.ResultsProcessEvent;
import graph.results.graph.GraphCoverageResult;
import org.jetbrains.annotations.NotNull;

public class ResultExecutionService {

    private final MessageBus messageBus;
    private final Project project;

    public ResultExecutionService(MessageBus messageBus, Project project) {
        this.messageBus = messageBus;
        this.project=project;
    }

    public void executeResults() {
        try {
            executeInBackground();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void executeInBackground() {
        ResultsProcessEvent event = messageBus.syncPublisher(ResultsProcessEvent.QUERY_EXECUTION_PROCESS_TOPIC);
        event.executionStarted();
        ApplicationManager.getApplication()
                    .executeOnPooledThread(executeResults(event));

    }

    @NotNull
    private Runnable executeResults(ResultsProcessEvent event) {
        return () -> {
            try {

                GraphCoverageResult result = new CoverageResults(project);

                ApplicationManager.getApplication().invokeLater(() -> {
                    event.resultReceived(result);
                    event.postResultReceived();
                    event.executionCompleted();

                });
            } catch (Exception e) {
                ApplicationManager.getApplication().invokeLater(() -> {
                    event.handleError(e);
                    event.executionCompleted();
                });
            }
        };
    }
}
