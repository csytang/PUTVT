package main.graph.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Imports for a file
 */
public class ImportFileUtil{

    private String name;
    private List<ImportFrom> importFromList = new ArrayList<>(); //list of all the imports in this file

    public ImportFileUtil(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImportFrom> getImportFromList() {
        return importFromList;
    }

    public void setImportFromList(List<ImportFrom> importFromList) {
        this.importFromList = importFromList;
    }
}
