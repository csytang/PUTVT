package main.graph.pycharm.api;


import main.graph.visualization.api.GraphNode;
import prefuse.visual.VisualItem;

import java.awt.event.MouseEvent;

@FunctionalInterface
public interface NodeCallback {
    void accept(GraphNode node, VisualItem item, MouseEvent e);
}
