package graph.enums;

import com.intellij.icons.AllIcons;
import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.Icon;

import static com.intellij.openapi.util.IconLoader.getIcon;

public final class GraphIcons {
    public static final class Database {
        //TODO PUT REAL ICONS
        public static final Icon UNKNOWN = getIcon("/graphdb/icons/database/unknown_16x16.png");
        public static final Icon NEO4J = getIcon("/graphdb/icons/database/neo4j_16x16.png");
        public static final Icon ORIENTDB = getIcon("/graphdb/icons/database/orientdb_16x16.png");
    }

    public static final class Language {
        public static final Icon CYPHER = getIcon("/graphdb/icons/language/cypher_16x16.png");
    }

    public static final class Window {
        public static final Icon DATABASE = getIcon("/graphdb/icons/window/database_12x16.png");
        public static final Icon GRAPH = getIcon("/graphdb/icons/window/graph_13x13.png");
    }

    public static final class Nodes {
        public static final Icon LABEL = AllIcons.Nodes.Class;
        public static final Icon RELATIONSHIP_TYPE = AllIcons.Vcs.Arrow_right;
        public static final Icon PROPERTY_KEY = AllIcons.Nodes.Property;
        public static final Icon VARIABLE = AllIcons.Nodes.Variable;
        public static final Icon FUNCTION = AllIcons.Nodes.Function;
        public static final Icon STORED_PROCEDURE = AllIcons.Nodes.Function;
        public static final Icon USER_FUNCTION = AllIcons.Nodes.Function;
    }

    private GraphIcons() {
    }
}
