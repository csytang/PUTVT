package graph.visualization.settings;

import graph.pycharm.services.LookAndFeelService;
import org.jetbrains.annotations.NotNull;
import prefuse.Constants;
import prefuse.action.ActionList;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.DataSizeAction;
import prefuse.action.assignment.StrokeAction;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;

import java.awt.*;

import static graph.constants.GraphColumns.COLOR_NUMBER;
import static graph.constants.GraphColumns.EDGE_SIZE;
import static graph.constants.GraphGroups.EDGES;
import static graph.constants.GraphGroups.NODES;
import static graph.constants.VisualizationParameters.EDGE_THICKNESS;
import static prefuse.visual.VisualItem.*;

public class ColorProvider {

    /*
     * Pastel color PALETTE for node coloring
     */
    private static final int ROT_1 = ColorLib.rgb(0, 255, 0); //100
    private static final int ROT_2 = ColorLib.rgb(43, 255, 0); //90
    private static final int ROT_3 = ColorLib.rgb(86, 255, 0); //80
    private static final int ROT_4 = ColorLib.rgb(129, 255, 0); //70
    private static final int ROT_5 = ColorLib.rgb(172, 255, 0);  //60
    private static final int ROT_6 = ColorLib.rgb(255, 255, 0);  //50
    private static final int ROT_7 = ColorLib.rgb(255, 172, 0); //40
    private static final int ROT_8 = ColorLib.rgb(255, 129, 0); //30
    private static final int ROT_9 = ColorLib.rgb(255, 86, 0);  //20
    private static final int ROT_10 = ColorLib.rgb(255, 43, 0); //10
    private static final int ROT_11 = ColorLib.rgb(255, 0, 0); //0

    private static final int GRAY = ColorLib.rgb(178, 178, 178);
    private static final int GRAY_DARK = ColorLib.rgb(90, 90, 90);

    private static final int EDGE = GRAY;
    private static final int EDGE_HOVER = GRAY_DARK;
    private static final int EDGE_HIGHLIGHT = GRAY_DARK;

    private static final int NODE_STROKE = GRAY;
    private static final int NODE_STROKE_HOVER = GRAY_DARK;
    private static final int NODE_STROKE_HIGHLIGHT = GRAY_DARK;

    private static final int[] PALETTE = {ROT_11, ROT_10, ROT_9, ROT_8, ROT_7, ROT_6, ROT_5, ROT_4, ROT_4, ROT_3, ROT_2, ROT_1};

    public static ActionList colors(LookAndFeelService lookAndFeelService) {
        ActionList colors = new ActionList();

        colors.add(getNodeStroke(lookAndFeelService));
        colors.add(getEdgeStroke(lookAndFeelService));
        colors.add(getEdgeFill(lookAndFeelService));
        colors.add(getNodeFill(lookAndFeelService));
        colors.add(getNodeStrokeThickness());
        colors.add(getEdgeStrokeThickness());
        colors.add(getEdgeThickness());
        return colors;
    }

    private static StrokeAction getNodeStrokeThickness() {
        StrokeAction stroke = new StrokeAction(NODES, new BasicStroke(EDGE_THICKNESS));
        stroke.add(HIGHLIGHT, new BasicStroke(EDGE_THICKNESS));
        stroke.add(HOVER, new BasicStroke(EDGE_THICKNESS));

        return stroke;
    }

    private static DataSizeAction getEdgeThickness(){
        DataSizeAction edgeWidth = new DataSizeAction(EDGES, EDGE_SIZE);
        return edgeWidth;
    }

    private static StrokeAction getEdgeStrokeThickness() {
        StrokeAction stroke = new StrokeAction(EDGES, new BasicStroke(EDGE_THICKNESS));
        stroke.add(HIGHLIGHT, new BasicStroke(EDGE_THICKNESS));
        stroke.add(HOVER, new BasicStroke(EDGE_THICKNESS));

        return stroke;
    }

    @NotNull
    private static ColorAction getNodeFill(LookAndFeelService lookAndFeelService) {
        DataColorAction fill = new DataColorAction(NODES, COLOR_NUMBER,
                Constants.NUMERICAL, VisualItem.FILLCOLOR, PALETTE);
      //  ColorAction fill = new ColorAction(NODES, FILLCOLOR, EDGE);
        return fill;
    }

    @NotNull
    private static ColorAction getEdgeFill(LookAndFeelService lookAndFeelService) {
        ColorAction arrow = new ColorAction(EDGES, FILLCOLOR, EDGE);
        arrow.add(HIGHLIGHT, EDGE_HIGHLIGHT);
        arrow.add(HOVER, EDGE_HOVER);

        return arrow;
    }

    @NotNull
    private static ColorAction getEdgeStroke(LookAndFeelService lookAndFeelService) {
        ColorAction nEdges = new ColorAction(EDGES, STROKECOLOR, NODE_STROKE);
        nEdges.add(HIGHLIGHT, NODE_STROKE_HIGHLIGHT);
        nEdges.add(HOVER, NODE_STROKE_HOVER);

        return nEdges;
    }

    @NotNull
    private static ColorAction getNodeStroke(LookAndFeelService lookAndFeelService) {
        ColorAction nStroke = new ColorAction(NODES, STROKECOLOR, NODE_STROKE);
        nStroke.add(HIGHLIGHT, NODE_STROKE_HIGHLIGHT);
        nStroke.add(HOVER, NODE_STROKE_HOVER);
        return nStroke;
    }
}