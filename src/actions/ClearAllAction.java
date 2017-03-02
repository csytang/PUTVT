package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import errors.ErrorManageFileControler;
import highlighters.MainHighlighter;
import pattern.PatternController;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Cegin on 3.11.2016.
 */
public class ClearAllAction extends AnAction {
    private ErrorManageFileControler errorManageFileControler;


    @Override
    public void actionPerformed(AnActionEvent e) {
        Hashtable hashtable = new Hashtable();
        hashtable.put(0, 0);
        ErrorManageFileControler.getInstance(hashtable);
    }

}


