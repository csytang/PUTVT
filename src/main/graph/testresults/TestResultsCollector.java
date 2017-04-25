package main.graph.testresults;


import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Collects all the test results for the visualization
 */
public class TestResultsCollector {
    private static TestResultsCollector instance;
    private Hashtable<String, Hashtable> testResults = new Hashtable<>();
    private Hashtable<String, TestResultKeyValuePair> testsOnly = new Hashtable<>();


    public static TestResultsCollector getInstance(){
        if (instance == null){
            instance = new TestResultsCollector();
        }
        return instance;
    }

    public void setChangedToFalse(){
        Enumeration e = testResults.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            Hashtable<String, TestResultKeyValuePair> testResultKeyValuePairs = testResults.get(key);
            Enumeration f = testResultKeyValuePairs.keys();
            while (f.hasMoreElements()){
            String nameOftest = (String) f.nextElement();
            TestResultKeyValuePair testResultKeyValuePair = testResultKeyValuePairs.get(nameOftest);
            testResultKeyValuePair.setChanged(false);
            }
        }
    }


    public Hashtable<String, Hashtable> getTestResults() {
        return testResults;
    }

    public void setTestResults(Hashtable<String, Hashtable> testResults) {
        this.testResults = testResults;
    }

    public Hashtable<String, TestResultKeyValuePair> getTestsOnly() {
        return testsOnly;
    }

    public void setTestsOnly(Hashtable<String, TestResultKeyValuePair> testsOnly) {
        this.testsOnly = testsOnly;
    }
}
