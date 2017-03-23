package graph.query.api;

import java.util.List;
import java.util.Map;

public interface ResultsPlan {

    String getOperatorType();

    Map<String, Object> getArguments();

    List<String> getIdentifiers();

    List<? extends ResultsPlan> children();

}
