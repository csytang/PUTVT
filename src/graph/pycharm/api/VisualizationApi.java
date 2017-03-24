package graph.pycharm.api;


import graph.enums.EventType;
import graph.pycharm.console.GraphRelationship;
import graph.visualization.api.GraphNode;

import javax.swing.*;
import java.util.Hashtable;

public interface VisualizationApi {

    JComponent getCanvas();

    void addNode(GraphNode node);

    void addRelation(GraphRelationship relationship);

    void clear();

    void paint();

    void stop();

    void addNodeListener(EventType type, NodeCallback action);

    void addEdgeListener(EventType type, RelationshipCallback action);

    void resetPan();
}
