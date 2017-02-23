import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import highlighters.MainHighlighter;


/**
 * Created by Cegin on 30.1.2017.
 */
public class ClearVisualAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        MainHighlighter mainHighlighter = new MainHighlighter(editor);
        mainHighlighter.clear();
    }
}
