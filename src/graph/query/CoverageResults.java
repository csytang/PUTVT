package graph.query;

import com.intellij.openapi.project.Project;
import graph.helper.*;
import graph.pycharm.console.GraphRelationship;
import graph.pycharm.services.RelationsService;
import graph.query.api.ResultsPlan;
import graph.query.graph.GraphCoverageResult;
import graph.visualization.api.GraphNode;

import java.util.*;

import static graph.helper.ProjectFileNamesUtil.getFileNamesFromProject;
import static java.lang.String.format;

/**
 * Created by Cegin on 14.3.2017.
 */
public class CoverageResults implements GraphCoverageResult {


    private Project project;

    private Hashtable nodeHashTable = new Hashtable();

    private List<String> namesOfFiles;

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
    public List<GraphNode> getNodes() {
        List<String> namesOfFiles = getFileNamesFromProject(project.getBaseDir());
        this.namesOfFiles=namesOfFiles;
        List<GraphNode> nodes = new ArrayList<>();
        int i = 0;
        for (String nameOfFile : namesOfFiles){
            String[] str = nameOfFile.split("/");
            String file = str[str.length-1];
            CoverageNode node = new CoverageNode(file);
            node.setCoverage(GetOnlyCoveragedFileNames.getCovForFile(file,project));
            node.getTypes().add("Coverage is: " + node.getCoverage() + "%.");
            node.setColor(node.getCoverage()/10);
            nodeHashTable.put(file, node);
            nodes.add(node);
        }
        return nodes;
    }

    @Override
    public List<GraphRelationship> getRelationships() {
        Hashtable relations = RelationsService.getRelations(project, namesOfFiles);
        List<GraphRelationship> relatonships = new ArrayList<>();
        List<String> filePaths = ProjectFileNamesUtil.getFileNamesFromProject(project.getBaseDir());
        for (String filePath : filePaths){
            String str[] = filePath.split("/");
            String name = str[str.length-1];
            CoverageNode startNode = (CoverageNode) nodeHashTable.get(name);
            ImportFileUtil importFileUtil = (ImportFileUtil) relations.get(name);
            if (importFileUtil != null){
                for (ImportFrom importFrom : importFileUtil.getImportFromList())
                {
                    CoverageNode endNode = (CoverageNode) nodeHashTable.get(importFrom.getName());
                    NodeRelationship relation = new NodeRelationship(startNode.getId() + "->" + endNode.getId());
                    relation.setWeight(1);
                    relation.setCallsCount(getRelationWeight(importFrom).toString());
                    relation.setStartNode(startNode);
                    relation.setEndNode(endNode);
                    setRelationTypes(relation.getTypes(),getRelationWeight(importFrom));
                    HashMap<String, Object> properties = new HashMap<>();
                    getPropertiesForRelations(properties, importFrom);
                    ResultsPropertyContainer resultsPropertyContainer = new ResultsPropertyContainer(properties);
                    relation.setPropertyContainer(resultsPropertyContainer);
                    relatonships.add(relation);
                }
            }
        }
        return relatonships;
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
    public Optional<ResultsPlan> getPlan() {
        return Optional.of(null);
    }

    private Integer getRelationWeight(ImportFrom importFrom){
        int weight = 0;
        for (IntegerKeyValuePair keyValuePair : importFrom.getKeyValuePairList()){
            weight += keyValuePair.getValue();
        }
        return weight;
    }

    private void setRelationTypes(List<String> types, Integer number){
        types.add("Times called: " + number.toString());
    }

    private void getPropertiesForRelations(HashMap<String, Object> properties, ImportFrom importFrom){
        for (IntegerKeyValuePair keyValuePair : importFrom.getKeyValuePairList()){
            properties.put(keyValuePair.getKey(), keyValuePair.getValue().toString());
        }
    }

    private float normalizeWeight(float weight){
        if (weight >= 2){
            return 2;
        }
        else{
            return weight;
        }
    }
}
