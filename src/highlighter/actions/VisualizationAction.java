package highlighter.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import highlighter.errors.ErrorManageFileControler;
import highlighter.highlighters.HighlightingMainController;
import highlighter.pattern.PatternController;

import java.io.IOException;
import java.util.Hashtable;

//TODO consider the refactoring of this, so the visualization is done only when wanted or triggered, not when files are loaded
public class VisualizationAction extends AnAction {
    private HighlightingMainController highlightingMainController;


    @Override
    public void actionPerformed(AnActionEvent e) {

        Editor editor;
        Hashtable hashtable = null;

        PatternController patternController = new PatternController();
        try {
             editor = e.getRequiredData(CommonDataKeys.EDITOR);
        }
        catch(AssertionError assertionError){
            createWarningHover(e, "You need to have an editor open for this action to take place.");
            return;
        }
        try {
           hashtable = patternController.patternDecode(e);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (hashtable == null){return;}
        ErrorManageFileControler.getInstance(hashtable);
        highlightingMainController = HighlightingMainController.getInstance(null);
        highlightingMainController.finishVisualization(hashtable,editor,EditorFactory.getInstance().getAllEditors());
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


