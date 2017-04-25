package main.graph.visualization;

import com.intellij.util.ui.UIUtil;
import main.graph.enums.EventType;
import main.graph.helper.DisplayUtil;
import main.graph.helper.PrefuseUtil;
import main.graph.listeners.NodeListener;
import main.graph.listeners.RelationshipListener;
import main.graph.pycharm.api.GraphRelationship;
import main.graph.pycharm.api.LookAndFeelService;
import main.graph.pycharm.api.NodeCallback;
import main.graph.pycharm.api.RelationshipCallback;
import main.graph.visualization.api.GraphNode;
import main.graph.visualization.settings.CustomItemSorter;
import main.graph.visualization.settings.LayoutProvider;
import main.graph.visualization.settings.SchemaProvider;
import main.graph.constants.GraphColumns;
import main.graph.constants.GraphGroups;
import main.graph.visualization.settings.RendererProvider;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.RendererFactory;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;

import java.util.HashMap;
import java.util.Map;

import static prefuse.Constants.SHAPE_ELLIPSE;

/**
 * Class taken and remade from https://plugins.jetbrains.com/plugin/8087-graph-database-support plugin
 */
public class GraphDisplay extends Display {

    private static final boolean DIRECTED = true;

    private static final String LAYOUT = "layout";
    private static final String REPAINT = "repaint";
    private static final String COLORIZE = "colorize";

    private Graph graph;

    private Map<String, Node> nodeMap = new HashMap<>();
    private Map<String, GraphNode> graphNodeMap = new HashMap<>();
    private Map<String, GraphRelationship> graphRelationshipMap = new HashMap<>();
    private CustomNeighborHighlightControl highlightControl;
    private LookAndFeelService lookAndFeel;

    /**
     * Constructor, which sets all the startup stuff
     * @param lookAndFeel
     */
    public GraphDisplay(LookAndFeelService lookAndFeel) {
        super(new Visualization());

        if (UIUtil.isUnderDarcula()) {
            setBackground(lookAndFeel.getBackgroundColor().darker());
        } else {
            setBackground(lookAndFeel.getBackgroundColor());
        }

        graph = new Graph(DIRECTED);
        graph.addColumn(GraphColumns.ID, String.class);
        graph.addColumn(GraphColumns.TYPE, String.class);
        graph.addColumn(GraphColumns.TITLE, String.class);
        graph.addColumn(GraphColumns.COLOR_NUMBER, int.class);
        graph.addColumn(GraphColumns.OUT_COLOR_NUMBER, int.class);
        graph.addColumn(GraphColumns.EDGE_SIZE, double.class);

        m_vis.addGraph(GraphGroups.GRAPH, graph, null, SchemaProvider.provideNodeSchema(), SchemaProvider.provideEdgeSchema());
        m_vis.setInteractive(GraphGroups.EDGES, null, false);
        m_vis.setValue(GraphGroups.NODES, null, VisualItem.SHAPE, SHAPE_ELLIPSE);

        m_vis.addDecorators(GraphGroups.NODE_LABEL, GraphGroups.NODES, SchemaProvider.provideFontSchema());
        m_vis.addDecorators(GraphGroups.EDGE_LABEL, GraphGroups.EDGES, SchemaProvider.provideFontSchemaWithBackground());

        m_vis.setRendererFactory(setupRenderer());

        m_vis.putAction(LAYOUT, LayoutProvider.forceLayout(m_vis, this, lookAndFeel));
        m_vis.putAction(REPAINT, LayoutProvider.repaintLayout(lookAndFeel));
        m_vis.putAction(COLORIZE, LayoutProvider.colorizeAll(m_vis, lookAndFeel));

        setItemSorter(new CustomItemSorter());

        setHighQuality(true);

        addControlListener(new DragControl());
        addControlListener(new WheelZoomControl());
        addControlListener(new ZoomToFitControl());
        addControlListener(new PanControl());
        highlightControl = new CustomNeighborHighlightControl();
        addControlListener(highlightControl);
    }

    public void clearGraph() {
        highlightControl.clear();
        graph.clear();
        graph.clearSpanningTree();
    }

    public void addNodeListener(EventType type, NodeCallback callback) {
        addControlListener(new NodeListener(type, callback, graphNodeMap));
    }

    public void addEdgeListener(EventType type, RelationshipCallback callback) {
        addControlListener(new RelationshipListener(type, callback, graphRelationshipMap));
    }

    public void addNode(GraphNode graphNode) {
        Node node = graph.addNode();
        node.set(GraphColumns.ID, graphNode.getId());
        node.set(GraphColumns.TYPE, graphNode.getCoverage().toString());
        node.set(GraphColumns.TITLE, DisplayUtil.getProperty(graphNode));
        node.set(GraphColumns.COLOR_NUMBER, graphNode.getColor());
        node.set(GraphColumns.OUT_COLOR_NUMBER, graphNode.getOutColor());

        nodeMap.put(graphNode.getId(), node);
        graphNodeMap.put(graphNode.getId(), graphNode);
    }

    public void addRelationship(GraphRelationship graphRelationship) {
        if (graphRelationship.hasStartAndEndNode()) {
            String start = graphRelationship.getStartNode().getId();
            String end = graphRelationship.getEndNode().getId();

            Edge edge = graph.addEdge(nodeMap.get(start), nodeMap.get(end));
            edge.set(GraphColumns.ID, graphRelationship.getId());
            edge.set(GraphColumns.TITLE, graphRelationship.getCallsCount());
            edge.set(GraphColumns.EDGE_SIZE, graphRelationship.relationWeight());
            graphRelationshipMap.put(graphRelationship.getId(), graphRelationship);
        }
    }

    private RendererFactory setupRenderer() {
        DefaultRendererFactory rendererFactory = new DefaultRendererFactory(RendererProvider.nodeRenderer(), RendererProvider.edgeRenderer());
        rendererFactory.add(new InGroupPredicate(GraphGroups.NODE_LABEL), RendererProvider.labelRenderer());
        rendererFactory.add(new InGroupPredicate(GraphGroups.EDGE_LABEL), RendererProvider.edgeLabelRenderer());
        return rendererFactory;
    }

    public void startLayout() {
        m_vis.removeAction(LAYOUT);
        m_vis.removeAction(REPAINT);
        m_vis.removeAction(COLORIZE);
        m_vis.putAction(LAYOUT, LayoutProvider.forceLayout(m_vis, this, lookAndFeel));
        m_vis.putAction(REPAINT, LayoutProvider.repaintLayout(lookAndFeel));
        m_vis.putAction(COLORIZE, LayoutProvider.colorizeAll(m_vis, lookAndFeel));
        m_vis.run(LAYOUT);
        m_vis.run(REPAINT);
        m_vis.run(COLORIZE);

    }

    public void stopLayout() {
        m_vis.cancel(LAYOUT);
        m_vis.cancel(REPAINT);
        m_vis.cancel(COLORIZE);
    }

    public void colorize(){
        m_vis.removeAction(COLORIZE);
        m_vis.putAction(COLORIZE, LayoutProvider.colorizeAll(m_vis,lookAndFeel));
        m_vis.run(COLORIZE);
    }

    public void zoomAndPanToFit() {
        PrefuseUtil.zoomAndPanToFit(m_vis, this);
    }
}
