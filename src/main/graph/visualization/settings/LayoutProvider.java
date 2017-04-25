package main.graph.visualization.settings;


import main.graph.pycharm.api.LookAndFeelService;
import main.graph.visualization.GraphDisplay;
import main.graph.visualization.layouts.CenteredLayout;
import main.graph.constants.GraphGroups;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.layout.graph.FruchtermanReingoldLayout;
import prefuse.activity.Activity;

public class LayoutProvider {

    public static ActionList forceLayout(Visualization viz, GraphDisplay display, LookAndFeelService lookAndFeelService) {
        ActionList actions = new ActionList(viz);
        FruchtermanReingoldLayout fruchtermanReingoldLayout = new FruchtermanReingoldLayout(GraphGroups.GRAPH);
        fruchtermanReingoldLayout.setMaxIterations(2000);
        actions.add(fruchtermanReingoldLayout);
        actions.add(ColorProvider.colors(lookAndFeelService));
        actions.add(new RepaintAndRepositionAction(viz, display));

        return actions;
    }

    public static ActionList repaintLayout(LookAndFeelService lookAndFeelService) {
        ActionList repaint = new ActionList(Activity.INFINITY);
        repaint.add(new CenteredLayout(GraphGroups.NODE_LABEL));
        repaint.add(new CenteredLayout(GraphGroups.EDGE_LABEL));
        repaint.add(ColorProvider.colors(lookAndFeelService));
        repaint.add(new RepaintAction());

        return repaint;
    }

    public static ActionList colorizeAll(Visualization viz, LookAndFeelService lookAndFeelService) {
        ActionList actionList = new ActionList(viz);
        actionList.add(ColorProvider.colors(lookAndFeelService));
        actionList.add(new RepaintAction());
        return actionList;
    }
}
