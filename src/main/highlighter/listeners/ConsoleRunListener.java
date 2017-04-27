package main.highlighter.listeners;

import com.intellij.execution.ExecutionListener;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import main.highlighter.highlighters.HighlightingMainController;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Listener for console executions on the IDE -> captures logs
 */
public class ConsoleRunListener implements ExecutionListener{
    private Boolean pytestCapture=false;
    private Boolean pythonCapture=false;

    private String executedLines = "";

    private Project project;

    public ConsoleRunListener(Project project) {
        this.project = project;
    }

    @Override
    public void processStartScheduled(String s, ExecutionEnvironment executionEnvironment) {
    }

    @Override
    public void processStarting(String s, @NotNull ExecutionEnvironment executionEnvironment) {
    }

    @Override
    public void processNotStarted(String s, @NotNull ExecutionEnvironment executionEnvironment) {
    }

    @Override
    public void processStarted(String s, @NotNull ExecutionEnvironment executionEnvironment, @NotNull ProcessHandler processHandler) {
        processHandler.addProcessListener(new ProcessAdapter() {
                @Override
                public void onTextAvailable(ProcessEvent event, Key outputType) {
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
                      //  pytestCapture = false;
                    }

                }
            });
    }

    @Override
    public void processTerminating(@NotNull RunProfile runProfile, @NotNull ProcessHandler processHandler) {
    }

    @Override
    public void processTerminated(@NotNull RunProfile runProfile, @NotNull ProcessHandler processHandler) {
        HighlightingMainController highlightingMainController = HighlightingMainController.getInstance(null);
        if (highlightingMainController.getUseConsoleLogs()){
               HighlightingMainController.getInstance(null).addToConsoleReading(executedLines);
        }
        executedLines="";
    }
}
