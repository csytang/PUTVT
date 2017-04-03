package highlighter.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import highlighter.errors.ErrorManageFileControler;
import highlighter.highlighters.MainHighlighter;

import java.util.Hashtable;

public class ClearAllAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Hashtable hashtable = new Hashtable();
        ErrorManageFileControler.getInstance(hashtable);
        for (Editor editor : EditorFactory.getInstance().getAllEditors()) {
            MainHighlighter mainHighlighter = MainHighlighter.getInstance();
            mainHighlighter.clear(editor);
        }
    }

}


