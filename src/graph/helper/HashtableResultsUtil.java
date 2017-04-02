package graph.helper;


import graph.testresults.TestResultKeyValuePair;

import java.util.Enumeration;
import java.util.Hashtable;

public class HashtableResultsUtil {

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

}
