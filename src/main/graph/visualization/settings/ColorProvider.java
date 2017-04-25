package main.graph.visualization.settings;

import main.graph.helper.HashtableResultsUtil;
import main.graph.pycharm.api.LookAndFeelService;
import main.graph.visualization.api.GraphNode;
import main.graph.constants.GraphColumns;
import main.graph.constants.GraphGroups;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static main.graph.constants.VisualizationParameters.EDGE_THICKNESS;
import static prefuse.visual.VisualItem.*;

/**
 * Class inspired by the ColorProvider class from https://plugins.jetbrains.com/plugin/8087-graph-database-support plugin
 */
public class ColorProvider {

    /*
     * Pastel color PALETTE for node coloring
     */
    private static  int ROT_1 = ColorLib.rgb(0, 255, 0); //100
    private static  int ROT_2 = ColorLib.rgb(43, 255, 0); //90
    private static  int ROT_3 = ColorLib.rgb(86, 255, 0); //80
    private static  int ROT_4 = ColorLib.rgb(129, 255, 0); //70
    private static  int ROT_5 = ColorLib.rgb(172, 255, 0);  //60
    private static  int ROT_6 = ColorLib.rgb(255, 255, 0);  //50
    private static  int ROT_7 = ColorLib.rgb(255, 172, 0); //40
    private static  int ROT_8 = ColorLib.rgb(255, 129, 0); //30
    private static  int ROT_9 = ColorLib.rgb(255, 86, 0);  //20
    private static  int ROT_10 = ColorLib.rgb(255, 43, 0); //10
    private static  int ROT_11 = ColorLib.rgb(255, 0, 0); //0

    private static final int RED_EDGE = ColorLib.rgb(255,0,0);
    private static final int GREEN_EDGE = ColorLib.rgb(0,255,0);

    private static final int GRAY = ColorLib.rgb(178, 178, 178);
    private static final int GRAY_DARK = ColorLib.rgb(90, 90, 90);

    private static final int EDGE = GRAY;
    private static final int EDGE_HOVER = GRAY_DARK;
    private static final int EDGE_HIGHLIGHT = GRAY_DARK;

    private static final int NODE_STROKE = GRAY;
    private static final int NODE_STROKE_HOVER = GRAY_DARK;
    private static final int NODE_STROKE_HIGHLIGHT = GRAY_DARK;

    private static  int[] PALETTE = {ROT_11, ROT_10, ROT_9, ROT_8, ROT_7, ROT_6, ROT_5, ROT_4, ROT_4, ROT_3, ROT_2, ROT_1};

    private static  int[] EDGE_PALLETE = {EDGE, GREEN_EDGE, RED_EDGE, ROT_6};

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
        StrokeAction stroke = new StrokeAction(GraphGroups.NODES, new BasicStroke(EDGE_THICKNESS));
        stroke.add(HIGHLIGHT, new BasicStroke(EDGE_THICKNESS));
        stroke.add(HOVER, new BasicStroke(EDGE_THICKNESS));

        return stroke;
    }

    /**
     * Get edge thickness - set log scale
     */
    private static DataSizeAction getEdgeThickness(){
        DataSizeAction edgeWidth = new DataSizeAction(GraphGroups.EDGES, GraphColumns.EDGE_SIZE);
        edgeWidth.setScale(Constants.LOG_SCALE);
        edgeWidth.setMaximumSize(3.0f);
        edgeWidth.setMinimumSize(1.0f);
        return edgeWidth;
    }

    private static StrokeAction getEdgeStrokeThickness() {
        StrokeAction stroke = new StrokeAction(GraphGroups.EDGES, new BasicStroke(EDGE_THICKNESS));
        stroke.add(HIGHLIGHT, new BasicStroke(EDGE_THICKNESS));
        stroke.add(HOVER, new BasicStroke(EDGE_THICKNESS));

        return stroke;
    }


    /**
     *     Gets node colours to be used - colours depends from the number of colours actually to be used
     *     If we for example use only the 1st,6th and 8th colour, only these need to passed, otherwise an error occurs
     */
    @NotNull
    private static ColorAction getNodeFill(LookAndFeelService lookAndFeelService) {
        DataColorAction fill;
        if (!(HashtableResultsUtil.getInstance().getNodes().size()>1)) {
             fill = new DataColorAction(GraphGroups.NODES, GraphColumns.COLOR_NUMBER,
                    Constants.NUMERICAL, VisualItem.FILLCOLOR, PALETTE);
        }
        else{
            List<Integer> ints = new ArrayList<>();
            for(GraphNode node : HashtableResultsUtil.getInstance().getNodes()){
                if (!ints.contains(node.getColor())) {
                    ints.add(node.getColor());
                }
            }
            Collections.sort(ints);
            int[] arr = new int[ints.size()];
            int j=0;
            for (Integer i : ints){
                arr[j++] = PALETTE[i];
            }
            fill = new DataColorAction(GraphGroups.NODES, GraphColumns.COLOR_NUMBER,
                    Constants.NUMERICAL, VisualItem.FILLCOLOR, arr);
        }
        return fill;
    }

    @NotNull
    private static ColorAction getEdgeFill(LookAndFeelService lookAndFeelService) {
        ColorAction arrow = new ColorAction(GraphGroups.EDGES, FILLCOLOR, EDGE);
        arrow.add(HIGHLIGHT, EDGE_HIGHLIGHT);
        arrow.add(HOVER, EDGE_HOVER);

        return arrow;
    }

    @NotNull
    private static ColorAction getEdgeStroke(LookAndFeelService lookAndFeelService) {
        ColorAction nEdges = new ColorAction(GraphGroups.EDGES, STROKECOLOR, NODE_STROKE);
        nEdges.add(HIGHLIGHT, NODE_STROKE_HIGHLIGHT);
        nEdges.add(HOVER, NODE_STROKE_HOVER);

        return nEdges;
    }

    /**
     * This gets the colour for the edges of the Node - Green,Red,Yellow and Grey.
     * Only colours to be used are returned
     * @param lookAndFeelService
     * @return
     */
    @NotNull
    private static ColorAction getNodeStroke(LookAndFeelService lookAndFeelService) {
        DataColorAction nStroke;
        if (!(HashtableResultsUtil.getInstance().getNodes().size()>1)) {
            nStroke = new DataColorAction(GraphGroups.NODES, GraphColumns.COLOR_NUMBER,
                    Constants.NUMERICAL, VisualItem.FILLCOLOR, EDGE_PALLETE);
        }
        else{
            List<Integer> ints = new ArrayList<>();
            for(GraphNode node : HashtableResultsUtil.getInstance().getNodes()){
                if (!ints.contains(node.getOutColor())) {
                    ints.add(node.getOutColor());
                }
            }
            Collections.sort(ints);
            int[] arr = new int[ints.size()];
            int j=0;
            for (Integer i : ints){
                arr[j++] = EDGE_PALLETE[i];
            }
            nStroke = new DataColorAction(GraphGroups.NODES, GraphColumns.OUT_COLOR_NUMBER, Constants.NUMERICAL, VisualItem.STROKECOLOR, arr);
        }
        nStroke.add(HIGHLIGHT, NODE_STROKE_HIGHLIGHT);
        nStroke.add(HOVER, NODE_STROKE_HOVER);
        return nStroke;
    }
}
