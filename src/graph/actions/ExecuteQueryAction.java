package graph.actions;

import com.intellij.openapi.actionSystem.ActionButtonComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.messages.MessageBus;
import graph.constants.GraphConstants;
import graph.enums.GraphLanguages;
import graph.events.ExecuteQueryEvent;
import graph.pycharm.ConsoleToolWindow;
import graph.pycharm.api.DataSourceApi;
import graph.pycharm.implementations.DataSource;
import graph.query.ExecuteQueryPayload;

import java.awt.*;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

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
        Editor editor = e.getData(CommonDataKeys.EDITOR_EVEN_IF_INACTIVE);
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);

        if (project == null) {
            System.out.println("No project.");
            return;
        }
        if (editor == null) {
            System.out.println("No editor.");
            return;
        }

        MessageBus messageBus = project.getMessageBus();
      //  DataSourcesComponent dataSourcesComponent = project.getComponent(DataSourcesComponent.class);

        Caret caret = editor.getCaretModel().getPrimaryCaret();

        String query = null;
        Map<String, Object> parameters = Collections.emptyMap();
        if (caret.hasSelection()) {
            query = caret.getSelectedText();
        } else if (psiFile != null) {
            if (psiFile.getLanguage().getID().equals("PYTHON")) {
             /*   PsiElement cypherStatement = getCypherStatement(psiFile, caret);
                if (cypherStatement != null) {
                  //  query = cypherStatement.getText();
                    try { // support parameters for PsiElement only
                    /*    ParametersService service = ServiceManager.getService(project, ParametersService.class);
                        parameters = service.getParameters(cypherStatement);
                    } catch (Exception exception) {
                        return;
                    }
                }*/
            }
        }


        if (query == null) {
            System.out.println("Execution error.");
        }

        query = decorateQuery("decorate");

        ExecuteQueryPayload executeQueryPayload = new ExecuteQueryPayload(query, parameters, editor);
        ConsoleToolWindow.ensureOpen(project);

        if (virtualFile != null) {
            String fileName = virtualFile.getName();
            if (fileName.startsWith(GraphConstants.BOUND_DATA_SOURCE_PREFIX)) {
                Optional<DataSource> boundDataSource = Optional.of(new DataSource());
                if (boundDataSource.isPresent()) {
                    executeQuery(messageBus, boundDataSource.get(), executeQueryPayload);
                    return;
                }
            }
        }

       /* ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                "Choose Data Source",
                new ChooseDataSourceActionGroup(messageBus, dataSourcesComponent, executeQueryPayload),
                e.getDataContext(),
                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                false
        );*/

        Component eventComponent = e.getInputEvent().getComponent();
        if (eventComponent instanceof ActionButtonComponent) {
          //  popup.showUnderneathOf(eventComponent);
        } else {
          //  popup.showInBestPositionFor(e.getDataContext());
        }
    }

    public void executeQuery(MessageBus messageBus, DataSourceApi dataSource, ExecuteQueryPayload payload) {
        ExecuteQueryEvent executeQueryEvent = messageBus.syncPublisher(ExecuteQueryEvent.EXECUTE_QUERY_TOPIC);
        executeQueryEvent.executeQuery(dataSource, payload);
    }

    protected String decorateQuery(String query) {
        return query;
    }

    /*private PsiElement getCypherStatement(PsiFile psiFile, Caret caret) {
        return PsiTraversalUtilities.Cypher.getCypherStatementAtOffset(psiFile, caret.getOffset());
    }*/


}
