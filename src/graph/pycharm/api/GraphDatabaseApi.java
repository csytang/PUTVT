package graph.pycharm.api;



import graph.query.graph.GraphCoverageResult;

import java.util.List;
import java.util.Map;

public interface GraphDatabaseApi {

    GraphCoverageResult execute(String query);

    GraphCoverageResult execute(String query, Map<String, Object> statementParameters);

    GraphCoverageResult executeBatch(List<String> query, Map<String, Object> statementParameters);
}
