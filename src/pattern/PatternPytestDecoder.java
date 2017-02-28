package pattern;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vfs.VirtualFile;
import util.StringPytestUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cegin on 18.2.2017.
 */
public class PatternPytestDecoder implements PatternDecoder{
    private Hashtable strings =  new Hashtable();

    public PatternPytestDecoder(AnActionEvent anActionEvent){

    }
    
    @Override
    public Boolean patternDecode(VirtualFile virtualFile) throws FileNotFoundException {
        List<String> testLines = new ArrayList<>();
        Scanner in = new Scanner(new FileReader(virtualFile.getPath()));
        String all = "", line;
        while (in.hasNextLine()) {
            line = in.nextLine();
            all += line;
            all += '\n';
        }
        in.close();
        all = all.trim();
        all = all.replaceAll("(?m)^[ \t]*\r?\n", "");
        System.out.print(all);

        Pattern p1 = Pattern.compile("_______(.*)_______");
        Matcher m1 = p1.matcher(all);
        int count = 0;

        Pattern p = Pattern.compile("\\n.*.py:\\d*:.*");
        Matcher m = p.matcher(all);

        while (m1.find() && m.find()) {
            count++;
            testLines.add(all.substring(m1.start(), m.end()).replaceAll("( )+", " "));
        }
        if (count == 0) {
            return false;
        }

        createHashtable(testLines);

        return true;

    }


    @Override
    public Hashtable getFileKeyDTOObject(){
        return strings;
    }

    //TODO FIND A USAGE OR REFACTOR
    @Override
    public List<String> getFileNames() {
        return null;
    }


    private void createHashtable(List<String> testLines) {
        int count = 0;
        for (String testLine : testLines) {
            String arr[] = testLine.split("\n");
            String last = arr[arr.length - 1];
            String helpArr[] = last.split(":");
            String fileName = helpArr[0].replace("\n", "");
            if (strings.get(fileName) != null) {
                StringPytestUtil stringPytestUtil = (StringPytestUtil) strings.get(fileName);
                stringPytestUtil.getLineNumber().add(Integer.parseInt(helpArr[1]));
                stringPytestUtil.getTypeOfError().add(helpArr[2]);
                stringPytestUtil.getTestNames().add(arr[0]);
                stringPytestUtil.getLinesDuringRuntime().add(getArrayExecutedLines(arr));

                strings.remove(fileName);
                strings.put(fileName, stringPytestUtil);
            } else {
                StringPytestUtil stringPytestUtil = new StringPytestUtil();
                stringPytestUtil.setFileName(fileName);

                List<Integer> numbersOfLines = new ArrayList<>();
                numbersOfLines.add(Integer.parseInt(helpArr[1]));

                List<String> typeOfErrors = new ArrayList<>();
                typeOfErrors.add(helpArr[2]);

                List<String> nameOfTests = new ArrayList<>();
                nameOfTests.add(arr[0]);

                List<String[]> linesDuringRuntime = new ArrayList<>();
                linesDuringRuntime.add(getArrayExecutedLines(arr));

                stringPytestUtil.setLinesDuringRuntime(linesDuringRuntime);
                stringPytestUtil.setLineNumber(numbersOfLines);
                stringPytestUtil.setTypeOfError(typeOfErrors);
                stringPytestUtil.setTestNames(nameOfTests);
                strings.put(fileName, stringPytestUtil);
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
}
