package main.highlighter.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import main.highlighter.highlighters.ErrorManageFileControler;
import main.highlighter.highlighters.HighlightingMainController;
import main.highlighter.highlighters.MainHighlighter;
import main.highlighter.util.ExternalLogsUtil;

import java.util.Hashtable;

/**
 * Cleas all the visualization and so far collected data
 */
public class ClearAllAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Hashtable hashtable = new Hashtable();
        ErrorManageFileControler.getInstance(hashtable);
        ExternalLogsUtil.getInstane().cleanLogs();
        HighlightingMainController.getInstance(null).clearLogs();
        for (Editor editor : EditorFactory.getInstance().getAllEditors()) {
            MainHighlighter mainHighlighter = MainHighlighter.getInstance();
            mainHighlighter.clear(editor);
        }
    }

}


