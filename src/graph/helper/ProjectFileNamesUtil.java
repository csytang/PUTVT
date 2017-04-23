package graph.helper;


import com.intellij.openapi.vfs.VirtualFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectFileNamesUtil {

    /**
     * Get all .py files from project
     * @param baseDir
     * @return
     */
    public static List<String> getFileNamesFromProject(VirtualFile baseDir) {
        List<String> fileNames = new ArrayList<>();
        List<VirtualFile> files = Arrays.asList(baseDir.getChildren());
        for (VirtualFile file : files){
            if (file.isDirectory()){
                fileNames.addAll(getFileNamesFromProject(file));
            }
            else if (("py").equals(file.getExtension())){
                fileNames.add(file.getUrl());
            }
        }
        return fileNames;
    }


}