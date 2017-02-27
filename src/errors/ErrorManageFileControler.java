package errors;


import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import highlighters.MainHighlighter;
import util.StringPytestUtil;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Cegin on 18.2.2017.
 */
public class ErrorManageFileControler {
    //SINGLETON
    private static ErrorManageFileControler instance = null;

    private Hashtable errorManageFileTable;
    private MainHighlighter mainHighlighter;



    protected ErrorManageFileControler(Hashtable hashtable) {
        this.errorManageFileTable = hashtable;
    }
    public static ErrorManageFileControler getInstance(Hashtable hashtable) {
        if (hashtable!=null && hashtable.get(0) != null){
            instance = new ErrorManageFileControler(null);
            return instance;
        }
        if(instance == null || hashtable != null) {
            instance = new ErrorManageFileControler(hashtable);
        }
        return instance;
    }

    public void decodeDTO(Hashtable hashtable, Editor editor){

        this.mainHighlighter = new MainHighlighter(editor);

       String editorFileName = getEditorOpenedFileName(editor);

        if (hashtable==null || hashtable.get(editorFileName)==null){
            return;
        }

       if (errorManageFileTable.get(editorFileName) == null){
           StringPytestUtil str = (StringPytestUtil) hashtable.get(editorFileName);
           errorManageFileTable.put(str.getFileName(), str);
       }

        StringPytestUtil str = (StringPytestUtil) errorManageFileTable.get(editorFileName);
        mainHighlighter.doHighlight(str);

    }

    public Hashtable getErrorManageFileTable() {
        return errorManageFileTable;
    }

    public StringPytestUtil getStringPytestUtilForFileName(Editor editor){
        String editorFileName = getEditorOpenedFileName(editor);
        return (StringPytestUtil) errorManageFileTable.get(editorFileName);
    }

    private String getEditorOpenedFileName(Editor editor){
        String tmp = editor.getDocument().toString();
        tmp = tmp.replace("]", "").replace('/', ' ');
        String arr[] = tmp.split(" ");
        return arr[arr.length - 1];
    }

}
