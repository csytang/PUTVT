package listeners;

import com.intellij.execution.ExecutionListener;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import highlighters.HighlightingMainController;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cegin on 27.2.2017.
 */
public class ConsoleRunListener implements ExecutionListener{
    private Boolean pytestCapture=false;
    private Boolean pythonCapture=false;

    private String executedLines = "";

    private Project project;

    private ExecutionEnvironment executionEnvironment;
    public ConsoleRunListener(Project project) {
        this.project = project;
    }

    @Override
    public void processStartScheduled(String s, ExecutionEnvironment executionEnvironment) {
        System.out.println(s);
    }

    @Override
    public void processStarting(String s, @NotNull ExecutionEnvironment executionEnvironment) {
    }

    @Override
    public void processNotStarted(String s, @NotNull ExecutionEnvironment executionEnvironment) {
    }

    @Override
    public void processStarted(String s, @NotNull ExecutionEnvironment executionEnvironment, @NotNull ProcessHandler processHandler) {
        /*if (!processHandler.isStartNotified())
            processHandler.startNotify();*/
       // processHandler.notifyTextAvailable("hey", new Key("Jude"))
        processHandler.addProcessListener(new ProcessAdapter() {
                @Override
                public void onTextAvailable(ProcessEvent event, Key outputType) {
                    if (outputType.toString().equals("Test"))
                        return;
                 //   processHandler.notifyTextAvailable("Execute", new Key("Test"
                    String line = event.getText();

                    Pattern p = Pattern.compile("___________ (.*) ___________");
                    Matcher m = p.matcher(line);

                    if (pythonCapture || pytestCapture){
                        executedLines += line;
                    }

                    if (m.find()){
                        pytestCapture = true;
                        executedLines += line;
                    }

                    if (line.contains("Traceback")){
                        pythonCapture = true;
                        executedLines += line;
                    }

                     p = Pattern.compile("(.*)Error:(.*)");
                     m = p.matcher(line);

                    if (m.find()){
                        executedLines += "\n";
                        pythonCapture = false;
                    }

                    p = Pattern.compile(".*.py:\\d*:.*");
                    m = p.matcher(line);

                    if (m.find()){
                        executedLines += "\n";
                        pytestCapture = false;
                    }

                }
            });
        this.executionEnvironment=executionEnvironment;
    }

    @Override
    public void processTerminating(@NotNull RunProfile runProfile, @NotNull ProcessHandler processHandler) {
       /* PythonProcessHandler processHandler1 = (PythonProcessHandler) processHandler;
        try {
            runProfile.getState(executionEnvironment.getExecutor(),executionEnvironment);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void processTerminated(@NotNull RunProfile runProfile, @NotNull ProcessHandler processHandler) {
        /*PythonProcessHandler processHandler1 = (PythonProcessHandler) processHandler;
        try {
            runProfile.getState(executionEnvironment.getExecutor(),executionEnvironment);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        HighlightingMainController highlightingMainController = HighlightingMainController.getInstance(null);
        if (highlightingMainController.getUseConsoleLogs()){
                HighlightingMainController.getInstance(executedLines);
        }
        executedLines="";
    }
}
