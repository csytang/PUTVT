package main.highlighter.util;

import java.util.List;

public class StringPytestUtil {

    private String fileName;
    private List<Integer> lineNumber;
    private List<String> typeOfError;
    private List<String> testNames;
    private List<String[]> linesDuringRuntime;


    public List<String[]> getLinesDuringRuntime() {
        return linesDuringRuntime;
    }

    public void setLinesDuringRuntime(List<String[]> linesDuringRuntime) {
        this.linesDuringRuntime = linesDuringRuntime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getTestNames() {
        return testNames;
    }

    public void setTestNames(List<String> testNames) {
        this.testNames = testNames;
    }

    public List<Integer> getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(List<Integer> lineNumber) {
        this.lineNumber = lineNumber;
    }

    public List<String> getTypeOfError() {
        return typeOfError;
    }

    public void setTypeOfError(List<String> typeOfError) {
        this.typeOfError = typeOfError;
    }
}
