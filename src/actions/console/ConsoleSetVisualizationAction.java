package actions.console;

import com.intellij.execution.ExecutionManager;
import com.intellij.execution.RunManager;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.ui.*;
import com.intellij.execution.ui.layout.impl.RunnerLayoutUiImpl;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.xdebugger.XDebugSession;
import com.intellij.xdebugger.XDebuggerManager;
import highlighters.HighlightingMainController;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;

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