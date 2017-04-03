package graph.pycharm.services;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import graph.helper.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RelationsService {

    public static Hashtable getRelations(Project project, List<String> nameOfFiles){
        Hashtable hashtable = new Hashtable();
        List<String> namesOfFiles = ProjectFileNamesUtil.getFileNamesFromProject(project.getBaseDir());
        namesOfFiles = GetOnlyCoveragedFileNames.getCoveragedFileNames(project, namesOfFiles);
        for (String nameOfFile : namesOfFiles){ //for each coveraged file
            PsiFile[] file = FilenameIndex.getFilesByName(project, nameOfFile, GlobalSearchScope.allScope(project));
            String contents = file[0].getViewProvider().getContents().toString();
            if(contents.contains("import")){ //file contains any imports
                Pattern p = Pattern.compile(".*import.*");
                Matcher m = p.matcher(contents);
                while (m.find()) {
                    String lineOfImports = contents.substring(m.start(), m.end());
                    String[] lineSplit = lineOfImports.split(" import ");
                    lineSplit[0] = lineSplit[0].replace("from ","");
                    if (lineSplit[0].contains(".")) {
                        String dot = Pattern.quote(".");
                        String[] nameFileLineSplit = lineSplit[0].split(dot);
                        lineSplit[0] = nameFileLineSplit[nameFileLineSplit.length - 1];
                    }
                    lineSplit[0] = lineSplit[0].concat(".py");
                    lineSplit[0] = lineSplit[0].replace(" ", "");
                    if (isFromList(lineSplit[0], nameOfFiles)){ //is a import from project
                        ImportFileUtil importFileUtil = new ImportFileUtil(nameOfFile); //new import file
                        if (hashtable.get(nameOfFile) != null){
                            importFileUtil = (ImportFileUtil) hashtable.get(nameOfFile);
                        }
                        List<ImportFrom> importFromList = importFileUtil.getImportFromList(); //list of module imports
                        if (importFromList == null){
                            importFromList = new ArrayList<>();
                        }
                        ImportFrom importFrom = new ImportFrom(lineSplit[0]);
                        importFrom.setKeyValuePairList(getAllIntegerKeyValuePair(lineSplit[1], contents)); //list of functions/objects imported for certain module
                        importFromList.add(importFrom);
                        importFileUtil.setImportFromList(importFromList);
                        hashtable.remove(importFileUtil.getName());
                        hashtable.put(importFileUtil.getName(),importFileUtil);
                    }
                }
            }
        }
        return hashtable;
    }

    private static List<IntegerKeyValuePair> getAllIntegerKeyValuePair(String imports, String file){
       // String str = imports.replace(" ", "");
        String[] importNames = imports.split(",");
        List<IntegerKeyValuePair> integerKeyValuePairList = new ArrayList<>();
        for (String importName : importNames){
            IntegerKeyValuePair integerKeyValuePair;
            if (importName.contains(" as ")){
                integerKeyValuePair = new IntegerKeyValuePair(importName,0);
                String[] arr =importName.split(" as ");
                importName = arr[arr.length-1];
            }
            else {
                integerKeyValuePair = new IntegerKeyValuePair(importName, 0);
            }
            importName = importName.replace(" ", "");
            String localName = importName;
            importName = importName.concat("(");
            importName = Pattern.quote(importName);
            Pattern p = Pattern.compile(importName);
            Matcher m = p.matcher(file);
            int poc = 0;
            while (m.find()){
                if (isAllowedCharBeforeStatement(file.charAt(m.start()-1))){
                    poc++;
                }
            }
            String local = Pattern.quote("(" + localName + ")");
            p = Pattern.compile(local);
            m = p.matcher(file);
            while (m.find()){
                    poc++;
            }
            local = Pattern.quote(localName + ".");
            p = Pattern.compile(local);
            m = p.matcher(file);
            while (m.find()){
                if (isAllowedCharBeforeStatement(file.charAt(m.start()-1))){
                    poc++;
                }
            }
            integerKeyValuePair.setValue(poc);
            integerKeyValuePairList.add(integerKeyValuePair);
        }
        return integerKeyValuePairList;
    }

    private static boolean isAllowedCharBeforeStatement(char c){
        String str = c + "";
        Pattern subP = Pattern.compile("[A-z0-9]");
        Matcher subM = subP.matcher(str);
        if (subM.find()){
            return false;
        }
        if (str.equals("-") || str.equals("_") || str.equals("-")){
             return false;
        }
        return true;
    }

    private static boolean isFromList(String str, List<String> strs){
        for (String s : strs){
            String[] arr = s.split("/");
            if (arr[arr.length-1].equals(str)){return true;}
        }
        return false;
    }
}
