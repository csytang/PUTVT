package main.graph.pycharm.api;

import java.awt.*;

public interface LookAndFeelService {

    Color getBackgroundColor();
    Color getBorderColor();
    Color getNodeStrokeColor();
    Color getNodeStrokeHoverColor();
    Color getNodeFillColor();
    Color getNodeFillHoverColor();
    Color getEdgeStrokeColor();
    Color getEdgeFillColor();

    Color getTextColor();
}
