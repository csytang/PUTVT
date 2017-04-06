package graph.visualization;

import prefuse.action.assignment.DataSizeAction;
import prefuse.data.tuple.TupleSet;
import prefuse.util.DataLib;
import prefuse.util.MathLib;

import java.util.logging.Logger;

public class OwnDataSizeAction extends DataSizeAction{
    public OwnDataSizeAction(String s, String s1) {
        super(s, s1);
    }

    protected void setup() {
        TupleSet var1 = this.m_vis.getGroup(this.m_group);
        this.m_tempScale = this.m_scale;
        if(this.m_inferBounds) {
            if(this.m_scale == 3 && this.m_bins > 0) {
                double[] var2 = DataLib.toDoubleArray(var1.tuples(), this.m_dataField);
                this.m_dist = MathLib.quantiles(this.m_bins, var2);
            } else {
                if(this.m_scale == 3) {
                    Logger.getLogger(this.getClass().getName()).warning("Can\'t use quantile scale with no binning. Defaulting to linear scale. Set the bin value greater than zero to use a quantile scale.");
                    this.m_scale = 0;
                }

                this.m_dist = new double[2];
                this.m_dist[0] = DataLib.min(var1, this.m_dataField).getDouble(this.m_dataField);
                this.m_dist[1] = DataLib.max(var1, this.m_dataField).getDouble(this.m_dataField);
                if (this.m_dist[0] < 0){
                    this.m_dist[0] = this.m_dist[0]*-1;
                }
                if (this.m_dist[1] < 0){
                    this.m_dist[1] = this.m_dist[1]*-1;
                }
            }

            if(this.m_inferRange) {
                this.m_sizeRange = this.m_dist[this.m_dist.length - 1] / this.m_dist[0] - this.m_minSize;
            }
        }

    }
}
