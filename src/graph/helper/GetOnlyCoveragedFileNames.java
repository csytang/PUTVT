package graph.helper;

import com.intellij.coverage.CoverageDataManager;
import com.intellij.coverage.CoverageSuitesBundle;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;

import java.util.ArrayList;
import java.util.List;

public class GetOnlyCoveragedFileNames {

    /**
     * Gets only coveraged file names - only nodes with coverage bigger than 0
     * @param project Project
     * @param filenames Names of files used in project
     * @return
     */
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

    /**
     *  Gets the current coverage for file from @{@link CoverageDataManager}
     * @param fileName
     * @param project
     * @return
     */
    public static Integer getCovForFile(String fileName, Project project){
        PsiFile[] file = FilenameIndex.getFilesByName(project, fileName, GlobalSearchScope.allScope(project));
        CoverageDataManager coverageDataManager = CoverageDataManager.getInstance(project);
        String info;
        CoverageSuitesBundle coverageSuitesBundle = coverageDataManager.getCurrentSuitesBundle();
        if (coverageSuitesBundle == null){return 0;}
        if (file.length > 0) {
            info = CoverageDataManager.getInstance(project).getCurrentSuitesBundle().getAnnotator(project).getFileCoverageInformationString(file[0], coverageSuitesBundle, coverageDataManager);
        }
        else{return 0;}
        if (file.length > 1){
            for (PsiFile file1 : file){
                 info = CoverageDataManager.getInstance(project).getCurrentSuitesBundle().getAnnotator(project).getFileCoverageInformationString(file1,coverageSuitesBundle, coverageDataManager);
                if (file1.getVirtualFile().getUrl().contains(project.getBaseDir().getUrl())){
                    if (info != null && info.contains("%")) {
                        String[] str = info.split("%");
                        return Integer.parseInt(str[0]);
                    }
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
