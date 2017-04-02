package graph.pycharm.console;

import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import graph.enums.EventType;
import graph.events.CleanCanvasEvent;
import graph.pycharm.GraphConsoleView;
import graph.pycharm.api.VisualizationApi;
import graph.results.api.GraphCoverageResult;
import graph.results.api.ResultsProcessEvent;


public class GraphPanelInteractions {

    private final GraphConsoleView graphConsoleView;
    private final MessageBus messageBus;
    private final VisualizationApi visualization;

    public GraphPanelInteractions(GraphConsoleView graphConsoleView,
                                  MessageBus messageBus, VisualizationApi visualization, Project project) {
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
                .subscribe(ResultsProcessEvent.QUERY_EXECUTION_PROCESS_TOPIC, new ResultsProcessEvent() {
                    @Override
                    public void executionStarted() {
                        visualization.stop();
                        visualization.clear();
                    }

                    @Override
                    public void resultReceived(GraphCoverageResult result) {
                        result.getNodes().forEach(visualization::addNode);
                        result.getRelationships().forEach(visualization::addRelation);
                    }

                    @Override
                    public void postResultReceived() {
                        visualization.paint();
                    }

                    @Override
                    public void handleError( Exception exception) {
                        visualization.stop();
                        visualization.clear();
                    }

                    @Override
                    public void executionCompleted() {
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
