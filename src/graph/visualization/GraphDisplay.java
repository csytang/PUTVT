package graph.visualization;

import com.intellij.util.ui.UIUtil;
import graph.enums.EventType;
import graph.helper.DisplayUtil;
import graph.helper.PrefuseUtil;
import graph.listeners.NodeListener;
import graph.listeners.RelationshipListener;
import graph.pycharm.api.GraphRelationship;
import graph.pycharm.api.LookAndFeelService;
import graph.pycharm.api.NodeCallback;
import graph.pycharm.api.RelationshipCallback;
import graph.visualization.api.GraphNode;
import graph.visualization.controls.CustomNeighborHighlightControl;
import graph.visualization.settings.CustomItemSorter;
import graph.visualization.settings.LayoutProvider;
import graph.visualization.settings.SchemaProvider;
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

import static graph.constants.GraphColumns.*;
import static graph.constants.GraphGroups.*;
import static graph.visualization.settings.RendererProvider.*;
import static prefuse.Constants.SHAPE_ELLIPSE;

public class GraphDisplay extends Display {

    private static final boolean DIRECTED = true;

    private static final String LAYOUT = "layout";
    private static final String REPAINT = "repaint";

    private Graph graph;

    private Map<String, Node> nodeMap = new HashMap<>();
    private Map<String, GraphNode> graphNodeMap = new HashMap<>();
    private Map<String, GraphRelationship> graphRelationshipMap = new HashMap<>();
    private CustomNeighborHighlightControl highlightControl;
    private LookAndFeelService lookAndFeel;

    public GraphDisplay(LookAndFeelService lookAndFeel) {
        super(new Visualization());

        if (UIUtil.isUnderDarcula()) {
            setBackground(lookAndFeel.getBackgroundColor().darker());
        } else {
            setBackground(lookAndFeel.getBackgroundColor());
        }

        graph = new Graph(DIRECTED);
        graph.addColumn(ID, String.class);
        graph.addColumn(TYPE, String.class);
        graph.addColumn(TITLE, String.class);
        graph.addColumn(COLOR_NUMBER, int.class);
        graph.addColumn(OUT_COLOR_NUMBER, int.class);
        graph.addColumn(EDGE_SIZE, float.class);

        m_vis.addGraph(GRAPH, graph, null, SchemaProvider.provideNodeSchema(), SchemaProvider.provideEdgeSchema());
        m_vis.setInteractive(EDGES, null, false);
        m_vis.setValue(NODES, null, VisualItem.SHAPE, SHAPE_ELLIPSE);

        m_vis.addDecorators(NODE_LABEL, NODES, SchemaProvider.provideFontSchema());
        m_vis.addDecorators(EDGE_LABEL, EDGES, SchemaProvider.provideFontSchemaWithBackground());

        m_vis.setRendererFactory(setupRenderer());

        m_vis.putAction(LAYOUT, LayoutProvider.forceLayout(m_vis, this, lookAndFeel));
        m_vis.putAction(REPAINT, LayoutProvider.repaintLayout(lookAndFeel));

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
    }

    public void addNodeListener(EventType type, NodeCallback callback) {
        addControlListener(new NodeListener(type, callback, graphNodeMap));
    }

    public void addEdgeListener(EventType type, RelationshipCallback callback) {
        addControlListener(new RelationshipListener(type, callback, graphRelationshipMap));
    }

    public void addNode(GraphNode graphNode) {
        Node node = graph.addNode();
        node.set(ID, graphNode.getId());
        node.set(TYPE, graphNode.getCoverage().toString());
        node.set(TITLE, DisplayUtil.getProperty(graphNode));
        node.set(COLOR_NUMBER, graphNode.getColor());
        node.set(OUT_COLOR_NUMBER, graphNode.getOutColor());

        nodeMap.put(graphNode.getId(), node);
        graphNodeMap.put(graphNode.getId(), graphNode);
    }

    public void addRelationship(GraphRelationship graphRelationship) {
        if (graphRelationship.hasStartAndEndNode()) {
            String start = graphRelationship.getStartNode().getId();
            String end = graphRelationship.getEndNode().getId();

            Edge edge = graph.addEdge(nodeMap.get(start), nodeMap.get(end));
            edge.set(ID, graphRelationship.getId());
            edge.set(TITLE, graphRelationship.getCallsCount());
            edge.set(EDGE_SIZE, graphRelationship.relationWeight());
            graphRelationshipMap.put(graphRelationship.getId(), graphRelationship);
        }
    }

    private RendererFactory setupRenderer() {
        DefaultRendererFactory rendererFactory = new DefaultRendererFactory(nodeRenderer(), edgeRenderer());
        rendererFactory.add(new InGroupPredicate(NODE_LABEL), labelRenderer());
        rendererFactory.add(new InGroupPredicate(EDGE_LABEL), edgeLabelRenderer());
        return rendererFactory;
    }

    public void startLayout() {
        m_vis.removeAction(LAYOUT);
        m_vis.removeAction(REPAINT);
        m_vis.putAction(LAYOUT, LayoutProvider.forceLayout(m_vis, this, lookAndFeel));
        m_vis.putAction(REPAINT, LayoutProvider.repaintLayout(lookAndFeel));
        m_vis.run(LAYOUT);
        m_vis.run(REPAINT);
    }

    public void stopLayout() {
        m_vis.cancel(LAYOUT);
        m_vis.cancel(REPAINT);
    }

    public void zoomAndPanToFit() {
        PrefuseUtil.zoomAndPanToFit(m_vis, this);
    }
}
