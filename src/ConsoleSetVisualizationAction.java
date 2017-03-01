import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import highlighters.HighlightingMainController;

/**
 * Created by Cegin on 1.3.2017.
 */
public class ConsoleSetVisualizationAction extends AnAction {
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