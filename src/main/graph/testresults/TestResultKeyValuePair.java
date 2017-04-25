package main.graph.testresults;

public class TestResultKeyValuePair {
    private String testName;
    private int testResult;
    private boolean isChanged = false;

    public TestResultKeyValuePair(String testName, Integer testResult){
        this.testName=testName;
        this.testResult=testResult;
    }

    public TestResultKeyValuePair(TestResultKeyValuePair copy){
        this.testName=copy.getTestName();
        this.testResult=copy.getTestResult();
        this.isChanged=copy.getChanged();
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getTestResult() {
        return testResult;
    }

    public void setTestResult(Integer testResult) {
        this.testResult = testResult;
    }

    public Boolean getChanged() {
        return isChanged;
    }

    public void setChanged(Boolean changed) {
        isChanged = changed;
    }

}
