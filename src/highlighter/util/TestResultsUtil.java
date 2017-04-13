package highlighter.util;


import java.util.ArrayList;
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
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }
}
