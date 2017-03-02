package util;

import java.util.*;

/**
 * Created by Cegin on 28.2.2017.
 */
public class HashtableCombineUtil {

    public HashtableCombineUtil(){

    }

    public Hashtable combineHashTables(Hashtable pytestTable,Hashtable pythonTable, List<String> fileNames) {
        Hashtable newHashtable = pytestTable;
        for (String fileName : fileNames){
            if (newHashtable.get(fileName) != null) {
                StringPytestUtil stringPytestUtil = (StringPytestUtil) newHashtable.get(fileName);
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

                newHashtable.remove(fileName);
                newHashtable.put(fileName,stringPytestUtil);
            }
            else{
                StringPytestUtil stringUtilpythonTable = (StringPytestUtil) pythonTable.get(fileName);
                newHashtable.put(stringUtilpythonTable.getFileName(), stringUtilpythonTable);
            }
        }
        return newHashtable;
    }

    public Hashtable combineHashTablesForConsoleAndFile(Hashtable file, Hashtable console, String fileName){
        Hashtable newHashtable = new Hashtable();
        Enumeration e = file.keys();
        //TODO keep file entries for other files
            if (file.get(fileName) != null) {
                StringPytestUtil newStringPytestUtil = new StringPytestUtil();
                StringPytestUtil stringPytestUtil = (StringPytestUtil) file.get(fileName);

                StringPytestUtil stringUtilpythonTable = (StringPytestUtil) console.get(fileName);

                if (stringUtilpythonTable == null){
                    return file;
                }

                List<Integer> lineNumbers = stringPytestUtil.getLineNumber();
                List<Integer> newLineNumbers = new ArrayList<>();
                newLineNumbers.addAll(lineNumbers);
                lineNumbers = stringUtilpythonTable.getLineNumber();
                newLineNumbers.addAll(lineNumbers);
                newStringPytestUtil.setLineNumber(newLineNumbers);

                List<String[]> linesDuringRuntime = stringPytestUtil.getLinesDuringRuntime();
                List<String[]> newlinesDuringRuntime = new ArrayList<>();
                newlinesDuringRuntime.addAll(linesDuringRuntime);
                linesDuringRuntime = stringUtilpythonTable.getLinesDuringRuntime();
                newlinesDuringRuntime.addAll(linesDuringRuntime);
                newStringPytestUtil.setLinesDuringRuntime(newlinesDuringRuntime);

                List<String> typeOfErrors = stringPytestUtil.getTypeOfError();
                List<String> newTypeOfErrors = new ArrayList<>();
                newTypeOfErrors.addAll(typeOfErrors);
                typeOfErrors = stringUtilpythonTable.getTypeOfError();
                newTypeOfErrors.addAll(typeOfErrors);
                newStringPytestUtil.setTypeOfError(newTypeOfErrors);

                newStringPytestUtil.setFileName(stringPytestUtil.getFileName());

                newHashtable.remove(fileName);
                newHashtable.put(fileName, newStringPytestUtil);
            } else {
                //TODO FIX? DELETE? WILL BE USED WHEN CONSOLE ONLY
                StringPytestUtil stringUtilpythonTable = (StringPytestUtil) console.get(fileName);
                newHashtable.put(stringUtilpythonTable.getFileName(), stringUtilpythonTable);
            }
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            if (key.equals(fileName) ){
                continue;
            }
            StringPytestUtil stringPytestUtil = (StringPytestUtil) file.get(key);
            newHashtable.put(key,stringPytestUtil);
        }

        return newHashtable;
    }
}
