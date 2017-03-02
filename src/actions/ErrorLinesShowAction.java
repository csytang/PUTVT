package actions;

import com.intellij.a.f.Message;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.ui.Messages;
import errors.ErrorManageFileControler;
import util.StringPytestUtil;

import java.util.ArrayList;
import java.util.List;

import static com.sun.tools.doclint.Entity.nu;

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
        StringPytestUtil stringPytestUtil = errorManageFileControler.getStringPytestUtilForFileName(editor);

        if (stringPytestUtil == null){
            return;
        }

        int count=0;
        List<String[]> runTimeLinesList = new ArrayList<>();
        for (Integer integer : stringPytestUtil.getLineNumber()){
            if (integer == lineN){
                runTimeLinesList.add(stringPytestUtil.getLinesDuringRuntime().get(count));
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
}
