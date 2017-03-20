package graph.actions;

import com.intellij.coverage.CoverageDataManager;
import com.intellij.coverage.CoverageSuitesBundle;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import graph.events.ExecuteQueryEvent;
import graph.pycharm.ConsoleToolWindow;
import graph.pycharm.implementations.DataSource;
import graph.query.ExecuteQueryPayload;

public class ExecuteQueryAction extends AnAction {

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


        MessageBus messageBus = project.getMessageBus();

        ExecuteQueryPayload executeQueryPayload = new ExecuteQueryPayload();
        ConsoleToolWindow.ensureOpen(project);

        DataSource boundDataSource = new DataSource();

        CoverageSuitesBundle coverageSuitesBundle = CoverageDataManager.getInstance(project).getCurrentSuitesBundle();

        ExecuteQueryEvent executeQueryEvent = messageBus.syncPublisher(ExecuteQueryEvent.EXECUTE_QUERY_TOPIC);
        executeQueryEvent.executeQuery(boundDataSource, executeQueryPayload);
    }




}
