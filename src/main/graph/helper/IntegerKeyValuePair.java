package main.graph.helper;

public class IntegerKeyValuePair {

    private String key;
    private Integer value;

    public IntegerKeyValuePair(String key, Integer value) {
       this.key=key;
       this.value=value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + ": " + value.toString();
    }
}
