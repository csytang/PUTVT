package highlighter.actions.console;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import highlighter.highlighters.HighlightingMainController;

/**
 * Created by Cegin on 1.3.2017.
 */
public class ConsoleSetVisualizationAction extends DumbAwareAction {
    private HighlightingMainController highlightingMainController;


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        Project eventProject = getEventProject(anActionEvent);

      /*  ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(eventProject);
        ToolWindow toolWindow = ToolWindowManager.getInstance(eventProject).getToolWindow("Run");
        ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(eventProject).getConsole();
        Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "A Console Foo Bar", true);
        toolWindow.getContentManager().addContent(content);*/

        HighlightingMainController.getInstance(null).setUseConsoleLogs(true);


    }

    @Override
    public void update(AnActionEvent e) {
        highlightingMainController = HighlightingMainController.getInstance(null);
        e.getPresentation().setVisible(!highlightingMainController.getUseConsoleLogs());
    }
}