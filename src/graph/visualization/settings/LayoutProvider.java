package graph.visualization.settings;


import graph.pycharm.services.LookAndFeelService;
import graph.visualization.GraphDisplay;
import graph.visualization.layouts.CenteredLayout;
import graph.visualization.layouts.DynamicForceLayout;
import graph.visualization.layouts.RepaintAndRepositionAction;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.activity.Activity;


import static graph.constants.GraphGroups.EDGE_LABEL;
import static graph.constants.GraphGroups.GRAPH;
import static graph.constants.GraphGroups.NODE_LABEL;

public class LayoutProvider {

    private static final boolean ENFORCE_BOUNDS = false;

    public static ActionList forceLayout(Visualization viz, GraphDisplay display, LookAndFeelService lookAndFeel) {
        ActionList actions = new ActionList(viz);

        actions.add(new DynamicForceLayout(GRAPH, ENFORCE_BOUNDS));
        actions.add(ColorProvider.colors(lookAndFeel));
        actions.add(new RepaintAndRepositionAction(viz, display));

        return actions;
    }

    public static ActionList repaintLayout(LookAndFeelService lookAndFeelService) {
        ActionList repaint = new ActionList(Activity.INFINITY);
        repaint.add(new CenteredLayout(NODE_LABEL));
        repaint.add(new CenteredLayout(EDGE_LABEL));
        repaint.add(ColorProvider.colors(lookAndFeelService));
        repaint.add(new RepaintAction());

        return repaint;
    }
}
