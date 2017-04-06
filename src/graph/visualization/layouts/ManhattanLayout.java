package graph.visualization.layouts;

import prefuse.action.layout.Layout;
import prefuse.visual.AggregateItem;
import prefuse.visual.VisualItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * @author Christian Laireiter
 */
public class ManhattanLayout extends Layout{
    /**
     * Default vertical distance (space between) the visual items of one group.
     */
    public static final int DISTANCE = 100;
    /**
     * The group members excluding the first item will be placed a little bit to
     * the right.<br>
     */
    public static final int HORIZ_OFFSET = 50;
    /**
     * Internal flag which tells, if something happend, that needs this layout
     * to recompute.
     */
    private AtomicBoolean dirty = new AtomicBoolean(true);
    /**
     * Creates an instance.<br>
     */
    public ManhattanLayout(String vis) {
        super(vis);
    }
    /**
     * Takes the <code>item</code>'s {@linkplain AggregateItem#items() visual
     * items}, and puts them into the provided list. (the list is cleared)<br>
     *
     * @param item
     *            The aggregate item containing the visual items.
     * @param result
     *            the list which will receive the visual items.
     */
    @SuppressWarnings("unchecked")
    private void collectAggrItems(AggregateItem item, List<VisualItem> result) {
        result.clear();
        Iterator items = item.items();
        while (items.hasNext()) {
            result.add((VisualItem) items.next());
        }
    }
    /**
     * (overridden)
     *
     * @see prefuse.activity.Activity#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return this.dirty.get() && super.isEnabled();
    }
    /**
     * @param groupItems
     * @return
     */
    private void orderItems(List<VisualItem> groupItems, List<VisualItem> result) {
  /*    result.clear();
        if (!groupItems.isEmpty()) {
            Map<Object, VisualItem> map = new HashMap<Object, VisualItem>();
            for (VisualItem curr : groupItems) {
                map.put(PrefuseUtil.extractGroupedObject(curr), curr);
            }
            Item groupObj = (Item) PrefuseUtil
                    .extractGroupedObject(groupItems.get(0));
            for (Object curr : groupObj.getGroup()) {
                if (map.containsKey(curr)) {
                    /*
                     * We need to ask if this object is in the map (in the
                     * groupItems list), because the group object is manipulated
                     * outside the current Thread (Prefuse Activitymanager),
                     * objects may have been added.

                    result.add(map.remove(curr));
                }
            }
            /*
             * The other way around, a visual items representation has been
             * removed from the group. However it still is in the graph. However
             * its order is now random

            for (VisualItem left : map.values()) {
                result.add(left);
            }
        }*/
    }
    /**
     * (overridden)
     *
     * @see prefuse.action.GroupAction#run(double)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void run(double frac) {
        this.dirty.set(false);
        Iterator<Object> items = getVisualization()
                .items();
        List<VisualItem> groupItems = new ArrayList<VisualItem>();
        List<VisualItem> orderedItems = new ArrayList<VisualItem>();
        while (items.hasNext()) {
            collectAggrItems((AggregateItem) items.next(), groupItems);
            if (!groupItems.isEmpty()) {
                orderedItems = groupItems;
                VisualItem first = orderedItems.remove(0);
                double lastMaxY = first.getBounds().getMaxY();
                for (VisualItem current : orderedItems) {
                    current.setX(first.getX() + HORIZ_OFFSET);
                    current.setY(lastMaxY + DISTANCE);
                    lastMaxY = current.getBounds().getMaxY();
                }
            }
        }
    }

}

