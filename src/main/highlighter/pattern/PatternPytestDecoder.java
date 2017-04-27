package main.highlighter.pattern;

import com.intellij.openapi.vcs.history.VcsRevisionNumber.Int;
import com.intellij.openapi.vfs.VirtualFile;
import main.highlighter.util.IntKeyIntValueObj;
import main.highlighter.util.StringPytestUtil;
import main.highlighter.util.TestResultsUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.intellij.dvcs.push.VcsPushReferenceStrategy.all;

/**
 * Class for decoding logs using PyTest failure pattern
 */
public class PatternPytestDecoder implements PatternDecoder{
    private List<String> fileNames = new ArrayList<>();
    private Hashtable strings =  new Hashtable();

    public PatternPytestDecoder(){

    }
    
    @Override
    public Boolean patternDecode(VirtualFile virtualFile) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader(virtualFile.getPath()));
        String all = "", line;
        while (in.hasNextLine()) {
            line = in.nextLine();
            all += line;
            all += '\n';
        }
        in.close();
       return doTheDecoding(all);
    }

    @Override
    public Boolean patternDecode(String consoleLogs) {
        return doTheDecoding(consoleLogs);
    }

    @Override
    public Hashtable getFileKeyDTOObject(){
        return strings;
    }

    @Override
    public List<String> getFileNames() {
        return fileNames;
    }

    private Boolean doTheDecoding(String all){
        List<String> testResults = new ArrayList<>();
        List<String> testLines = new ArrayList<>();
        all = all.trim();
        all = all.replaceAll("(?m)^[ \t]*\r?\n", "");

        Pattern p1 = Pattern.compile("_______(.*)_______"); //first line of failure ___TestName___
        Matcher failures = p1.matcher(all);
        int count = 0;

        Pattern p = Pattern.compile(".*.py:\\d*:.*"); //last line of pytest failure
        Matcher errors = p.matcher(all);

        List<IntKeyIntValueObj> failuresList = new ArrayList<>();
        List<IntKeyIntValueObj> errorsList = new ArrayList<>();

        while (failures.find()){
            IntKeyIntValueObj intKeyIntValueObj = new IntKeyIntValueObj(failures.start(),failures.end());
            failuresList.add(intKeyIntValueObj);
        }

        while (errors.find()){
            IntKeyIntValueObj intKeyIntValueObj = new IntKeyIntValueObj(errors.start(),errors.end());
            errorsList.add(intKeyIntValueObj);
        }

        for (int i = 0; i < failuresList.size();i++){
            IntKeyIntValueObj failure = failuresList.get(i);
            for (IntKeyIntValueObj error : errorsList){
                Pattern endOfTest = Pattern.compile("=== .* failed,.*");
                Matcher endMatcher;
                if (i+1!=failuresList.size()){
                     endMatcher = endOfTest.matcher(all.substring(failure.getKey(),failuresList.get(i+1).getKey()));
                    if (failuresList.get(i+1).getKey() < error.getKey()){
                        continue;
                    }
                }
                else{
                     endMatcher = endOfTest.matcher(all.substring(failure.getKey(),all.length()-1));
                }
                IntKeyIntValueObj matcherValue = new IntKeyIntValueObj();
                if (endMatcher.find()){
                    matcherValue = new IntKeyIntValueObj(endMatcher.start(),endMatcher.end());
                }
                if (error.getKey() > failure.getKey() && (matcherValue.getKey()==null || error.getKey() < matcherValue.getKey())){
                    count++;
                    doAdd(failure.getKey(),failure.getValue(),failure.getValue(),error.getValue(),all,testResults,testLines);
                }
            }
        }
        if (count == 0) {
            return false;
        }
        TestResultsUtil.getInstance().setResults(testResults);
        createHashtable(testLines); //creates hashtable from results
        return true;
    }

    private void createHashtable(List<String> testLines) {
        Pattern p = Pattern.compile(".*:([0-9]{1,}):");
        for (String testLine : testLines) {
            String arr[] = testLine.split("\n");
            String last = arr[arr.length - 1];
            Matcher m = p.matcher(last);
            if (!m.find()){
                continue;
            }
            String helpArr[] = last.split(".py:");
            String fileName = helpArr[0].replace("\n", "");
            fileName = helpArr[0].concat(".py");
            fileName = getFileName(fileName);
            if (strings.get(fileName) != null) {
                StringPytestUtil stringPytestUtil = (StringPytestUtil) strings.get(fileName);
                String numArr[] = helpArr[1].split(":");
                stringPytestUtil.getLineNumber().add(Integer.parseInt(numArr[0]));
                stringPytestUtil.getTypeOfError().add(numArr[1]);
                stringPytestUtil.getTestNames().add(arr[0]);
                stringPytestUtil.getLinesDuringRuntime().add(getArrayExecutedLines(arr));

                strings.remove(fileName);
                strings.put(fileName, stringPytestUtil);
            } else {
                StringPytestUtil stringPytestUtil = new StringPytestUtil();
                stringPytestUtil.setFileName(fileName); //name of file

                List<Integer> numbersOfLines = new ArrayList<>();
                String numArr[] = helpArr[1].split(":");
                numbersOfLines.add(Integer.parseInt(numArr[0])); //number of line where error occured

                List<String> typeOfErrors = new ArrayList<>();
                if (numArr.length == 2) {
                    typeOfErrors.add(numArr[1]); //type of error
                }
                else{
                    typeOfErrors.add(numArr[0]);
                }
                List<String> nameOfTests = new ArrayList<>();
                nameOfTests.add(arr[0]); //name of test - no usage yet

                List<String[]> linesDuringRuntime = new ArrayList<>();
                linesDuringRuntime.add(getArrayExecutedLines(arr));

                stringPytestUtil.setLinesDuringRuntime(linesDuringRuntime);
                stringPytestUtil.setLineNumber(numbersOfLines);
                stringPytestUtil.setTypeOfError(typeOfErrors);
                stringPytestUtil.setTestNames(nameOfTests);
                strings.put(fileName, stringPytestUtil);
                fileNames.add(fileName);
            }
        }
    }

    private String[] getArrayExecutedLines(String[] arr) {
        List<String> tmp = new ArrayList<>();
        Pattern p = Pattern.compile("> .*");
        for (String str : arr) {
            Matcher m = p.matcher(str);
            if (m.find()) {
                tmp.add(str);
            }
        }
        p = Pattern.compile("E .*");
        for (String str : arr) {
            Matcher m = p.matcher(str);
            if (m.find()) {
                tmp.add(str);
            }
        }
        String[] arrT = new String[tmp.size()];
        int count = 0;
        for (String temp : tmp) {
            arrT[count] = temp;
            count++;
        }
        return arrT;
    }

    private String getFileName(String file){
        String backSlash = Pattern.quote("\\");
        String[] arr = file.split(backSlash);
        return arr[arr.length-1];
    }

    private void doAdd(int failStart, int failEnd, int errStart, int errorEnd, String all, List<String> testResults, List<String> testLines){
        String testName = all.substring(failStart, failEnd);
        testName = testName.replace("_","");
        testName = testName.replace(" ","");
        testResults.add(testName);
        testLines.add(all.substring(errStart, errorEnd).replaceAll("( )+", " ")); //log from failure
    }
}
