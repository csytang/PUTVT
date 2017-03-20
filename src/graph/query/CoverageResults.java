package graph.query;

import com.intellij.coverage.CoverageDataManager;
import com.intellij.coverage.CoverageSuitesBundle;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import graph.helper.ProjectFileNamesUtil;
import graph.pycharm.console.GraphRelationship;
import graph.query.api.GraphQueryNotification;
import graph.query.api.GraphQueryPlan;
import graph.query.api.GraphQueryResultColumn;
import graph.query.api.GraphQueryResultRow;
import graph.query.graph.GraphCoverageResult;
import graph.visualization.api.GraphNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

/**
 * Created by Cegin on 14.3.2017.
 */
public class CoverageResults implements GraphCoverageResult {


    private Project project;

    public CoverageResults(Project project) {
        this.project=project;
    }

    @Override
    public String getResultSummary() {


        StringBuilder sb = new StringBuilder();

            sb.append(format("Nodes created: %s\n", "node"));

           /* if (counters.nodesDeleted() > 0) {
                sb.append(format("Nodes deleted: %s\n", counters.nodesDeleted()));
            }
            if (counters.labelsAdded() > 0) {
                sb.append(format("Labels added: %s\n", counters.labelsAdded()));
            }
            if (counters.labelsRemoved() > 0) {
                sb.append(format("Labels removed: %s\n", counters.labelsRemoved()));
            }
            if (counters.relationshipsCreated() > 0) {
                sb.append(format("Relationships created: %s\n", counters.relationshipsCreated()));
            }
            if (counters.relationshipsDeleted() > 0) {
                sb.append(format("Relationships deleted: %s\n", counters.relationshipsDeleted()));
            }
            if (counters.propertiesSet() > 0) {
                sb.append(format("Properties set: %s\n", counters.propertiesSet()));
            }*/

                sb.append(format("Indexes added: %s\n", "INDEX"));


      /*  if (summary.hasProfile()) {
            sb.append("Profile:\n");
            profileToString(sb, summary.profile(), 1);
        } else if (summary.hasPlan()) {
            sb.append("Plan:\n");
            planToString(sb, summary.plan(), 1);
        }*/

       /* if (summary.notifications().size() > 0) {
            sb.append("Notifications:\n");
            for (Notification notification : summary.notifications()) {
                sb.append(format("[%s] %s(%s) - %s", notification.severity(),
                        notification.title(), notification.code(), notification.description()));
            }
        }*/
        return sb.toString();
    }



    @Override
    public List<GraphQueryResultColumn> getColumns() {
        return new ArrayList<>();
    }

    @Override
    public List<GraphQueryResultRow> getRows() {
        return new ArrayList<>();
    }

    @Override
    public List<GraphNode> getNodes() {
        List<String> namesOfFiles = ProjectFileNamesUtil.getFileNamesFromProject(project.getBaseDir());

        List<GraphNode> nodes = new ArrayList<>();
        int i = 0;
        for (String nameOfFile : namesOfFiles){
            String[] str = nameOfFile.split("/");
            String file = str[str.length-1];
            CoverageNode node = new CoverageNode(file);
            node.setCoverage(getCovForFile(file));
            node.setColor(node.getCoverage()/10);
            nodes.add(node);
        }
        return nodes;
    }

    @Override
    public List<GraphRelationship> getRelationships() {
      /*  Neo4jBoltRelationship relationship = new Neo4jBoltRelationship("rel");
        relationship.setStartNode(nodes.get(0));
        relationship.setEndNode(nodes.get(1));*/

        List<GraphRelationship> relatonships = new ArrayList<>();
       // relatonships.add(relationship);
        return relatonships;
    }

    @Override
    public List<GraphQueryNotification> getNotifications() {
        return new ArrayList<>();
    }

    @Override
    public boolean hasPlan() {
        return true;
    }

    @Override
    public boolean isProfilePlan() {
        return true;
    }

    @Override
    public Optional<GraphQueryPlan> getPlan() {
        return Optional.of(null);
    }

    private Integer getCovForFile(String fileName){
        PsiFile[] file = FilenameIndex.getFilesByName(project, fileName, GlobalSearchScope.allScope(project));
        CoverageDataManager coverageDataManager = CoverageDataManager.getInstance(project);
        CoverageSuitesBundle coverageSuitesBundle = coverageDataManager.getCurrentSuitesBundle();
        String info = CoverageDataManager.getInstance(project).getCurrentSuitesBundle().getAnnotator(project).getFileCoverageInformationString(file[0],coverageSuitesBundle, coverageDataManager);
        if (info.contains("%")){
            String[] str = info.split("%");
            return Integer.parseInt(str[0]);
        }
        return 0;

    }
}
