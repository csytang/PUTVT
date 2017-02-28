import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import errors.ErrorManageFileControler;
import highlighters.HighlightingMainController;
import pattern.PatternController;

import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by Cegin on 3.11.2016.
 */
public class VisualizationAction extends AnAction {
    private ErrorManageFileControler errorManageFileControler;
    private HighlightingMainController highlightingMainController;


    @Override
    public void actionPerformed(AnActionEvent e) {


        Hashtable hashtable = null;

        PatternController patternController = new PatternController();
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        try {
           hashtable = patternController.patternDecode(e);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        highlightingMainController = HighlightingMainController.getInstance(null);
        highlightingMainController.finishVisualization(hashtable,editor);

    }

    }


