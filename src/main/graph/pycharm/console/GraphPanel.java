package main.graph.pycharm.console;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.BalloonBuilder;
import com.intellij.ui.ColorUtil;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.popup.BalloonPopupBuilderImpl;
import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.messages.MessageBus;
import main.graph.events.ResultsProcessEvent;
import main.graph.helper.DisplayUtil;
import main.graph.helper.PropertyTreeCellRenderer;
import main.graph.helper.UiHelper;
import main.graph.pycharm.GraphConsoleView;
import main.graph.pycharm.api.GraphRelationship;
import main.graph.pycharm.api.LookAndFeelService;
import main.graph.results.api.GraphCoverageResult;
import main.graph.visualization.api.GraphEntity;
import main.graph.visualization.api.GraphNode;
import prefuse.visual.VisualItem;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

/**
 * Taken from https://plugins.jetbrains.com/plugin/8087-graph-database-support source code
 */
public class GraphPanel {

    private PrefuseVisualization visualization;
    private LookAndFeelService lookAndFeelService;
    private BalloonBuilder balloonPopupBuilder;
    private Balloon balloon;
    private JBLabel balloonLabel = new JBLabel();
    private GraphPanelInteractions interactions;
    private Tree entityDetailsTree;
    private DefaultTreeModel entityDetailsTreeModel;

    public GraphPanel() {
        entityDetailsTreeModel = new DefaultTreeModel(null);
    }

    public void initialize(GraphConsoleView graphConsoleView, Project project) {
        MessageBus messageBus = project.getMessageBus();
        this.lookAndFeelService = graphConsoleView.getLookAndFeelService();
        this.entityDetailsTree = graphConsoleView.getEntityDetailsTree();

        // Bootstrap visualisation
        visualization = new PrefuseVisualization(lookAndFeelService);
        graphConsoleView.getGraphCanvas().add(visualization.getCanvas());

        // Entity data table
        entityDetailsTree.setCellRenderer(new PropertyTreeCellRenderer());
        entityDetailsTree.setModel(entityDetailsTreeModel);
        messageBus.connect().subscribe(ResultsProcessEvent.QUERY_EXECUTION_PROCESS_TOPIC, new ResultsProcessEvent() {
            @Override
            public void executionStarted() {
                entityDetailsTreeModel.setRoot(null);
            }

            @Override
            public void resultReceived( GraphCoverageResult result) {
                if (result.getNodes().isEmpty()) {
                    entityDetailsTreeModel.setRoot(null);
                } else {
                    entityDetailsTreeModel.setRoot(new PatchedDefaultMutableTreeNode("Select an item in the graph to view details..."));
                }
            }

            @Override
            public void postResultReceived() {
            }

            @Override
            public void handleError( Exception exception) {
            }

            @Override
            public void executionCompleted() {
            }
        });

        // Tooltips
        balloonBuilder();

        // Interactions
        this.interactions = new GraphPanelInteractions(
                graphConsoleView,
                messageBus,
                visualization,
                project);
    }

    public void showNodeData(GraphNode node, VisualItem item, MouseEvent e) {
        PatchedDefaultMutableTreeNode root = UiHelper.nodeToTreeNode(node.getRepresentation(), node);
        entityDetailsTreeModel.setRoot(root);

        Enumeration childs = root.children();
        while (childs.hasMoreElements()) {
            PatchedDefaultMutableTreeNode treeNode
                    = (PatchedDefaultMutableTreeNode) childs.nextElement();
            entityDetailsTree.expandPath(new TreePath(treeNode.getPath()));
        }
    }

    public void showRelationshipData(GraphRelationship relationship, VisualItem item, MouseEvent e) {
        PatchedDefaultMutableTreeNode root = UiHelper.relationshipToTreeNode(
                relationship.getRepresentation(), relationship);
        entityDetailsTreeModel.setRoot(root);

        Enumeration childs = root.children();
        while (childs.hasMoreElements()) {
            PatchedDefaultMutableTreeNode treeNode
                    = (PatchedDefaultMutableTreeNode) childs.nextElement();
            entityDetailsTree.expandPath(new TreePath(treeNode.getPath()));
        }
    }

    public void showTooltip(GraphEntity entity, VisualItem item, MouseEvent e) {
        if (balloon != null && !balloon.isDisposed()) {
            balloon.hide();
        }

        balloonPopupBuilder.setTitle(DisplayUtil.getTooltipTitle(entity));
        balloonLabel.setText(DisplayUtil.getTooltipText(entity));

        balloon = balloonPopupBuilder.createBalloon();
        Container panel = e.getComponent().getParent();

        final int magicNumber = 15;
        int heightOffset = balloon.getPreferredSize().height / 2 + magicNumber;

        int widthOffset;
        if (e.getX() > panel.getWidth() / 2) {
            widthOffset = balloon.getPreferredSize().width / 2;
        } else {
            widthOffset = panel.getWidth() - balloon.getPreferredSize().width / 2;
        }

        balloon.show(new RelativePoint(panel, new Point(widthOffset, heightOffset)), Balloon.Position.below);
    }

    public void resetPan() {
       // visualization.resetPan();
    }

    private void balloonBuilder() {
        final BalloonPopupBuilderImpl builder = new BalloonPopupBuilderImpl(null, balloonLabel);

        final Color bg = lookAndFeelService.getBackgroundColor();
        final Color borderOriginal = lookAndFeelService.getEdgeStrokeColor();
        final Color border = ColorUtil.toAlpha(borderOriginal, 75);
        builder
                .setShowCallout(false)
                .setDialogMode(false)
                .setAnimationCycle(20)
                .setFillColor(bg).setBorderColor(border).setHideOnClickOutside(true)
                .setHideOnKeyOutside(false)
                .setHideOnAction(false)
                .setCloseButtonEnabled(false)
                .setShadow(true);

        balloonPopupBuilder = builder;
    }

    public void hideTooltip(GraphEntity entity, VisualItem visualItem, MouseEvent mouseEvent) {
        balloon.dispose();
    }
}
