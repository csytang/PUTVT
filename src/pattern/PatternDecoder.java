package pattern;

import com.intellij.openapi.vfs.VirtualFile;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Cegin on 18.2.2017.
 */
public interface PatternDecoder {

    public Boolean patternDecode(VirtualFile virtualFile) throws FileNotFoundException;
    public Hashtable getFileKeyDTOObject();
    public List<String> getFileNames();
    public Boolean patternDecode(String consoleLogs);
}
