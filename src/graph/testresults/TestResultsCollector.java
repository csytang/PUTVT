package graph.testresults;


import java.util.Hashtable;

public class TestResultsCollector {
    private static TestResultsCollector instance;
    private Hashtable testResults = new Hashtable();


    public static TestResultsCollector getInstance(){
        if (instance == null){
            instance = new TestResultsCollector();
        }
        return instance;
    }


    public Hashtable getTestResults() {
        return testResults;
    }

    public void setTestResults(Hashtable testResults) {
        this.testResults = testResults;
    }
}
