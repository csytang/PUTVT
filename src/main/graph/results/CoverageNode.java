package main.graph.results;


import main.graph.visualization.api.GraphNode;
import main.graph.visualization.api.GraphPropertyContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CoverageNode implements GraphNode {

    private final String id;
    private final String path;
    private ResultsPropertyContainer propertyContainer;
    private List<String> types;
    private Integer coverage;
    private int color = 0;
    private Boolean set = false;
    private int OutColorNumber = 0;

    public CoverageNode(String id, String path) {
        this.id = id;
        this.path = path;
        this.types = new ArrayList<>();
        this.propertyContainer = new ResultsPropertyContainer();
    }

    public void setResultsPropertyContainer(ResultsPropertyContainer resultsPropertyContainer) {
        this.propertyContainer = resultsPropertyContainer;
    }

    public Integer getCoverage() {
        return coverage;
    }

    @Override
    public void setAdded(Boolean bol) {
        this.set=bol;
    }

    @Override
    public Boolean getAdded() {
        return set;
    }

    @Override
    public int getOutColor() {
        return OutColorNumber;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    public void setCoverage(Integer coverage) {
        this.coverage = coverage;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<String> getTypes() {
        return types;
    }

    @Override
    public String getTypesName() {
        return "coverage_info";
    }

    @Override
    public boolean isTypesSingle() {
        return false;
    }

    @Override
    public GraphPropertyContainer getPropertyContainer() {
        return propertyContainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoverageNode that = (CoverageNode) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setOutColorNumber(int color){
        this.OutColorNumber = color;
    }
}
