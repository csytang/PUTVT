package test.graph.results;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import main.graph.helper.*;
import main.graph.pycharm.api.GraphRelationship;
import main.graph.pycharm.services.RelationsService;
import main.graph.results.CoverageResults;
import main.graph.results.NodeRelationship;
import main.graph.visualization.api.GraphNode;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoverageResultsTest {

    List<String> testFileNames;

    @Before
    public void prepareData(){
        testFileNames = new ArrayList<String>(
                Arrays.asList("sample1.py","sample.py", "sample2.py"));

        List<GraphNode> graphNodes = new ArrayList<>();

        ImportFileUtil importFileUtil = new ImportFileUtil("sample1.py");
        List<ImportFrom> importFroms = new ArrayList<>();
        ImportFrom importFrom = new ImportFrom("sample.py");
        importFroms.add(importFrom);
        IntegerKeyValuePair pair1 = new IntegerKeyValuePair("Block",2);
        IntegerKeyValuePair pair2 = new IntegerKeyValuePair("Module",3);
        List<IntegerKeyValuePair> integerKeyValuePairs= new ArrayList<IntegerKeyValuePair>(
                Arrays.asList(pair1,pair2));

        importFrom.setKeyValuePairList(integerKeyValuePairs);
        importFileUtil.setImportFromList(importFroms);


        HashtableResultsUtil.getInstance().setNodes(graphNodes);
    }

    @Test
    public void shouldReturnNodes(){
        when(ProjectFileNamesUtil.getFileNamesFromProject(any(VirtualFile.class))).thenReturn(testFileNames);
        //when(RelationsService.getRelations(any(Project.class),testFileNames)).then();

        CoverageResults coverageResults = new CoverageResults(mock(Project.class));
    }

}
