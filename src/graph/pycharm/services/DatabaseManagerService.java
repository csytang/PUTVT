package graph.pycharm.services;


import graph.pycharm.api.DataSourceApi;
import graph.pycharm.api.GraphDatabaseApi;

public interface DatabaseManagerService {

    GraphDatabaseApi getDatabaseFor(DataSourceApi dataSource);
}
