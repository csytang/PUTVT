package main.highlighter.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class HashtableCombineUtil {
    /**
     * Combines two hashtables and creates a new one with unique objects in it, not just references to already existing ones
     * @param file
     * @param console
     * @param fileNames
     * @return
     */
    public static Hashtable combineHashTables(Hashtable file,Hashtable console, List<String> fileNames) {
        Hashtable newHashtable = file;
        for (String fileName : fileNames){
            if (newHashtable.get(fileName) != null) {
                StringPytestUtil stringPytestUtil = (StringPytestUtil) newHashtable.get(fileName);
                StringPytestUtil stringUtilpythonTable = (StringPytestUtil) console.get(fileName);

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
                StringPytestUtil stringUtilpythonTable = (StringPytestUtil) console.get(fileName);
                newHashtable.put(stringUtilpythonTable.getFileName(), stringUtilpythonTable);
            }
        }
        return newHashtable;
    }

    public static Hashtable combineHashTablesOneFileOnly(Hashtable file, Hashtable console, String fileName){
        Hashtable newHashtable = new Hashtable();
        Enumeration e = file.keys();
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
                StringPytestUtil stringUtilpythonTable = (StringPytestUtil) console.get(fileName);
                if (stringUtilpythonTable != null) {
                    newHashtable.put(stringUtilpythonTable.getFileName(), stringUtilpythonTable);
                }
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
