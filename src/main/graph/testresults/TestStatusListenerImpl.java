package main.graph.testresults;

import com.intellij.execution.testframework.AbstractTestProxy;
import com.intellij.execution.testframework.TestStatusListener;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import main.graph.pycharm.services.ResultExecutionService;
import org.jetbrains.annotations.Nullable;

import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Listener to always get new test results
 */
public class TestStatusListenerImpl extends TestStatusListener{
    @Override
    public void testSuiteFinished(@Nullable AbstractTestProxy abstractTestProxy) {
        Editor ed[] = EditorFactory.getInstance().getAllEditors();
        Project project = ed[0].getProject();
        List<? extends AbstractTestProxy> abstractTestProxyList = abstractTestProxy.getAllTests();
        Hashtable<String, Hashtable> testResults = TestResultsCollector.getInstance().getTestResults();
        Hashtable<String, TestResultKeyValuePair> testsOnly = new Hashtable<> ();
        if (abstractTestProxyList.size() > 1){
            TestResultsCollector.getInstance().setChangedToFalse();
        }
        for (AbstractTestProxy proxy: abstractTestProxyList){
            int result = 0;
            if (proxy.getMagnitude() == 1){ //only 1 is successful, all the other values signalize failed test
                result=1;
            }
            if (proxy.getLocationUrl()!=null ) {
                if (testResults.get(getTestFileName(proxy.getLocationUrl())) == null) {
                    if (!proxy.getName().contains("root") && (proxy.getName() != null)) { //root or null proxy
                        Hashtable testResultKeyValuePairs = new Hashtable();
                        TestResultKeyValuePair testResultKeyValuePair = new TestResultKeyValuePair(proxy.getName(), result);
                        testResultKeyValuePairs.put(proxy.getName(), testResultKeyValuePair);
                        testsOnly.put(proxy.getName(), testResultKeyValuePair);
                        testResults.put(getTestFileName(proxy.getLocationUrl()), testResultKeyValuePairs);
                    }
                } else {
                    Hashtable<String, TestResultKeyValuePair> testResultKeyValuePairs = testResults.get(getTestFileName(proxy.getLocationUrl()));
                    TestResultKeyValuePair testResultKeyValuePair = testResultKeyValuePairs.get(proxy.getName());
                    if (testResultKeyValuePair == null) {
                        testResultKeyValuePair = new TestResultKeyValuePair(proxy.getName(), result);
                        testResultKeyValuePairs.put(proxy.getName(), testResultKeyValuePair);
                        testsOnly.put(proxy.getName(), testResultKeyValuePair);
                    } else {
                        if (testResultKeyValuePair.getTestResult() != result) {
                            testResultKeyValuePair.setTestResult(result);
                            testResultKeyValuePair.setChanged(true);
                        }
                    }
                }
            }
        }
        TestResultsCollector.getInstance().setTestsOnly(testsOnly);
        TestResultsCollector.getInstance().setTestResults(testResults);
        MessageBus messageBus = project.getMessageBus();
        ResultExecutionService resultExecutionService = new ResultExecutionService(messageBus,project);
        resultExecutionService.executeResults();
    }

    private String getTestFileName(String url){
        String[] arr = url.split("/");
        String[] arr2 = arr[arr.length-1].split(Pattern.quote("\\"));
        String[] arr1 = arr2[arr2.length-1].split(":");
        return arr1[0];
    }
}
