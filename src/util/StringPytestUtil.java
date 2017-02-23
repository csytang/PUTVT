package util;

import java.util.List;

/**
 * Created by Cegin on 18.2.2017.
 */
public class StringPytestUtil {

    private String testLastLine;
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

    public String getTestLastLine() {
        return testLastLine;
    }

    public void setTestLastLine(String testLastLine) {
        this.testLastLine = testLastLine;
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
