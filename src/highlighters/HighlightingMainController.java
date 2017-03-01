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

        if (useConsoleLogs) {
            Hashtable decodedLogs = null;
            if (consolesReadings.size() != 0 && consolesReadings.get(consolesReadings.size() - 1) != null) {
                decodedLogs = patternController.patternDecode(consolesReadings.get(consolesReadings.size() - 1));
            }
            if (decodedLogs != null) {
                HashtableCombineUtil hashtableCombineUtil = new HashtableCombineUtil();
                hashtable = hashtableCombineUtil.combineHashTables(hashtable, decodedLogs, patternController.getFileNamesForConsoleLog());
            }
        }


        //TODO Check for file load
        errorManageFileControler = ErrorManageFileControler.getInstance(hashtable);
        errorManageFileControler.decodeDTO(hashtable,editor);
    }

    public Boolean getUseConsoleLogs() {
        return useConsoleLogs;
    }

    public void setUseConsoleLogs(Boolean useConsoleLogs) {
        this.useConsoleLogs = useConsoleLogs;
    }
}
