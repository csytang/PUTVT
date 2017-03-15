package graph.query;

import graph.pycharm.api.GraphDatabaseApi;
import graph.query.graph.GraphQueryResult;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Communicates with Neo4j 3.0+ database using Bolt driver.
 */
public class Neo4jBoltDatabase implements GraphDatabaseApi {


    public Neo4jBoltDatabase() {

    }

    @Override
    public GraphQueryResult execute(String query) {
        return execute(query, Collections.emptyMap());
    }

    @Override
    public GraphQueryResult execute(String query, Map<String, Object> statementParameters) {
        return new Neo4jBoltQueryResult(10L);
    }

    @Override
    public GraphQueryResult executeBatch(List<String> queries, Map<String, Object> statementParameters) {
       return new Neo4jBoltQueryResult(10L);
    }
}
