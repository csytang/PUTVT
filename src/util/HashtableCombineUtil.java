package util;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by Cegin on 28.2.2017.
 */
public class HashtableCombineUtil {

    public HashtableCombineUtil(){

    }

    public Hashtable combineHashTables(Hashtable pytestTable,Hashtable pythonTable, List<String> fileNames) {
        for (String fileName : fileNames){
            if (pytestTable.get(fileName) != null) {
                StringPytestUtil stringPytestUtil = (StringPytestUtil) pytestTable.get(fileName);
                StringPytestUtil stringUtilpythonTable = (StringPytestUtil) pythonTable.get(fileName);

                List<Integer>lineNumbers = stringPytestUtil.getLineNumber();
                lineNumbers.addAll(stringUtilpythonTable.getLineNumber());
                stringPytestUtil.setLineNumber(lineNumbers);

                List<String[]> linesDuringRuntime = stringPytestUtil.getLinesDuringRuntime();
                linesDuringRuntime.addAll(stringUtilpythonTable.getLinesDuringRuntime());
                stringPytestUtil.setLinesDuringRuntime(linesDuringRuntime);

                List<String> typeOfErrors =  stringPytestUtil.getTypeOfError();
                typeOfErrors.addAll(stringUtilpythonTable.getTypeOfError());
                stringPytestUtil.setTypeOfError(typeOfErrors);

                pytestTable.remove(fileName);
                pytestTable.put(fileName,stringPytestUtil);
            }
            else{
                StringPytestUtil stringUtilpythonTable = (StringPytestUtil) pythonTable.get(fileName);
                pytestTable.put(stringUtilpythonTable.getFileName(), stringUtilpythonTable);
            }
        }
        return pytestTable;
    }
}
