package graph.pycharm.implementations;



import graph.enums.DataSourceType;
import graph.pycharm.api.DataSourceApi;

import java.util.HashMap;
import java.util.Map;

/**
 * Legacy DataSource. Versioning schema was not invented when this class was created.
 */
public final class DataSource implements DataSourceApi {

    public DataSourceType dataSourceType = DataSourceType.UNKNOWN;
    public String name = "unknown";
    public Map<String, String> configuration = new HashMap<>();

    /**
     * Default constructor for serialization.
     */
    public DataSource() {
    }

    public DataSource(DataSourceType dataSourceType, String name,
                      Map<String, String> configuration) {
        this.dataSourceType = dataSourceType;
        this.name = name;
        this.configuration = configuration;
    }

    @Override
    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    @Override
    public String getUUID() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, String> getConfiguration() {
        return configuration;
    }
}
