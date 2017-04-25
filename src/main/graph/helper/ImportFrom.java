package main.graph.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Imports from a specific file
 */
public class ImportFrom {
    private String name;
    private List<IntegerKeyValuePair> keyValuePairList = new ArrayList<>();// function, object imports from file
    public ImportFrom(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IntegerKeyValuePair> getKeyValuePairList() {
        return keyValuePairList;
    }

    public void setKeyValuePairList(List<IntegerKeyValuePair> keyValuePairList) {
        this.keyValuePairList = keyValuePairList;
    }

    public void addToList(IntegerKeyValuePair pair){
        this.keyValuePairList.add(pair);
    }
}
