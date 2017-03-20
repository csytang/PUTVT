package graph.visualization.settings;

import graph.pycharm.services.LookAndFeelService;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import prefuse.Constants;
import prefuse.action.ActionList;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.StrokeAction;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;

import java.awt.*;

import static graph.constants.GraphColumns.COLOR_NUMBER;
import static graph.constants.GraphColumns.TYPE;
import static graph.constants.GraphGroups.EDGES;
import static graph.constants.GraphGroups.NODES;
import static graph.constants.VisualizationParameters.EDGE_THICKNESS;
import static prefuse.visual.VisualItem.*;

public class ColorProvider {

    /*
     * Pastel color PALETTE for node coloring
     */
    private static final int ROT_1 = ColorLib.rgb(86, 219, 127);
    private static final int ROT_2 = ColorLib.rgb(86, 211, 219);
    private static final int ROT_3 = ColorLib.rgb(86, 111, 219);
    private static final int ROT_4 = ColorLib.rgb(145, 219, 86);
    private static final int ROT_5 = ColorLib.rgb(160, 86, 219);
    private static final int ROT_6 = ColorLib.rgb(219, 86, 178);
    private static final int ROT_7 = ColorLib.rgb(219, 94, 86);
    private static final int ROT_8 = ColorLib.rgb(219, 194, 86);

    private static final int GRAY = ColorLib.rgb(178, 178, 178);
    private static final int GRAY_DARK = ColorLib.rgb(90, 90, 90);

    private static final int EDGE = GRAY;
    private static final int EDGE_HOVER = GRAY_DARK;
    private static final int EDGE_HIGHLIGHT = GRAY_DARK;

    private static final int NODE_STROKE = GRAY;
    private static final int NODE_STROKE_HOVER = GRAY_DARK;
    private static final int NODE_STROKE_HIGHLIGHT = GRAY_DARK;

    private static final int[] PALETTE = {ROT_1, ROT_2, ROT_3, ROT_4, ROT_5, ROT_6, ROT_7, ROT_8};

    public static ActionList colors(LookAndFeelService lookAndFeelService) {
        ActionList colors = new ActionList();

        colors.add(getNodeStroke(lookAndFeelService));
        colors.add(getEdgeStroke(lookAndFeelService));
        colors.add(getEdgeFill(lookAndFeelService));
        colors.add(getNodeFill(lookAndFeelService));
        colors.add(getNodeStrokeThickness());
        colors.add(getEdgeStrokeThickness());

        return colors;
    }

    private static StrokeAction getNodeStrokeThickness() {
        StrokeAction stroke = new StrokeAction(NODES, new BasicStroke(EDGE_THICKNESS));
        stroke.add(HIGHLIGHT, new BasicStroke(EDGE_THICKNESS));
        stroke.add(HOVER, new BasicStroke(EDGE_THICKNESS));

        return stroke;
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
