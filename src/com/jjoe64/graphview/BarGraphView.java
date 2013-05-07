package com.jjoe64.graphview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import android.util.Log;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;

import java.util.*;

/**
 * Draws a Bar Chart
 * @author Muhammad Shahab Hameed
 */
public class BarGraphView extends GraphView {
	public BarGraphView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

    public BarGraphView(Context context, String title) {
        super(context, title);
    }

    protected boolean isBucketBarGraph = false;

    public void setIsBucketBarGraph(boolean val) {
        this.isBucketBarGraph = val;
    }

    static public class DiscreetGraphViewData extends GraphViewData {
        public final int discreetValueX;

        public DiscreetGraphViewData(int discreetValueX, double valueY) {
            super(discreetValueX, valueY);
            this.discreetValueX = discreetValueX;
        }
    }

    @Override
    protected void drawGraphSeries(Canvas canvas, List<GraphViewSeries> graphSeries, float graphwidth, float graphheight,
                                   float border, double minX, double minY, double diffX, double diffY, float horstart) {

        if (isBucketBarGraph) {
            class SeriesDataHolder {
                GraphViewData data;
                GraphViewSeriesStyle style;

                SeriesDataHolder(GraphViewData data, GraphViewSeriesStyle style) {
                    this.data = data;
                    this.style = style;
                }
            }

            DiscreetGraphViewData [] _dgvga = (DiscreetGraphViewData[]) graphSeries.get(graphSeries.size()-1).values;
            int maxValue = _dgvga[_dgvga.length-1].discreetValueX + 1;

            // generate buckets
            List<List<SeriesDataHolder>> buckets = new ArrayList<List<SeriesDataHolder>>(maxValue); // not strictly true, but good enough as a starting capacity
            for (int i = 0; i < maxValue; i++) {
                buckets.add(new ArrayList<SeriesDataHolder>());
            }

            // sort the data into buckets
            for (int i = 0; i < graphSeries.size(); i++) {
                GraphViewSeries series = graphSeries.get(i);
                for (int j = 0; j < series.values.length ; j++) {
                    DiscreetGraphViewData dgvd = (DiscreetGraphViewData)series.values[j];

                    Log.d("ADFActive:BarGraphView", "  (i: " + i + ", j:" + j + "): " + dgvd.discreetValueX);
                    buckets.get(dgvd.discreetValueX).add(new SeriesDataHolder(dgvd, series.getStyle()));
                }
            }

            float col_width = 0.84f * graphwidth / maxValue;
            // for each buckets
            for (int i = 0; i < buckets.size(); i++) {

                // go through each data
                List<SeriesDataHolder> bunch = buckets.get(i);

                float single_width = col_width / bunch.size();
                float half_width = - (col_width/2);
                for (int j = 0; j < bunch.size(); j++) {
                    SeriesDataHolder datah = bunch.get(j);
                    DiscreetGraphViewData ddd = (DiscreetGraphViewData)datah.data;

                    paint.setStrokeWidth(datah.style.thickness);
                    paint.setColor(datah.style.color);

                    float ratY = (float) (ddd.valueY / diffY);
                    float y = graphheight * ratY;

                    float startX = (ddd.discreetValueX * col_width) - half_width + (single_width * j);
                    canvas.drawRect(horstart + startX, (border - y) + graphheight, horstart + startX + single_width, graphheight + border, paint);

                }
            }

        } else {
            super.drawGraphSeries(canvas, graphSeries, graphwidth, graphheight, border, minX, minY, diffX, diffY, horstart);
        }
    }

	@Override
	public void drawSeries(Canvas canvas, GraphViewData[] values, float graphwidth, float graphheight,
			float border, double minX, double minY, double diffX, double diffY,
			float horstart, GraphViewSeriesStyle style) {


        float colwidth = (graphwidth - (2 * border)) / values.length;

        paint.setStrokeWidth(style.thickness);
        paint.setColor(style.color);

        // draw data
        for (int i = 0; i < values.length; i++) {
            float valY = (float) (values[i].valueY - minY);
            float ratY = (float) (valY / diffY);
            float y = graphheight * ratY;

            // hook for value dependent color
            if (style.getValueDependentColor() != null) {
                paint.setColor(style.getValueDependentColor().get(values[i]));
            }

            canvas.drawRect((i * colwidth) + horstart, (border - y) + graphheight, ((i * colwidth) + horstart) + (colwidth - 1), graphheight + border - 1, paint);
        }
	}
}
