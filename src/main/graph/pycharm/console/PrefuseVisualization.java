package main.graph.pycharm.console;


import main.graph.enums.EventType;
import main.graph.pycharm.api.*;
import main.graph.visualization.GraphDisplay;
import main.graph.visualization.api.GraphNode;
import org.apache.commons.lang.time.StopWatch;

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
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        display.startLayout();
        long stopTime = System.currentTimeMillis();
        stopWatch.stop();
        System.out.println("Execution took: " + stopWatch.getTime());
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

    public void colorize(){
        display.colorize();
    }
}
