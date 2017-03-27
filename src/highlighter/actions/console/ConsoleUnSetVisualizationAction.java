package highlighter.actions.console;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import highlighter.highlighters.HighlightingMainController;

/**
 * Created by Cegin on 1.3.2017.
 */
public class ConsoleUnSetVisualizationAction extends AnAction {
    private HighlightingMainController highlightingMainController;
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        HighlightingMainController.getInstance(null).setUseConsoleLogs(false);
        HighlightingMainController.getInstance("");
    }

    @Override
    public void update(AnActionEvent e) {
        highlightingMainController = HighlightingMainController.getInstance(null);
        e.getPresentation().setVisible(highlightingMainController.getUseConsoleLogs());
    }
}
