package graph.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import graph.helper.HashtableResultsUtil;
import graph.pycharm.ConsoleToolWindow;
import graph.pycharm.services.ResultExecutionService;

public class ExecuteResultOnlyCoveragedAction extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR_EVEN_IF_INACTIVE);

        if (editor != null) {
            e.getPresentation().setEnabled(true);
        } else {
            e.getPresentation().setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {

        Project project = getEventProject(e);

        if (project == null) {
            System.out.println("No project.");
            return;
        }

        HashtableResultsUtil.getInstance().setOnlyCoveraged(true);

        MessageBus messageBus = project.getMessageBus();

        ResultExecutionService resultExecutionService = new ResultExecutionService(messageBus,project);
        ConsoleToolWindow.ensureOpen(project);
        resultExecutionService.executeResults();
    }




}
