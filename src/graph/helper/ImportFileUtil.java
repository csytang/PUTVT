package graph.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cegin on 24.3.2017.
 */
public class ImportFileUtil{

    private String name;
    private List<ImportFrom> importFromList = new ArrayList<>();

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
