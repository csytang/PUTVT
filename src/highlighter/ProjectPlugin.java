package highlighter;

import com.intellij.execution.ExecutionManager;
import com.intellij.execution.testframework.TestStatusListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.util.messages.MessageBusConnection;
import com.jetbrains.python.testing.PyTestFrameworkService;
import com.jetbrains.python.testing.VFSTestFrameworkListener;
import highlighter.handlers.DisplayHandler;
import highlighter.listeners.ConsoleRunListener;
import highlighter.listeners.EditorManagerListener;
import highlighter.listeners.FileOperationListener;

/**
 * Created by Cegin on 20.2.2017.
 */
public class ProjectPlugin {
    private Project project;

    public DisplayHandler displayHandler;

    public ProjectPlugin(Project project, DisplayHandler displayHandler) {
        this.project = project;
        this.displayHandler = displayHandler;
        registerListeners(displayHandler);

    }

    private void registerListeners(DisplayHandler displayHandler) {
        final MessageBusConnection connect = this.project.getMessageBus().connect(this.project);

        connect.subscribe(ExecutionManager.EXECUTION_TOPIC, new ConsoleRunListener(project));
        connect.subscribe(EditorManagerListener.FILE_EDITOR_MANAGER, new EditorManagerListener(displayHandler));
        VirtualFileManager.getInstance().addVirtualFileListener(new FileOperationListener(displayHandler));

    }

}
