package main.highlighter.pattern;

import com.intellij.openapi.vfs.VirtualFile;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.List;


public interface PatternDecoder {

     Boolean patternDecode(VirtualFile virtualFile) throws FileNotFoundException;
     Hashtable getFileKeyDTOObject();
     List<String> getFileNames();
     Boolean patternDecode(String consoleLogs);
}
