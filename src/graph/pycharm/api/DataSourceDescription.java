package graph.pycharm.api;


import graph.enums.DataSourceType;
import graph.enums.GraphIcons;

import javax.swing.*;

public interface DataSourceDescription {

    DataSourceType getType();

    String geTypeName();

    Icon getIcon();

    String getDefaultFileExtension();

    DataSourceDescription NEO4J_BOLT = new DataSourceDescription() {
        @Override
        public DataSourceType getType() {
            return DataSourceType.NEO4J_BOLT;
        }

        @Override
        public Icon getIcon() {
            return GraphIcons.Database.NEO4J;
        }

        @Override
        public String getDefaultFileExtension() {
            return "cypher";
        }

        @Override
        public String geTypeName() {
            return "Neo4j - Bolt";
        }
    };
}
