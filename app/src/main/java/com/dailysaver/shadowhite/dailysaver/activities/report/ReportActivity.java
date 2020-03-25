package com.dailysaver.shadowhite.dailysaver.activities.report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.widget.RelativeLayout;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.onboard.HomeActivity;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.dailysaver.shadowhite.dailysaver.utills.bar_chart.CustomBarChartRenderer;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private RelativeLayout mainLayout;
    private Toolbar toolbar;
    private UX ux;
    private Tools tools;
    private BarChart barChart;
    private float barWidth = 0.8f;
    private BarData mData;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initUI();

        //Set toolbar
        ux.setToolbar(toolbar,this, HomeActivity.class);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);
        tools.setAnimation(mainLayout);

        bindUIWithComponents();
    }

    private void initUI() {
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.parent_container);
        barChart = findViewById(R.id.barChart);
        ux = new UX(this);
        tools = new Tools(this);
        databaseHelper = new DatabaseHelper(this);
    }

    private void bindUIWithComponents() {
        setBarChart();
    }

    private void setBarChart() {

        // bind up X-axis properties
        setXAxis();

        // bind bar chart data bind
        setData();

        //region make barChart
        makeBarChart();
    }

    //region data render
    private DataRenderer getRenderer() {
        CustomBarChartRenderer customBarChartRenderer = new CustomBarChartRenderer(barChart, barChart.getAnimator(), barChart.getViewPortHandler());
        customBarChartRenderer.setRadius(40);
        return customBarChartRenderer;
    }
    //endregion

    // bind bar chart data bind
    private void setData() {
        BarDataSet set = new BarDataSet(getEntries(), "Cost per month");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        mData = new BarData(set);
        mData.setValueFormatter(new LargeValueFormatter());
    }
    //endregion

    //region set xAxis properties
    private void setXAxis() {
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setSpaceMax(0.5f);
        xAxis.setSpaceMin(0.5f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getXAxisValues()));
        xAxis.setLabelCount(12);
    }
    //endregion

    //region data for barChart
    private List<BarEntry> getEntries() {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 1100));
        entries.add(new BarEntry(1, 1200));
        entries.add(new BarEntry(2, 1300));
        entries.add(new BarEntry(3, 700));
        entries.add(new BarEntry(4, 470));
        entries.add(new BarEntry(5, 900));
        entries.add(new BarEntry(6, 400));
        entries.add(new BarEntry(7, 1000));
        entries.add(new BarEntry(8, 1400));
        entries.add(new BarEntry(9, 2000));
        entries.add(new BarEntry(10, 900));
        entries.add(new BarEntry(11, 1100));
        return entries;
    }
    //endregion

    //region xAxis labels
    private ArrayList getXAxisValues() {
        ArrayList<String> xLabels = new ArrayList<>();
        xLabels.add("Jan");
        xLabels.add("Feb");
        xLabels.add("Mar");
        xLabels.add("Apr");
        xLabels.add("May");
        xLabels.add("Jun");
        xLabels.add("Jul");
        xLabels.add("Aug");
        xLabels.add("Sep");
        xLabels.add("Oct");
        xLabels.add("Nov");
        xLabels.add("Dec");

        return xLabels;
    }
    //end region

    //region make barChart
    private void makeBarChart(){
        barChart.getDescription().setEnabled(false);
        barChart.getDescription().setPosition(10f,10f);
        barChart.setData(mData);barChart.getBarData().setBarWidth(barWidth);
        barChart.setVisibleXRangeMaximum(6);
        barChart.resetViewPortOffsets();
        barChart.animateXY(1000, 1000);
        barChart.setData(mData);
        barChart.getData().setHighlightEnabled(true);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setRenderer(getRenderer());
        barChart.setFitBars(true);
        barChart.invalidate();
    }
    //endregion
}
