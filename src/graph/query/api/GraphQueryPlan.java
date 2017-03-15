package graph.query.api;

import java.util.List;
import java.util.Map;

public interface GraphQueryPlan {

    String getOperatorType();

    Map<String, Object> getArguments();

    List<String> getIdentifiers();

    List<? extends GraphQueryPlan> children();

}
