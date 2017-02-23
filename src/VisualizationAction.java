import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import errors.ErrorManageFileControler;
import highlighters.MainHighlighter;
import pattern.PatternController;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Cegin on 3.11.2016.
 */
public class VisualizationAction extends AnAction {
    private ErrorManageFileControler errorManageFileControler;


    @Override
    public void actionPerformed(AnActionEvent e) {
        //PsiElement.ARRAY_FACTORY.create()


        Hashtable hashtable = null;

        PatternController patternController = new PatternController();
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        String str = editor.getDocument().getText();
        try {
           hashtable = patternController.patternDecode(e);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        errorManageFileControler = ErrorManageFileControler.getInstance(hashtable);
        errorManageFileControler.decodeDTO(hashtable,editor);
        // String tmp = patternPythonDecoder.getExceptionReason();
        /* for (Integer line : lines) {
            mainHighlighter.highlightLines(new Color(255, 0, 0, 255), line, line);
        }
        /*JPanel panel = new JPanel(new BorderLayout());
        panel.add(new EditorTextField(), BorderLayout.CENTER);
        /*PopupFactoryImpl popupFactory = new PopupFactoryImpl();
        popupFactory.createComponentPopupBuilder(panel, new EditorTextField()).createPopup().showInBestPositionFor(editor);*/
    }

    }


