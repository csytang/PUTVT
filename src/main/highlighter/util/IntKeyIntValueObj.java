package main.highlighter.util;


public class IntKeyIntValueObj {
    private Integer key;
    private Integer value;

    public IntKeyIntValueObj(Integer key, Integer value){
        this.key=key;
        this.value=value;
    }

    public IntKeyIntValueObj(){}

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
