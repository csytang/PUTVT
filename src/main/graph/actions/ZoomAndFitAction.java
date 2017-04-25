package main.graph.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import main.graph.events.ZoomAndPanToFitEvent;

/**
 * Reset focus for the visualization
 */
public class ZoomAndFitAction extends AnAction{
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
        messageBus.syncPublisher(ZoomAndPanToFitEvent.ZOOM_AND_PAN_TO_FIT_EVENT_TOPIC).resetPan();
    }
}
