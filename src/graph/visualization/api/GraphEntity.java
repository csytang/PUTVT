package graph.visualization.api;

import java.util.List;

public interface GraphEntity {

    String getId();

    GraphPropertyContainer getPropertyContainer();

    List<String> getTypes();

    String getTypesName();

    boolean isTypesSingle();

    String getRepresentation();
}
