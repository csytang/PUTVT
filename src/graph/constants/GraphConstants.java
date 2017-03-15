package graph.constants;

public final class GraphConstants {

    public static final boolean IS_DEVELOPMENT = System.getProperty("graphDatabaseSupportDevelopment") != null;

    public static final String BOUND_DATA_SOURCE_PREFIX = "out";
    public static final String PLUGIN_ID = "org.cegin.plugin.PUTVT";

    public static class ToolWindow {
        public static final String CONSOLE_TOOL_WINDOW = "Graph Database Console";
    }

    public static class Actions {
        public static final String CONSOLE_ACTIONS = "GraphDependencyConsoleViewActions";
    }

    private GraphConstants() {
    }
}
