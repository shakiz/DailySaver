package com.dailysaver.shadowhite.dailysaver.activities.report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.onboard.HomeActivity;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private RelativeLayout mainLayout;
    private Toolbar toolbar;
    private UX ux;
    private Tools tools;
    private BarChart barChart;

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
    }

    private void bindUIWithComponents() {
        setBarChart();
    }

    private void setBarChart() {
        BarDataSet dataSet = new BarDataSet(dataSet(), "Cost per month");
        dataSet.setColor(Color.GRAY);
        dataSet.setDrawValues(true);
        BarData barData = new BarData();
        barData.addDataSet(dataSet);

        barChart.setData(barData);
        barChart.setVisibleXRangeMaximum(6);
        barChart.resetViewPortOffsets();
        barChart.moveViewToX(7);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(getXLabels()));
        barChart.getXAxis().setCenterAxisLabels(true);
        barChart.animateY(500);
        barChart.setFitBars(true);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.getDescription().setEnabled(false);
    }

    private List<BarEntry> dataSet() {
        ArrayList<BarEntry> yValues = new ArrayList<>();
        //TODO add data of cost per day from database
        yValues.add(new BarEntry(1,1800));
        yValues.add(new BarEntry(2,2800));
        yValues.add(new BarEntry(3,3800));
        yValues.add(new BarEntry(4,1500));
        yValues.add(new BarEntry(5,1600));
        yValues.add(new BarEntry(6,1300));
        yValues.add(new BarEntry(7,2300));
        yValues.add(new BarEntry(8,2600));
        yValues.add(new BarEntry(9,3200));
        yValues.add(new BarEntry(10,2200));
        yValues.add(new BarEntry(11,1900));
        yValues.add(new BarEntry(12,600));
        return yValues;
    }

    private ArrayList<String> getXLabels() {
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
}
