package com.example.example;

import android.app.Activity;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;


public class MyActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        LinearLayout layout = (LinearLayout)findViewById(R.id.layout);

        GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
                new GraphViewData(1, 2.0d) ,
                new GraphViewData(2, 1.5d) ,
                new GraphViewData(3, 2.5d) ,
                new GraphViewData(4, 1.0d)
        });

        GraphViewSeries exampleSeries2 = new GraphViewSeries(
                "White Dotted",
                new LineGraphView.LineGraphViewSeriesStyle(0xFFFFFFFF,
                        3,
                        LineGraphView.LineGraphViewSeriesStyle.LineGraphViewSeriesLineStyleDotted),
                new GraphViewData[] {
                        new GraphViewData(1, 2.0d) ,
                        new GraphViewData(2, 0.2d) ,
                        new GraphViewData(3, 0.2d) ,
                        new GraphViewData(4, 1.8d)
                });

        GraphViewSeries exampleSeries3 = new GraphViewSeries(
                "Red Dashed",
                new LineGraphView.LineGraphViewSeriesStyle(0xFFFF223D,
                        3,
                        LineGraphView.LineGraphViewSeriesStyle.LineGraphViewSeriesLineStyleDashed),
                new GraphViewData[] {
                        new GraphViewData(1, 1.0d) ,
                        new GraphViewData(2, 1.4d) ,
                        new GraphViewData(3, 1.1d) ,
                        new GraphViewData(4, 0.4d)
                });

        GraphViewSeries exampleSeries4 = new GraphViewSeries(
                "Green Dash Dot",
                new LineGraphView.LineGraphViewSeriesStyle(0xFF22FF1D,
                        3,
                        LineGraphView.LineGraphViewSeriesStyle.LineGraphViewSeriesLineStyleCustom),
                new GraphViewData[] {
                        new GraphViewData(1, 0.3d) ,
                        new GraphViewData(2, 0.34d) ,
                        new GraphViewData(3, 1.72d) ,
                        new GraphViewData(4, 2.13d)
                });
        LineGraphView.LineGraphViewSeriesStyle ls = (LineGraphView.LineGraphViewSeriesStyle)exampleSeries4.getStyle();
        ls.setCustomPathEffect(new DashPathEffect(new float[] {30, 14, 4, 14}, 0));

        GraphView graphView = new LineGraphView (
                this // context
                , "GraphViewDemo" // heading
        );

//        GraphViewSeries.GraphViewSeriesStyle
        graphView.addSeries(exampleSeries); // data
        graphView.addSeries(exampleSeries2); // data
        graphView.addSeries(exampleSeries3); // data
        graphView.addSeries(exampleSeries4); // data
//        ((LineGraphView)graphView)

        graphView.setShowLegend(true);

        // set gridview
        GraphViewStyle gvs = graphView.getGraphViewStyle();
        gvs.setGridStyle(GraphViewStyle.kGraphViewStyleGridStyleHorizontalGridOnly);
//        gvs.setGridStyle(GraphViewStyle.kGraphViewStyleGridStyleVerticalGridOnly);

        layout.addView(graphView);
    }
}
