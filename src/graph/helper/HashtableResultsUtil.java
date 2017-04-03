package graph.helper;


import graph.testresults.TestResultKeyValuePair;
import graph.visualization.api.GraphNode;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class HashtableResultsUtil {

    private static List<GraphNode> nodes = new ArrayList<>();
    private static HashtableResultsUtil instance;


    public static HashtableResultsUtil getInstance(){
        if (instance==null){
            instance=new HashtableResultsUtil();
        }
        return instance;
    }

    public static Hashtable copyHashtableTestResults(Hashtable testResults){
        Enumeration e = testResults.keys();
        Hashtable newHashtable = new Hashtable();
        while(e.hasMoreElements()){
            String key = (String) e.nextElement();
            Hashtable testKeyValuePairs = (Hashtable) testResults.get(key);
            Hashtable newHashtableWithPairs = new Hashtable();
            Enumeration enumerationOfTests = testKeyValuePairs.keys();
            while(enumerationOfTests.hasMoreElements()){
                String anotherKey = (String) enumerationOfTests.nextElement();
                TestResultKeyValuePair pair = (TestResultKeyValuePair) testKeyValuePairs.get(anotherKey);
                TestResultKeyValuePair copy = new TestResultKeyValuePair(pair);
                newHashtableWithPairs.put(anotherKey,copy);
            }
            newHashtable.put(key,newHashtableWithPairs);
        }
        return newHashtable;
    }

    public static List<GraphNode> getNodes() {
        return nodes;
    }

    public static void setNodes(List<GraphNode> nodes) {
        HashtableResultsUtil.nodes = nodes;
    }

    //TODO MAYBE NO USE FOR THIS, TRY RUNNING WITHOUT THIS

    /*public static Hashtable compareAndReturnNewHashtable(Hashtable oldTestResults, Hashtable newTestResults){
        Enumeration e = newTestResults.keys();
        while(e.hasMoreElements()){
            String key = (String) e.nextElement();
            Hashtable testResults = (Hashtable) oldTestResults.get(key);
            if (testResults != null){
                Hashtable newTestR = (Hashtable) newTestResults.get(key);
                Enumeration eT = newTestR.keys();
                while(eT.hasMoreElements()){
                    String keyTest = (String) eT.nextElement();
                    TestResultKeyValuePair testResultKeyValuePair = (TestResultKeyValuePair) testResults.get(keyTest);
                    if (testResultKeyValuePair != null){
                        TestResultKeyValuePair newTestResultKeyValuePair1 = (TestResultKeyValuePair) newTestR.get(keyTest);
                        if (testResultKeyValuePair.getTestResult() != newTestResultKeyValuePair1.getTestResult()){
                            testResultKeyValuePair.setTestResult(newTestResultKeyValuePair1.getTestResult());
                            testResultKeyValuePair.setChanged(true);
                        }
                    }
                    else{
                        testResultKeyValuePair = (TestResultKeyValuePair) newTestResults.get(keyTest);
                        testResults.put(keyTest,testResultKeyValuePair);
                    }
                }
            }
            else{
                testResults = (Hashtable) newTestResults.get(key);
                oldTestResults.put(key,testResults);
            }
        }
        return oldTestResults;
    }*/

}
