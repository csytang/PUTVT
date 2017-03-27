package graph.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cegin on 24.3.2017.
 */
public class ImportFrom {
    private String name;
    private List<IntegerKeyValuePair> keyValuePairList = new ArrayList<>();
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
