package graph.pycharm.services;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.UIUtil;
import graph.pycharm.api.LookAndFeelService;

import javax.swing.*;
import java.awt.*;
/**
 * Taken from https://plugins.jetbrains.com/plugin/8087-graph-database-support source code
 */
public class IdeaLookAndFeelService implements LookAndFeelService {

    @Override
    public Color getBackgroundColor() {
        return UIUtil.getPanelBackground();
    }

    @Override
    public Color getBorderColor() {
        return JBColor.border();
    }

    @Override
    public Color getEdgeStrokeColor() {
        return UIUtil.getBoundsColor().darker();
    }

    @Override
    public Color getEdgeFillColor() {
        return UIUtil.getBoundsColor().darker();
    }

    @Override
    public Color getNodeStrokeColor() {
        return UIUtil.getBoundsColor().darker();
    }

    @Override
    public Color getNodeStrokeHoverColor() {
        return UIUtil.getBoundsColor().darker();
    }

    @Override
    public Color getNodeFillColor() {
        return UIManager.getColor("InternalFrame.inactiveTitleBackground");
    }

    @Override
    public Color getNodeFillHoverColor() {
        return UIManager.getColor("InternalFrame.activeTitleBackground");
    }

    @Override
    public Color getTextColor() {
        return UIManager.getColor("text");
    }

}
