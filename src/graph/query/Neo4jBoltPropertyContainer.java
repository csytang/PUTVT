package graph.query;



import graph.visualization.api.GraphPropertyContainer;

import java.util.HashMap;
import java.util.Map;

public class Neo4jBoltPropertyContainer implements GraphPropertyContainer {

    private final HashMap<String, Object> properties;

    public Neo4jBoltPropertyContainer() {
        this.properties =  new HashMap<>();
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }
}
