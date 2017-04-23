package highlighter.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.EditorFactory;
import highlighter.highlighters.HighlightingMainController;
import highlighter.pattern.PatternController;

import java.io.IOException;
import java.util.Hashtable;

/**
 * Visualize source code action
 */
public class VisualizationAction extends AnAction {
    private HighlightingMainController highlightingMainController;
    private Hashtable hashtable = null;

    @Override
    public void actionPerformed(AnActionEvent e) {

        hashtable = null;

        PatternController patternController = new PatternController();
        try {
           hashtable = patternController.patternDecode(e);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (hashtable == null){return;}

        highlightingMainController = HighlightingMainController.getInstance(null);
        highlightingMainController.finishVisualization(hashtable,EditorFactory.getInstance().getAllEditors(),e.getData(CommonDataKeys.EDITOR));
    }

}


