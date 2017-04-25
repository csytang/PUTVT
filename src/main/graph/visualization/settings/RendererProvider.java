package main.graph.visualization.settings;


import main.graph.constants.VisualizationParameters;
import main.graph.renderers.CustomEdgeRenderer;
import main.graph.constants.GraphColumns;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.render.ShapeRenderer;

import static main.graph.constants.VisualizationParameters.NODE_SIZE;
import static prefuse.Constants.EDGE_TYPE_LINE;
import static prefuse.data.io.GraphMLReader.Tokens.ID;
/**
 * Inspiration from https://plugins.jetbrains.com/plugin/8087-graph-database-support source code
 */
public class RendererProvider {

    private static final int TEXT_OVERLAP = 12;

    public static LabelRenderer labelRenderer() {
        LabelRenderer labelRenderer = new LabelRenderer(ID);
        labelRenderer.setMaxTextWidth(NODE_SIZE - TEXT_OVERLAP);

        return labelRenderer;
    }

    public static LabelRenderer edgeLabelRenderer() {
        LabelRenderer labelRenderer = new LabelRenderer(GraphColumns.TITLE);
        labelRenderer.setMaxTextWidth(NODE_SIZE - TEXT_OVERLAP);

        return labelRenderer;
    }

    public static ShapeRenderer nodeRenderer() {
        ShapeRenderer nodeRenderer = new ShapeRenderer();
        nodeRenderer.setBaseSize(NODE_SIZE);

        return nodeRenderer;
    }

    public static EdgeRenderer edgeRenderer() {
        EdgeRenderer edgeRenderer = new CustomEdgeRenderer(EDGE_TYPE_LINE);
        edgeRenderer.setDefaultLineWidth(VisualizationParameters.EDGE_THICKNESS);
        edgeRenderer.setArrowHeadSize(10, 15);

        return edgeRenderer;
    }
}
