package graph.pycharm.console;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.util.messages.MessageBus;
import graph.EventType;
import graph.events.CleanCanvasEvent;
import graph.pycharm.GraphConsoleView;
import graph.pycharm.api.VisualizationApi;
import graph.query.ExecuteQueryPayload;
import graph.query.GraphQueryResult;
import graph.query.QueryExecutionProcessEvent;


public class GraphPanelInteractions {

    private final GraphConsoleView graphConsoleView;
    private final MessageBus messageBus;

    private final VisualizationApi visualization;

    public GraphPanelInteractions(GraphConsoleView graphConsoleView,
                                  MessageBus messageBus, VisualizationApi visualization) {
        this.graphConsoleView = graphConsoleView;
        this.messageBus = messageBus;
        this.visualization = visualization;


        registerMessageBusSubscribers();
        registerVisualisationEvents();
    }

    private void registerMessageBusSubscribers() {

        messageBus.connect()
                .subscribe(CleanCanvasEvent.CLEAN_CANVAS_TOPIC, () -> {
                    visualization.stop();
                    visualization.clear();
                    visualization.paint();
                });
        messageBus.connect()
                .subscribe(QueryExecutionProcessEvent.QUERY_EXECUTION_PROCESS_TOPIC, new QueryExecutionProcessEvent() {
                    @Override
                    public void executionStarted(ExecuteQueryPayload payload) {
                        visualization.stop();
                        visualization.clear();
                    }

                    @Override
                    public void resultReceived(ExecuteQueryPayload payload, GraphQueryResult result) {
                        result.getNodes().forEach(visualization::addNode);
                        result.getRelationships().forEach(visualization::addRelation);
                    }

                    @Override
                    public void postResultReceived(ExecuteQueryPayload payload) {
                        visualization.paint();
                    }

                    @Override
                    public void handleError(ExecuteQueryPayload payload, Exception exception) {
                        String errorMessage = exception.getMessage() == null ? "Error occurred" : "Error occurred: " + exception.getMessage();
                        payload.getEditor().ifPresent(editor -> HintManager.getInstance().showErrorHint(editor, errorMessage));

                        visualization.stop();
                        visualization.clear();
                    }

                    @Override
                    public void executionCompleted(ExecuteQueryPayload payload) {
                    }
                });
    }

    private void registerVisualisationEvents() {
        visualization.addNodeListener(EventType.CLICK, graphConsoleView.getGraphPanel()::showNodeData);
        visualization.addEdgeListener(EventType.CLICK, graphConsoleView.getGraphPanel()::showRelationshipData);
        visualization.addNodeListener(EventType.HOVER_START, graphConsoleView.getGraphPanel()::showTooltip);
        visualization.addNodeListener(EventType.HOVER_END, graphConsoleView.getGraphPanel()::hideTooltip);
        visualization.addEdgeListener(EventType.HOVER_START, graphConsoleView.getGraphPanel()::showTooltip);
        visualization.addEdgeListener(EventType.HOVER_END, graphConsoleView.getGraphPanel()::hideTooltip);
    }
}
