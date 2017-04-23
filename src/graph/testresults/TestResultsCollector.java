package graph.testresults;


import java.util.Hashtable;

/**
 * Collects all the test results for the visualization
 */
public class TestResultsCollector {
    private static TestResultsCollector instance;
    private Hashtable testResults = new Hashtable();
    private Hashtable testsOnly = new Hashtable();


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

    public Hashtable getTestsOnly() {
        return testsOnly;
    }

    public void setTestsOnly(Hashtable testsOnly) {
        this.testsOnly = testsOnly;
    }
}
