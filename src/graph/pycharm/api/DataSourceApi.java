package graph.pycharm.api;


import graph.enums.DataSourceType;

import java.util.Map;

public interface DataSourceApi {

    String getUUID();

    String getName();

    DataSourceType getDataSourceType();

    Map<String, String> getConfiguration();

    default DataSourceDescription getDescription() {
        switch (getDataSourceType()) {
            case NEO4J_BOLT:
                return DataSourceDescription.NEO4J_BOLT;
            default:
                throw new IllegalStateException("Unknown data source type encountered: " + getDataSourceType());
        }
    }
}
