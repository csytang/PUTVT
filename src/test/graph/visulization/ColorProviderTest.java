package test.graph.visulization;


import main.graph.helper.HashtableResultsUtil;
import main.graph.pycharm.api.LookAndFeelService;
import main.graph.results.CoverageNode;
import main.graph.visualization.api.GraphNode;
import main.graph.visualization.settings.ColorProvider;
import org.junit.Before;
import org.junit.Test;
import prefuse.action.ActionList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;

public class ColorProviderTest {


    @Before
    public void setData(){
        List<GraphNode> graphNodes = new ArrayList<>();

        CoverageNode graphNode = new CoverageNode("1");
        graphNode.setColor(6);
        graphNode.setOutColorNumber(1);
        CoverageNode graphNode1 = new CoverageNode("1");
        graphNode1.setColor(6);
        graphNode1.setOutColorNumber(3);
        CoverageNode graphNode2 = new CoverageNode("1");
        graphNode2.setColor(2);
        graphNode2.setOutColorNumber(3);

        graphNodes.add(graphNode);
        graphNodes.add(graphNode1);
        graphNodes.add(graphNode2);

        HashtableResultsUtil.getInstance().setNodes(graphNodes);
    }

    @Test
    public void shouldReturnProperNodeFillColor(){
        ActionList actionList = ColorProvider.colors(mock(LookAndFeelService.class));

        assertNotEquals(actionList.size(),0);
    }

}
