package com.dailysaver.shadowhite.dailysaver.activities.report;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.dashboard.DashboardActivity;
import com.dailysaver.shadowhite.dailysaver.utills.DataManager;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.dailysaver.shadowhite.dailysaver.utills.chart.Chart;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseReportActivity extends AppCompatActivity {
    private RelativeLayout mainLayout;
    private TextView TotalCost, TotalRemaining, TotalBalance;
    private Toolbar toolbar;
    private UX ux;
    private Tools tools;
    private BarChart barChart;
    private BarData barData;
    private DatabaseHelper databaseHelper;
    private DataManager dataManager;
    private Chart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_report);

        init();

        //Set toolbar
        ux.setToolbar(toolbar,this, DashboardActivity.class,"","");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);
        tools.setAnimation(mainLayout);

        bindUIWithComponents();
    }

    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.parent_container);
        barChart = findViewById(R.id.barChart);
        ux = new UX(this, mainLayout);
        TotalCost = findViewById(R.id.TotalCost);
        TotalBalance = findViewById(R.id.TotalBalance);
        TotalRemaining = findViewById(R.id.TotalRemaining);
        tools = new Tools(this);
        dataManager = new DataManager(this);
        databaseHelper = new DatabaseHelper(this);
        chart = new Chart(this);
    }

    private void bindUIWithComponents() {
        setBarChart();
        TotalBalance.setText(""+databaseHelper.getTotalWalletBalance());
        TotalCost.setText(""+databaseHelper.getAllCost());
        TotalRemaining.setText(""+(databaseHelper.getTotalWalletBalance() - databaseHelper.getAllCost()));
    }

    //make bar chart complete
    private void setBarChart() {
        chart.setBarChart(barChart);
        setBarData();
        chart.buildBarChart(false,barData,1000,1000,6,0.6f,0,0,24);
        chart.setAxisForBarChart(false,0, getXAxisValues(), 12, 1,0.5f, 0.5f,12,0);
    }
    //end

    // bind bar chart data bind
    private void setBarData() {
        BarDataSet set = new BarDataSet(getEntries(), "Cost per month");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        barData = new BarData(set);
        barData.setValueFormatter(new LargeValueFormatter());
    }
    //end

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
    //end

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ExpenseReportActivity.this, DashboardActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.push_up_out);
    }
}
