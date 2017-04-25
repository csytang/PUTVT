package main.graph.visualization.api;

import java.util.HashMap;

public interface GraphPropertyContainer {

    HashMap<String, Object> getProperties();

    void setProperties (HashMap<String, Object> properties);
}
