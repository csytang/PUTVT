package graph.query;



import graph.visualization.api.GraphPropertyContainer;

import java.util.HashMap;
import java.util.Map;

public class ResultsPropertyContainer implements GraphPropertyContainer {

    private final HashMap<String, Object> properties;

    public ResultsPropertyContainer() {
        this.properties =  new HashMap<>();
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }
}
