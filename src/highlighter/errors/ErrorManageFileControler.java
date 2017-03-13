package highlighter.errors;


import com.intellij.openapi.editor.Editor;
import highlighter.highlighters.MainHighlighter;
import highlighter.util.StringPytestUtil;

import java.util.Hashtable;

/**
 * Created by Cegin on 18.2.2017.
 */
public class ErrorManageFileControler {
    //SINGLETON
    private static ErrorManageFileControler instance = null;

    private Hashtable errorManageFileTable = new Hashtable();
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

        this.mainHighlighter = MainHighlighter.getInstance();

       String editorFileName = getEditorOpenedFileName(editor);

        if (hashtable==null || hashtable.get(editorFileName)==null){
            return;
        }

        StringPytestUtil str = (StringPytestUtil) hashtable.get(editorFileName);
        mainHighlighter.doHighlight(str,editor);

    }

    public Hashtable getErrorManageFileTable() {
        return errorManageFileTable;
    }

    public void setErrorManageFileTable(Hashtable errorManageFileTable){
        this.errorManageFileTable=errorManageFileTable;
    }

    public StringPytestUtil getStringPytestUtilForFileName(Editor editor){
        String editorFileName = getEditorOpenedFileName(editor);
        if (errorManageFileTable == null){
            return null;
        }
        return (StringPytestUtil) errorManageFileTable.get(editorFileName);
    }

    private String getEditorOpenedFileName(Editor editor){
        String tmp = editor.getDocument().toString();
        tmp = tmp.replace("]", "").replace('/', ' ');
        String arr[] = tmp.split(" ");
        return arr[arr.length - 1];
    }



}
