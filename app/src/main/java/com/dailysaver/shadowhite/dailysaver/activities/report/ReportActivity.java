package com.dailysaver.shadowhite.dailysaver.activities.report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.widget.RelativeLayout;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.onboard.HomeActivity;
import com.dailysaver.shadowhite.dailysaver.utills.DataManager;
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
    private BarData mData;
    private DatabaseHelper databaseHelper;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        init();

        //Set toolbar
        ux.setToolbar(toolbar,this, HomeActivity.class);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);
        tools.setAnimation(mainLayout);

        bindUIWithComponents();
    }

    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.parent_container);
        barChart = findViewById(R.id.barChart);
        ux = new UX(this, mainLayout);
        tools = new Tools(this);
        dataManager = new DataManager(this);
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
        customBarChartRenderer.setRadius(32);
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

        String[] monthNames = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        for (int start = 0; start < monthNames.length; start++) {
            entries.add(new BarEntry(start, databaseHelper.getCostOfMonth(monthNames[start])));
        }

        return entries;
    }
    //endregion

    //region xAxis labels
    private ArrayList getXAxisValues() {
        ArrayList<String> xLabels = dataManager.getMonthNameForLabel();
        return xLabels;
    }
    //end region

    //region make barChart
    private void makeBarChart(){
        //remove extra space from bottom if value is zero
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisRight().setAxisMinimum(0f);
        //end

        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getDescription().setPosition(10f,10f);
        barChart.setData(mData);
        barChart.getBarData().setBarWidth(0.6f);
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
