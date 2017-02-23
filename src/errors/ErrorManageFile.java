package errors;

import java.util.List;

/**
 * Created by Cegin on 18.2.2017.
 */
public class ErrorManageFile {
    private List<Integer> numberOfLines;
    private List<String> ErrorReason;
    private String fileName;

    public ErrorManageFile(){

    }

    public List<Integer> getNumberOfLines() {
        return numberOfLines;
    }

    public void setNumberOfLines(List<Integer> numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

    public List<String> getErrorReason() {
        return ErrorReason;
    }

    public void setErrorReason(List<String> errorReason) {
        ErrorReason = errorReason;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
