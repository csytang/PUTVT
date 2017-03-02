package highlighters;

import com.intellij.openapi.editor.Editor;
import errors.ErrorManageFileControler;
import pattern.PatternController;
import util.HashtableCombineUtil;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Cegin on 28.2.2017.
 */
public class HighlightingMainController {

    private static HighlightingMainController instance = null;
    private ErrorManageFileControler errorManageFileControler;

    private static List<String> consolesReadings;

    private Boolean useConsoleLogs = false;


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

    public void finishVisualization(Hashtable hashtable, Editor editor){
        PatternController patternController = new PatternController();
        Hashtable newHashtable = new Hashtable();
        errorManageFileControler = ErrorManageFileControler.getInstance(hashtable);
        if (useConsoleLogs) {
            Hashtable decodedLogs = null;
            if (consolesReadings.size() != 0 && consolesReadings.get(consolesReadings.size() - 1) != null) {
                decodedLogs = patternController.patternDecode(consolesReadings.get(consolesReadings.size() - 1));
            }
            if (decodedLogs != null) {
                HashtableCombineUtil hashtableCombineUtil = new HashtableCombineUtil();
                newHashtable = hashtableCombineUtil.combineHashTablesForConsoleAndFile(hashtable, decodedLogs, getEditorOpenedFileName(editor));
            }
        }


        //TODO Check for file load

        errorManageFileControler.decodeDTO(newHashtable,editor);
    }

    public Boolean getUseConsoleLogs() {
        return useConsoleLogs;
    }

    public void setUseConsoleLogs(Boolean useConsoleLogs) {
        this.useConsoleLogs = useConsoleLogs;
    }

    private String getEditorOpenedFileName(Editor editor){
        String tmp = editor.getDocument().toString();
        tmp = tmp.replace("]", "").replace('/', ' ');
        String arr[] = tmp.split(" ");
        return arr[arr.length - 1];
    }
}
