package main.graph.helper;


import main.graph.testresults.TestResultKeyValuePair;
import main.graph.visualization.api.GraphNode;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class HashtableResultsUtil {

    private List<GraphNode> nodes = new ArrayList<>();
    private static HashtableResultsUtil instance;
    private Boolean onlyCoveraged = false;


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

    public List<GraphNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<GraphNode> nodes) {

        this.nodes = nodes;
    }

    public Boolean getOnlyCoveraged() {
        return onlyCoveraged;
    }

    public void setOnlyCoveraged(Boolean onlyCoveraged) {
        this.onlyCoveraged = onlyCoveraged;
    }
}
