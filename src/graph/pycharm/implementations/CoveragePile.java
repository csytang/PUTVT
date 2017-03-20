package graph.pycharm.implementations;

import com.intellij.coverage.CoverageAnnotator;

/**
 * Created by Cegin on 19.3.2017.
 */
public class CoveragePile {

    private static CoveragePile coveragePile;
    private CoverageAnnotator coverageAnnotator = null;

    public static CoveragePile getInstance(){
        if (coveragePile == null ){
            coveragePile = new CoveragePile();
        }
        return coveragePile;
    }

    private CoveragePile() {

    }

    public CoverageAnnotator getCoverageAnnotator() {
        return coverageAnnotator;
    }

    public void setCoverageAnnotator(CoverageAnnotator coverageAnnotator) {
        this.coverageAnnotator = coverageAnnotator;
    }
}
