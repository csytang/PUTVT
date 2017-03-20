package graph.pycharm.services;


import graph.pycharm.api.DataSourceApi;
import graph.pycharm.api.GraphDatabaseApi;
import graph.query.Neo4jBoltDatabase;

public class DatabaseManagerServiceImpl implements DatabaseManagerService {

    public DatabaseManagerServiceImpl() {
    }

    public GraphDatabaseApi getDatabaseFor(DataSourceApi dataSource) {
        switch (dataSource.getDataSourceType()) {
            case NEO4J_BOLT:
                return new Neo4jBoltDatabase();
            default:
                throw new RuntimeException();
        }
    }
}
