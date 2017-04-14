package graph.results;

import com.intellij.openapi.project.Project;
import graph.helper.*;
import graph.pycharm.api.GraphRelationship;
import graph.pycharm.services.RelationsService;
import graph.results.api.GraphCoverageResult;
import graph.testresults.TestResultKeyValuePair;
import graph.testresults.TestResultsCollector;
import graph.visualization.api.GraphNode;

import java.util.*;

import static graph.helper.ProjectFileNamesUtil.getFileNamesFromProject;
import static java.lang.String.format;

public class CoverageResults implements GraphCoverageResult {

    private Hashtable resultsOfRanTests = null;

    private Project project;

    private Hashtable nodeHashTable = new Hashtable();

    public CoverageResults(Project project) {
        this.project=project;
    }

    private Hashtable relations;

    @Override
    public List<GraphNode> getNodes() {
        List<String> namesOfFiles = getFileNamesFromProject(project.getBaseDir());
        this.relations = RelationsService.getRelations(project, namesOfFiles);
        List<GraphNode> nodes = new ArrayList<>();
        nodeHashTable = new Hashtable();
        nodeHashTable.clear();
        resultsOfRanTests = HashtableResultsUtil.copyHashtableTestResults(TestResultsCollector.getInstance().getTestResults());
        int i = 0;
        for (String nameOfFile : namesOfFiles){
            String[] str = nameOfFile.split("/");
            String file = str[str.length-1];
            Hashtable fileTestResults = (Hashtable) resultsOfRanTests.get(file);
            if (nodeHashTable.get(file)!=null){
                file=file.concat(" (" + i++ + ")");
            }
            CoverageNode node = new CoverageNode(file);
            node.setCoverage(GetOnlyCoveragedFileNames.getCovForFile(file,project));
            node.getTypes().add("Coverage is: " + node.getCoverage() + "%.");
            node.setColor(node.getCoverage()/10);
            if (fileTestResults != null) {
                node.setOutColorNumber(getNodeOutColor(fileTestResults));
            }
            HashMap<String, Object> properties = new HashMap<>();
            getPropertiesForNodes(properties, nameOfFile);
            ResultsPropertyContainer resultsPropertyContainer = new ResultsPropertyContainer(properties);
            node.setResultsPropertyContainer(resultsPropertyContainer);
            nodeHashTable.put(file, node);
            nodes.add(node);
        }
        if (HashtableResultsUtil.getInstance().getOnlyCoveraged()) {
            nodes = doCleaning(nodes);
        }
        HashtableResultsUtil.getInstance().setNodes(nodes);
        return nodes;
    }

    @Override
    public List<GraphRelationship> getRelationships() {
        int localMax=1;
        Hashtable checkIfRelationExists = new Hashtable();
        List<GraphRelationship> relatonships = new ArrayList<>();
        List<String> filePaths = ProjectFileNamesUtil.getFileNamesFromProject(project.getBaseDir());
        for (String filePath : filePaths){
            String str[] = filePath.split("/");
            String name = str[str.length-1];
            CoverageNode startNode = (CoverageNode) nodeHashTable.get(name);
            ImportFileUtil importFileUtil = (ImportFileUtil) relations.get(name);
            if (importFileUtil != null && startNode != null){
                for (ImportFrom importFrom : importFileUtil.getImportFromList())
                {
                    NodeRelationship relation;
                    CoverageNode endNode = (CoverageNode) nodeHashTable.get(importFrom.getName());
                    String nameOfRelation = startNode.getId() + "->" + endNode.getId();
                    if (checkIfRelationExists.get(nameOfRelation) != null){
                        relation = (NodeRelationship) checkIfRelationExists.get(nameOfRelation);
                        relatonships.remove(relation);
                        relation.setWeight(getRelationWeight(importFrom) + relation.getWeight());
                        if (localMax < relation.getWeight()){localMax= (int) relation.getWeight();}
                        relation.setCallsCount("" + (int) relation.getWeight());
                        getPropertiesForRelations(relation.getPropertyContainer().getProperties(), importFrom);
                        setRelationTypes(relation.getTypes(), relation.getWeight());
                        relatonships.add(relation);
                        continue;
                    }
                    relation = new NodeRelationship(nameOfRelation);
                    relation.setWeight(getRelationWeight(importFrom));
                    if (localMax < getRelationWeight(importFrom)){localMax=getRelationWeight(importFrom);}
                    relation.setCallsCount(getRelationWeight(importFrom).toString());
                    relation.setStartNode(startNode);
                    relation.setEndNode(endNode);
                    setRelationTypes(relation.getTypes(),relation.getWeight());
                    HashMap<String, Object> properties = new HashMap<>();
                    getPropertiesForRelations(properties, importFrom);
                    ResultsPropertyContainer resultsPropertyContainer = new ResultsPropertyContainer(properties);
                    relation.setPropertyContainer(resultsPropertyContainer);
                    checkIfRelationExists.put(relation.getId(), relation);
                    relatonships.add(relation);
                }
            }
        }
        for(GraphRelationship relationship : relatonships){
            relationship.setWeight(normalizeWeight(relationship.getWeight(), localMax));
        }
        return relatonships;
    }

    private Integer getRelationWeight(ImportFrom importFrom){
        int weight = 0;
        for (IntegerKeyValuePair keyValuePair : importFrom.getKeyValuePairList()){
            weight += keyValuePair.getValue();
        }
        return weight;
    }

    private void setRelationTypes(List<String> types, double number){
        if (types.size()!=0){
            types.clear();
        }
        types.add("Times called: " + ((int) number));
    }

    private void getPropertiesForNodes(HashMap<String, Object> properties, String fileName){
        properties.put("Full path to file: ", fileName);
    }

    private void getPropertiesForRelations(Map<String, Object> properties, ImportFrom importFrom){
        for (IntegerKeyValuePair keyValuePair : importFrom.getKeyValuePairList()){
            properties.put(keyValuePair.getKey(), keyValuePair.getValue().toString());
        }
    }

    private double normalizeWeight(double weight, int max){
        double addition = 1.0f / max;
        return  1+(addition*weight);
    }

    private List<GraphNode> doCleaning(List<GraphNode> nodes){
        List<GraphNode> addNode = new ArrayList<>();
        for (GraphNode node : nodes) {
            String name = node.getId();
            ImportFileUtil importFileUtil = (ImportFileUtil) relations.get(name);
            if (importFileUtil != null && node.getCoverage()!=0){
                node.setAdded(true);
                addNode.add(node);
                for (ImportFrom importFrom : importFileUtil.getImportFromList())
                {
                    CoverageNode endNode = (CoverageNode) nodeHashTable.get(importFrom.getName());
                    if (!endNode.getAdded()){
                        endNode.setAdded(true);
                        addNode.add(endNode);
                    }
                }
            }
            else{
                if (node.getCoverage() != 0 && !node.getAdded()){
                    node.setAdded(true);
                    addNode.add(node);
                }
            }
        }
        nodeHashTable.clear();
        for (GraphNode node : addNode) {
            nodeHashTable.put(node.getId(),node);
        }
        return addNode;
    }

    private int getNodeOutColor(Hashtable hashtable){
        Enumeration e = hashtable.keys();
        int i = 0, j = 0;
        while(e.hasMoreElements()){
            String key = (String) e.nextElement();
            TestResultKeyValuePair testResultKeyValuePair = (TestResultKeyValuePair) hashtable.get(key);
            if (testResultKeyValuePair.getChanged()){
                if (testResultKeyValuePair.getTestResult() == 0){
                    i++;
                }
                if (testResultKeyValuePair.getTestResult() == 1){
                    j++;
                }
            }
        }
        if (i>0){
            if (j>0){return 3;}
            if (j==0){return 2;}
        }
        else{
            if (j>0){return 1;}
        }
        return 0;
    }
}
