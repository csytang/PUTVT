package graph.pycharm.api;



import graph.query.graph.GraphQueryResult;

import java.util.List;
import java.util.Map;

public interface GraphDatabaseApi {

    GraphQueryResult execute(String query);

    GraphQueryResult execute(String query, Map<String, Object> statementParameters);

    GraphQueryResult executeBatch(List<String> query, Map<String, Object> statementParameters);
}
