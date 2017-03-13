package highlighter.pattern;

import com.intellij.openapi.vfs.VirtualFile;
import highlighter.util.StringPytestUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cegin on 30.1.2017.
 */
public class PatternPythonDecoder implements PatternDecoder{
    private String[] lines;
    Hashtable strings =  new Hashtable();
    private List<String> fileNames = new ArrayList<>();

    public PatternPythonDecoder(){

    }

    @Override
    public Boolean patternDecode(VirtualFile virtualFile) throws FileNotFoundException {
        List<String> pythonTraceBacks = new ArrayList<>();

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


    //TODO REFACTOR
    private Boolean doTheDecoding(String all){
        List<String> pythonTraceBacks = new ArrayList<>();
        if (!all.contains("Traceback")) {
            return false;
        }

        Pattern p1 = Pattern.compile("Traceback");
        Matcher m1 = p1.matcher(all);

        Pattern p2 = Pattern.compile(".*Error:.*");
        Matcher m2 = p2.matcher(all);

        int count = 0;

        while (m1.find() && m2.find()) {
            count++;
            pythonTraceBacks.add(all.substring(m1.start(), m2.end()));
        }

        for (String traceback : pythonTraceBacks) {
            Pattern p = Pattern.compile("File ");
            String reason="";
            lines = p.split(traceback);

            if (!lines[lines.length-1].endsWith("\n")) {
                String[] help = lines[lines.length-1].split("\n");
                reason = help[help.length - 1];
            }
            else{
                String[] help = lines[lines.length-1].split("\n");
                reason = help[help.length - 2];
            }
            for (int i = 1; i < lines.length; i++) {
                String[] tmp = lines[i].split(",");
                int lineNumber = Integer.parseInt(tmp[1].replace("line", "").replace(" ", ""));

                String fileName = tmp[0].replace("\"", "");

                p2 = Pattern.compile("/(.*).py");
                m2 = p2.matcher(fileName);
                if (m2.find()){
                    String arr[] = fileName.split("/");
                    fileName = arr[arr.length-1];
                }
                if (strings.get(fileName) != null) {
                    StringPytestUtil pytestUtil = (StringPytestUtil) strings.get(fileName);

                    List<Integer> lineNumbers = pytestUtil.getLineNumber();
                    lineNumbers.add(lineNumber);
                    pytestUtil.setLineNumber(lineNumbers);

                    List<String[]> linesDuringRuntime = pytestUtil.getLinesDuringRuntime();
                    String[] dao = {tmp[2].trim()};
                    linesDuringRuntime.add(dao);
                    pytestUtil.setLinesDuringRuntime(linesDuringRuntime);

                    List<String> typeOfError = pytestUtil.getTypeOfError();
                    typeOfError.add(reason);
                    pytestUtil.setTypeOfError(typeOfError);

                    strings.remove(fileName);
                    strings.put(fileName, pytestUtil);

                } else {
                    fileNames.add(fileName);
                    StringPytestUtil pytestUtil = new StringPytestUtil();
                    pytestUtil.setFileName(fileName);

                    List<Integer> lineNumbers = new ArrayList<>();
                    lineNumbers.add(lineNumber);
                    pytestUtil.setLineNumber(lineNumbers);

                    List<String[]> linesDuringRuntime = new ArrayList<>();
                    String[] dao = {tmp[2].trim()};
                    linesDuringRuntime.add(dao);
                    pytestUtil.setLinesDuringRuntime(linesDuringRuntime);

                    List<String> typeOfError = new ArrayList<>();
                    typeOfError.add(reason);
                    pytestUtil.setTypeOfError(typeOfError);

                    strings.put(fileName, pytestUtil);
                }
            }
        }
        return true;
    }

    @Override
    public Hashtable getFileKeyDTOObject() {
        return strings;
    }

    public List<String> getFileNames(){return fileNames;}



}
