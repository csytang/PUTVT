package highlighter.actions.console;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import highlighter.highlighters.HighlightingMainController;

public class ConsoleSetVisualizationAction extends DumbAwareAction {
    private HighlightingMainController highlightingMainController;


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        HighlightingMainController.getInstance(null).setUseConsoleLogs(true);
    }

    @Override
    public void update(AnActionEvent e) {
        highlightingMainController = HighlightingMainController.getInstance(null);
        e.getPresentation().setVisible(!highlightingMainController.getUseConsoleLogs());
    }
}