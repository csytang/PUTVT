package highlighter.highlighters;

import com.intellij.openapi.editor.Editor;
import highlighter.errors.ErrorManageFileControler;
import highlighter.pattern.PatternController;
import highlighter.util.ExternalLogsUtil;
import highlighter.util.HashtableCombineUtil;
import highlighter.util.StringPytestUtil;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class HighlightingMainController {

    private static HighlightingMainController instance = null;
    private ErrorManageFileControler errorManageFileControler;

    private static List<String> consolesReadings;

    private Boolean useConsoleLogs = false;

    private Hashtable decodedLogs = new Hashtable();


    protected HighlightingMainController(String consolesReading) {
        this.consolesReadings = new ArrayList<>();
        consolesReadings.add(consolesReading);
    }

    public static HighlightingMainController getInstance(String consolesReading) {
        if (consolesReading != null){
            if (instance != null) {
                consolesReadings.add(consolesReading);
            }
            else{
                instance = new HighlightingMainController(consolesReading);
            }
        }
        else{
            if (instance == null){
                instance = new HighlightingMainController(null);
            }
        }
        return instance;
    }

    public void finishVisualization(Hashtable hashtable, Editor editor, Editor[] editors){
        PatternController patternController = new PatternController();
        Hashtable newHashtable;
        errorManageFileControler = ErrorManageFileControler.getInstance(hashtable);
        if (useConsoleLogs) {
            Hashtable decodedLogs = null;
            if (consolesReadings.size() != 0 && consolesReadings.get(consolesReadings.size() - 1) != null) {
                decodedLogs = patternController.patternDecode(consolesReadings.get(consolesReadings.size() - 1));
            }
            if (decodedLogs != null) {
                this.decodedLogs=decodedLogs;
                if (hashtable != null) {
                    newHashtable = HashtableCombineUtil.combineHashTablesForConsoleAndFile(hashtable, decodedLogs, getEditorOpenedFileName(editor));
                }
                else{
                    newHashtable=decodedLogs;
                }
            }
            else{
                newHashtable=hashtable;
            }
        }
        else{
            newHashtable=hashtable;
        }

        if (ExternalLogsUtil.getInstane().getBeingUsed()){
            Hashtable decodedExternalLogs = null;
            String logs = ExternalLogsUtil.getInstane().getLogs();
            if (!"".equals(logs)){
                decodedExternalLogs = patternController.patternDecode(logs);
            }
            if (decodedExternalLogs != null){
                if (newHashtable != null){
                    newHashtable = HashtableCombineUtil.combineHashTablesForConsoleAndFile(newHashtable, decodedExternalLogs, getEditorOpenedFileName(editor));
                }
                else{
                    newHashtable = decodedExternalLogs;
                }
            }
        }

        errorManageFileControler.decodeDTO(newHashtable,editor);
        for (Editor editor1 : editors){
            errorManageFileControler.decodeDTO(newHashtable,editor1);
        }
    }

    public Boolean getUseConsoleLogs() {
        return useConsoleLogs;
    }

    public void setUseConsoleLogs(Boolean useConsoleLogs) {
        this.useConsoleLogs = useConsoleLogs;
    }

    public StringPytestUtil getStringPytestUtilForFileName(Editor editor){
        String editorFileName = getEditorOpenedFileName(editor);
        return (StringPytestUtil) decodedLogs.get(editorFileName);
    }

    private String getEditorOpenedFileName(Editor editor){
        String tmp = editor.getDocument().toString();
        tmp = tmp.replace("]", "").replace('/', ' ');
        String arr[] = tmp.split(" ");
        return arr[arr.length - 1];
    }
}
