package main.highlighter.actions.externallogs;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import main.highlighter.util.ExternalLogsUtil;

/**
 * Stops listening for external logs on port
 */
public class StopListeningAction extends AnAction{
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        ExternalLogsUtil.getInstane().setBeingUsed(false);
    }

    public void update(AnActionEvent e) {
        e.getPresentation().setVisible(ExternalLogsUtil.getInstane().getBeingUsed());
        ExternalLogsUtil.getInstane().cleanLogs();
    }
}
