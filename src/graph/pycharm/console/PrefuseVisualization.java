package graph.pycharm.console;



import graph.enums.EventType;
import graph.pycharm.api.GraphRelationship;
import graph.pycharm.api.NodeCallback;
import graph.pycharm.api.RelationshipCallback;
import graph.pycharm.api.VisualizationApi;
import graph.pycharm.api.LookAndFeelService;
import graph.visualization.GraphDisplay;
import graph.visualization.api.GraphNode;

import javax.swing.*;

public class PrefuseVisualization implements VisualizationApi {

    private GraphDisplay display;

    public PrefuseVisualization(LookAndFeelService lookAndFeelService) {
        this.display = new GraphDisplay(lookAndFeelService);
    }

    @Override
    public void addNode(GraphNode node) {
        display.addNode(node);
    }

    @Override
    public void addRelation(GraphRelationship relationship) {
        display.addRelationship(relationship);
    }

    @Override
    public void clear() {
        display.clearGraph();
    }

    @Override
    public void paint() {
        display.startLayout();
    }

    @Override
    public void stop() {
        display.stopLayout();
    }

    @Override
    public JComponent getCanvas() {
        return display;
    }

    @Override
    public void addNodeListener(EventType type, NodeCallback action) {
        display.addNodeListener(type, action);
    }

    @Override
    public void addEdgeListener(EventType type, RelationshipCallback action) {
        display.addEdgeListener(type, action);
    }

    @Override
    public void resetPan() {
        display.zoomAndPanToFit();
    }
}
