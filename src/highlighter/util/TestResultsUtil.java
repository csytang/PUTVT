package highlighter.util;


import graph.testresults.TestResultKeyValuePair;
import graph.testresults.TestResultsCollector;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class TestResultsUtil {

    private static TestResultsUtil instance;
    private List<String> results = new ArrayList<>();

    public static TestResultsUtil getInstance() {
        if (instance == null) {
            instance = new TestResultsUtil();
        }
        return instance;
    }


    public List<String> getResults() {
        Hashtable testResults = TestResultsCollector.getInstance().getTestsOnly();
        List<String> passingTests = new ArrayList<>();
        for (String result : results) {
            if (testResults.get(result) != null){
                TestResultKeyValuePair testResultKeyValuePair = (TestResultKeyValuePair) testResults.get(result);
                if (testResultKeyValuePair.getTestResult()==1){
                    passingTests.add(result);
                }
            }
        }
        results.removeAll(passingTests);
        return passingTests;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }
}
