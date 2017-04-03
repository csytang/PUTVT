package highlighter.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import highlighter.highlighters.MainHighlighter;

public class ClearVisualAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor;
        try {
             editor = e.getRequiredData(CommonDataKeys.EDITOR);
        }
        catch(AssertionError assertionError){
            createWarningHover(e, "You need to have an editor open for this action to take place.");
            return;
        }
        MainHighlighter mainHighlighter = MainHighlighter.getInstance();
        mainHighlighter.clear(editor);
    }

    private void createWarningHover(AnActionEvent anActionEvent, String message){

        StatusBar statusBar = WindowManager.getInstance()
                .getStatusBar(CommonDataKeys.PROJECT.getData(anActionEvent.getDataContext()));

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(message, MessageType.WARNING, null)
                .setFadeoutTime(30000)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()),
                        Balloon.Position.atRight);

    }
}
