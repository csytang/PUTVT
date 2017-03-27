package graph.enums;

public enum ResultPlanArgumentKeys {
    ESTIMATED_ROWS("EstimatedRows"),
    ROWS("Rows"),

    PLANNER_IMPL("planner-impl"),
    RUNTIME("runtime"),
    KEY_NAMES("KeyNames"),
    RUNTIME_IMPL("runtime-impl"),
    PLANNER("planner"),
    VERSION("version");

    private String key;

    ResultPlanArgumentKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


}
