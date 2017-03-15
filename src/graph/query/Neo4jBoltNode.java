package graph.query;



import com.google.common.collect.Iterables;
import graph.visualization.api.GraphNode;
import graph.visualization.api.GraphPropertyContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Neo4jBoltNode implements GraphNode {

    private final String id;
    private final Neo4jBoltPropertyContainer propertyContainer;
    private final List<String> types;

    public Neo4jBoltNode(String id) {
        this.id = id;
        this.types = new ArrayList<>();
        this.propertyContainer = new Neo4jBoltPropertyContainer();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<String> getTypes() {
        return types;
    }

    @Override
    public String getTypesName() {
        return "labels";
    }

    @Override
    public boolean isTypesSingle() {
        return false;
    }

    @Override
    public GraphPropertyContainer getPropertyContainer() {
        return propertyContainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Neo4jBoltNode that = (Neo4jBoltNode) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
