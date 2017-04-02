package graph.testresults;

import com.intellij.execution.testframework.AbstractTestProxy;
import com.intellij.execution.testframework.TestStatusListener;
import org.jetbrains.annotations.Nullable;

import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

public class TestStatusListenerImpl extends TestStatusListener{
    @Override
    public void testSuiteFinished(@Nullable AbstractTestProxy abstractTestProxy) {
        List<? extends AbstractTestProxy> abstractTestProxyList = abstractTestProxy.getAllTests();
        Hashtable testResults = TestResultsCollector.getInstance().getTestResults();
        for (AbstractTestProxy proxy: abstractTestProxyList){
            if (proxy.getLocationUrl()!=null ) {
                if (testResults.get(getTestFileName(proxy.getLocationUrl())) == null) {
                    if (!proxy.getName().contains("root") && (proxy.getName() != null)) {
                        Hashtable testResultKeyValuePairs = new Hashtable();
                        testResultKeyValuePairs.put(proxy.getName(), new TestResultKeyValuePair(proxy.getName(),
                                proxy.getMagnitude()));
                        testResults.put(getTestFileName(proxy.getLocationUrl()), testResultKeyValuePairs);
                    }
                } else {
                    Hashtable testResultKeyValuePairs = (Hashtable) testResults.get(getTestFileName(proxy.getLocationUrl()));
                    TestResultKeyValuePair testResultKeyValuePair = (TestResultKeyValuePair) testResultKeyValuePairs.get(proxy.getName());
                    if (testResultKeyValuePair == null) {
                        testResultKeyValuePairs.put(proxy.getName(), new TestResultKeyValuePair(proxy.getName(), proxy.getMagnitude()));
                    } else {
                        if (testResultKeyValuePair.getTestResult() != proxy.getMagnitude()) {
                            testResultKeyValuePair.setTestResult(proxy.getMagnitude());
                            testResultKeyValuePair.setChanged(true);
                        }
                    }
                }
            }
        }
        TestResultsCollector.getInstance().setTestResults(testResults);
    }

    private String getTestFileName(String url){
        String[] arr = url.split("/");
        String[] arr2 = arr[arr.length-1].split(Pattern.quote("\\"));
        String[] arr1 = arr2[arr2.length-1].split(":");
        return arr1[0];
    }
}
