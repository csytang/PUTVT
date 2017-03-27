package graph.helper;

import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class PropertyTreeCellRenderer extends ColoredTreeCellRenderer {

    @Override
    public void customizeCellRenderer(@NotNull JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Object userObject = ((DefaultMutableTreeNode) value).getUserObject();

        if (userObject instanceof KeyValuePair) {
            KeyValuePair pair = (KeyValuePair) userObject;

            append(pair.getKey() + ": ", SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES, true);
            if (pair.isValueData()) {
                append(pair.getValue().toString());
            } else {
                append(pair.getValue().toString(), SimpleTextAttributes.GRAYED_SMALL_ATTRIBUTES);
            }
        } else {
            append(userObject.toString());
        }
    }
}
