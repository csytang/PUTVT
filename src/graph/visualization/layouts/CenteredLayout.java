package graph.visualization.layouts;

import prefuse.action.layout.Layout;
import prefuse.visual.DecoratorItem;
import prefuse.visual.VisualItem;

import java.awt.geom.Rectangle2D;
import java.util.Iterator;
/**
 * Taken from https://plugins.jetbrains.com/plugin/8087-graph-database-support source code
 */
public class CenteredLayout extends Layout {
    public CenteredLayout(String group) {
        super(group);
    }

    @Override
    public void run(double frac) {
        Iterator iter = m_vis.items(m_group);
        while (iter.hasNext()) {
            DecoratorItem decorator = (DecoratorItem) iter.next();
            VisualItem decoratedItem = decorator.getDecoratedItem();
            Rectangle2D bounds = decoratedItem.getBounds();

            double x = bounds.getCenterX();
            double y = bounds.getCenterY();

            setX(decorator, null, x);
            setY(decorator, null, y);
        }
    }
}
