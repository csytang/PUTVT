package graph.helper;

import com.intellij.coverage.CoverageDataManager;
import com.intellij.coverage.CoverageSuitesBundle;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cegin on 24.3.2017.
 */
public class GetOnlyCoveragedFileNames {


    public static List<String> getCoveragedFileNames(Project project, List<String> filenames){
        List<String> coveragedFiles = new ArrayList<>();
        for (String filename : filenames) {
            String[] str = filename.split("/");
            String name = str[str.length-1];
            if (getCovForFile(name, project) >= 0) {
                coveragedFiles.add(name);
            }
        }
        return coveragedFiles;
    }

    public static Integer getCovForFile(String fileName, Project project){
        PsiFile[] file = FilenameIndex.getFilesByName(project, fileName, GlobalSearchScope.allScope(project));
        CoverageDataManager coverageDataManager = CoverageDataManager.getInstance(project);
        CoverageSuitesBundle coverageSuitesBundle = coverageDataManager.getCurrentSuitesBundle();
        if (coverageSuitesBundle == null){return 0;}
        String info = CoverageDataManager.getInstance(project).getCurrentSuitesBundle().getAnnotator(project).getFileCoverageInformationString(file[0],coverageSuitesBundle, coverageDataManager);
        if (file.length > 1){
            //TODO FIX SO THAT THE REAL FILE IS CHECKED - SOME LOCATION CHECK
            for (PsiFile file1 : file){
                 info = CoverageDataManager.getInstance(project).getCurrentSuitesBundle().getAnnotator(project).getFileCoverageInformationString(file1,coverageSuitesBundle, coverageDataManager);
                if (info != null && info.contains("%")){
                    String[] str = info.split("%");
                    return Integer.parseInt(str[0]);
                }
            }
        }
        if (info != null && info.contains("%")){
            String[] str = info.split("%");
            return Integer.parseInt(str[0]);
        }
        return 0;

    }
}
