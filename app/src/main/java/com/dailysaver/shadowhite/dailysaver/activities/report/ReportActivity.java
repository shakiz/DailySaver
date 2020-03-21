package com.dailysaver.shadowhite.dailysaver.activities.report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

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
        barChart.getDescription().setEnabled(false);
        getBarData();
        barChart.setFitBars(true);
    }

    private void getBarData() {
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            float value = (float) (Math.random()*100);
            yValues.add(new BarEntry(i,(int)value));
        }

        BarDataSet dataSet = new BarDataSet(yValues, "Date");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setDrawValues(true);
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.invalidate();
        barChart.animateY(500);
    }
}
