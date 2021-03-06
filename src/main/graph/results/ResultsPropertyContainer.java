package main.graph.results;


import main.graph.visualization.api.GraphPropertyContainer;

import java.util.HashMap;

public class ResultsPropertyContainer implements GraphPropertyContainer {

    private HashMap<String, Object> properties;

    public ResultsPropertyContainer() {
        this.properties =  new HashMap<>();
    }

    public ResultsPropertyContainer(HashMap<String, Object> properties){
        this.properties = properties;
    }

    @Override
    public HashMap<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String,Object> properties){this.properties=properties;}
}
