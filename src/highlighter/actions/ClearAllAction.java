package highlighter.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.jetbrains.cython.CythonNames;
import highlighter.errors.ErrorManageFileControler;
import highlighter.highlighters.HighlightingMainController;
import highlighter.highlighters.MainHighlighter;
import highlighter.util.ExternalLogsUtil;

import java.util.Hashtable;

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


