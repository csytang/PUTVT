package main.highlighter.highlighters;

import com.intellij.openapi.editor.Editor;
import main.highlighter.pattern.PatternController;
import main.highlighter.util.ExternalLogsUtil;
import main.highlighter.util.HashtableCombineUtil;
import main.highlighter.util.StringPytestUtil;

import java.util.Hashtable;
//Highlighting controller
public class HighlightingMainController {

    private static HighlightingMainController instance = null;
    private ErrorManageFileControler errorManageFileControler;

    private String consolesReadings = " ";

    private Boolean useConsoleLogs = false;

    private Hashtable decodedLogs = new Hashtable();

    private Hashtable fileHashTable = new Hashtable();


    protected HighlightingMainController(String consolesReading) {
        consolesReadings = consolesReading;
    }

    public static HighlightingMainController getInstance(String consolesReading) {
        if (consolesReading != null){
            if (instance != null) {
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

    /**
     * Does the visualization
     * @param hashtable Hashtable with data for visualization from file logs, may be null - for each editor
     * @param editors all editors
     * @param editor current editor
     */
    public void finishVisualization(Hashtable hashtable, Editor[] editors, Editor editor){
        PatternController patternController = new PatternController();
        Hashtable newHashtable;
        errorManageFileControler = ErrorManageFileControler.getInstance(null);
        if (useConsoleLogs) {
            Hashtable decodedLogs = null;
            if (consolesReadings != null && !"".equals(consolesReadings)) { //console is used
                decodedLogs = patternController.patternDecode(consolesReadings);
            }
            if (decodedLogs != null) {
                this.decodedLogs=decodedLogs;
                if (hashtable == null || hashtable.isEmpty()) {
                    hashtable = fileHashTable;
                }
                else{
                    fileHashTable = hashtable;
                }
                    if (editor != null && !hashtable.isEmpty()) { //combines file with console logs for the current editor only
                        newHashtable = HashtableCombineUtil.combineHashTablesOneFileOnly(hashtable, decodedLogs, getEditorOpenedFileName(editor));
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

        if (ExternalLogsUtil.getInstane().getBeingUsed()){ //external logs used
            Hashtable decodedExternalLogs = null;
            String logs = ExternalLogsUtil.getInstane().getLogs();
            if (!"".equals(logs)){
                decodedExternalLogs = patternController.patternDecode(logs);
            }
            if (decodedExternalLogs != null){
                if (newHashtable != null){
                    if (editor != null) { //combines for the current editor only
                        newHashtable = HashtableCombineUtil.combineHashTablesOneFileOnly(newHashtable, decodedExternalLogs, getEditorOpenedFileName(editor));
                    }
                }
                else{
                    newHashtable = decodedExternalLogs;
                }
            }
        }


        for (Editor editor1 : editors){ //foreach editor recreate
            errorManageFileControler.decodeDTO(newHashtable,editor1);
        }
        errorManageFileControler.setErrorManageFileTable(newHashtable);
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

    public void clearLogs(){
        this.decodedLogs = new Hashtable();
        consolesReadings = "";
    }

    public void addToConsoleReading (String str){
        if (consolesReadings == null) {
            consolesReadings = "\n" + str;
        }
        else{
            consolesReadings = consolesReadings.concat("\n" + str);
        }
    }

}
