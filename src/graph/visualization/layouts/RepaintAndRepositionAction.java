package graph.visualization.layouts;


import graph.helper.PrefuseUtil;
import graph.visualization.GraphDisplay;
import prefuse.Visualization;
import prefuse.action.RepaintAction;

public class RepaintAndRepositionAction extends RepaintAction {

    private Visualization visualization;
    private GraphDisplay display;

    public RepaintAndRepositionAction(Visualization visualization, GraphDisplay display) {
        super(visualization);
        this.visualization = visualization;
        this.display = display;
    }

    @Override
    public void run(double frac) {
        PrefuseUtil.zoomAndPanToFit(visualization, display);
        super.run(frac);
    }
}
