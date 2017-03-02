package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.ui.Messages;
import errors.ErrorManageFileControler;
import highlighters.HighlightingMainController;
import util.StringPytestUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cegin on 27.2.2017.
 */
public class ErrorLinesShowAction extends AnAction {

    final String logMessage = "LOG for Error:" + "\n" + "****************" + "\n";
    final String noLogsFound = "No logs for this line.";

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        LogicalPosition logicalPosition = caretModel.getLogicalPosition();
        int lineN = logicalPosition.line + 1;
        VisualPosition visualPosition = caretModel.getVisualPosition();

        ErrorManageFileControler errorManageFileControler = ErrorManageFileControler.getInstance(null);
        HighlightingMainController highlightingMainController = HighlightingMainController.getInstance(null);

        StringPytestUtil fileUtil = errorManageFileControler.getStringPytestUtilForFileName(editor);
        StringPytestUtil consoleUtil = highlightingMainController.getStringPytestUtilForFileName(editor);

        StringPytestUtil utilUsed = new StringPytestUtil();

        if (fileUtil == null && consoleUtil == null){
            return;
        }

        if (fileUtil != null && consoleUtil != null){
            utilUsed = combineStringUtils(fileUtil,consoleUtil);
        }
        else{
            if (consoleUtil != null){
                utilUsed=consoleUtil;
            }
            else{
                utilUsed=fileUtil;
            }
        }
        int count=0;
        List<String[]> runTimeLinesList = new ArrayList<>();
        for (Integer integer : utilUsed.getLineNumber()){
            if (integer == lineN){
                runTimeLinesList.add(utilUsed.getLinesDuringRuntime().get(count));
            }
            count++;
        }

        if (runTimeLinesList.size()==0){
            Messages.showInfoMessage(noLogsFound, noLogsFound);
            return;
        }

        String message="";

        for (String[] lines : runTimeLinesList){
            message+=logMessage;
            for (String line : lines){
                message+=line;
                message+='\n';
            }
            message+='\n';
        }

        Messages.showWarningDialog(message, "Error Logs for line no." + lineN + "");
    }

    private StringPytestUtil combineStringUtils(StringPytestUtil fileUtil, StringPytestUtil consoleUtil){
        StringPytestUtil combine = new StringPytestUtil();
        List<Integer> newLinesNumbers = new ArrayList<>();

        newLinesNumbers.addAll(fileUtil.getLineNumber());
        newLinesNumbers.addAll(consoleUtil.getLineNumber());

        List<String[]> newLinesDuringRuntime = new ArrayList<>();

        newLinesDuringRuntime.addAll(fileUtil.getLinesDuringRuntime());
        newLinesDuringRuntime.addAll(consoleUtil.getLinesDuringRuntime());

        List<String> newTypeOfErrors = new ArrayList<>();

        newTypeOfErrors.addAll(fileUtil.getTypeOfError());
        newTypeOfErrors.addAll(consoleUtil.getTypeOfError());

        List<String> newTestNames = new ArrayList<>();

        newTestNames.addAll(fileUtil.getTestNames());
        if (consoleUtil.getTestNames() != null) {
            newTestNames.addAll(consoleUtil.getTestNames());
        }

        combine.setFileName(fileUtil.getFileName());
        combine.setTypeOfError(newTypeOfErrors);
        combine.setLinesDuringRuntime(newLinesDuringRuntime);
        combine.setLineNumber(newLinesNumbers);
        combine.setTestNames(newTestNames);

        return combine;
    }
}
