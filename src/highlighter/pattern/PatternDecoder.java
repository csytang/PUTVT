package highlighter.pattern;

import com.intellij.openapi.vfs.VirtualFile;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.List;


public interface PatternDecoder {

    public Boolean patternDecode(VirtualFile virtualFile) throws FileNotFoundException;
    public Hashtable getFileKeyDTOObject();
    public List<String> getFileNames();
    public Boolean patternDecode(String consoleLogs);
}
