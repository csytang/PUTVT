package main.graph.pycharm.services;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import main.graph.helper.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RelationsService {
    /**
     * Get all relations between nodes
     * @param project Current Project
     * @param nameOfFiles List of all files in project
     * @return Hashtable with relations
     */
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
                    lineOfImports = lineOfImports.replace("(",""); //in case of: from ... import (...,...,...,...)
                    lineOfImports = lineOfImports.replace(")","");
                    String[] lineSplit = lineOfImports.split(" import ");
                    lineSplit[0] = lineSplit[0].replace("from ",""); //remove from word
                     if (".".equals(lineSplit[0])){
                         continue;
                     }
                    if (lineSplit[0].contains(".")) { //dot notation used in import
                        String dot = Pattern.quote(".");
                        String[] nameFileLineSplit = lineSplit[0].split(dot);
                        lineSplit[0] = nameFileLineSplit[nameFileLineSplit.length - 1];
                    }
                    lineSplit[0] = lineSplit[0].concat(".py"); //according to node name
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
                        hashtable.put(importFileUtil.getName(),importFileUtil);//put into hashtable
                    }
                }
            }
        }
        return hashtable;
    }

    /**
     * Gets all the IntegerKeyValuePair - integer for number of times the key - import, was called in file
     * @param imports imports in form of string
     * @param file the file with source code
     * @return list of integerkeyvaluepair
     */
    private static List<IntegerKeyValuePair> getAllIntegerKeyValuePair(String imports, String file){
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
            int poc = 0;
            String local = "[^A-z0-9]" + localName + "[^A-z0-9]";
            Pattern p = Pattern.compile(local);
            Matcher m = p.matcher(file);
            while (m.find()){
                try {
                    if (isAllowedAndNotImportCharBeforeStatement(file.charAt(m.start() - 1), file.substring(m.start() - 6, m.start()), file.substring(m.end(), m.end() + 6))) {
                        poc++;
                    }
                }
                catch (Exception e){ //sometimes weird patterns can cause this
                    continue;
                }
            }
            integerKeyValuePair.setValue(poc);
            integerKeyValuePairList.add(integerKeyValuePair);
        }
        return integerKeyValuePairList;
    }

    private static boolean isAllowedAndNotImportCharBeforeStatement(char c, String imp, String from){
        String str = c + "";
        String fr = from.replace(" ","");
        if ("import".equals(imp) || "import".equals(fr) || c==','){
            return false;
        }
        if (str.equals("-") || str.equals("_") || str.equals("-")){
             return false;
        }
        return true;
    }

    //Custom List check
    private static boolean isFromList(String str, List<String> strs){
        for (String s : strs){
            String[] arr = s.split("/");
            if (arr[arr.length-1].equals(str)){return true;}
        }
        return false;
    }
}
