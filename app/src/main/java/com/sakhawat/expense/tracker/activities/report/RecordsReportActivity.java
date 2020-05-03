package com.sakhawat.expense.tracker.activities.report;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.sakhawat.expense.tracker.R;
import com.sakhawat.expense.tracker.activities.dashboard.DashboardActivity;
import com.sakhawat.expense.tracker.utills.DataManager;
import com.sakhawat.expense.tracker.utills.Tools;
import com.sakhawat.expense.tracker.utills.UX;
import com.sakhawat.expense.tracker.utills.chart.Chart;
import com.sakhawat.expense.tracker.utills.dbhelper.DatabaseHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.List;

public class RecordsReportActivity extends AppCompatActivity {
    private RelativeLayout mainLayout;
    private TextView TotalSavings, TotalExpense;
    private Toolbar toolbar;
    private UX ux;
    private Tools tools;
    private BarChart expenseBarChart, savingsBarChart;
    private BarData expenseBarData, savingsBarData;
    private DatabaseHelper databaseHelper;
    private DataManager dataManager;
    private Chart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_report);

        init();

        //Set toolbar
        ux.setToolbar(toolbar,this, DashboardActivity.class,"","");
        if (getActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);
        }
        tools.setAnimation(mainLayout);

        // all the user interactions
        bindUIWithComponents();
    }

    //will init all the components and new instances
    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.parent_container);
        expenseBarChart = findViewById(R.id.ExpenseBarChart);
        savingsBarChart = findViewById(R.id.SavingsBarChart);
        ux = new UX(this, mainLayout);
        TotalSavings = findViewById(R.id.TotalSavings);
        TotalExpense = findViewById(R.id.TotalExpense);
        tools = new Tools(this);
        dataManager = new DataManager(this);
        databaseHelper = DatabaseHelper.getHelper(this);
        chart = new Chart(this);
    }

    private void bindUIWithComponents() {
        setExpenseBarChart();
        setSavingsBarChart();
        TotalExpense.setText(""+databaseHelper.getAllCostBasedOnRecord("Expense"));
        TotalSavings.setText(""+databaseHelper.getAllCostBasedOnRecord("Savings"));
    }

    private void setSavingsBarChart() {
        chart.setBarChart(savingsBarChart);
        setSavingsBarData();
        if (getSavingsEntries().size() > 0) {
            chart.buildBarChart(false,savingsBarData,800,800,5,0.6f,0,0,24);
        }
        chart.setAxisForBarChart(false,0, getXAxisValues(), 12, 1,0.5f, 0.5f,12,0);
    }

    //make bar chart complete
    private void setExpenseBarChart() {
        chart.setBarChart(expenseBarChart);
        setExpenseBarData();
        if (getExpenseEntries().size() > 0) {
            chart.buildBarChart(false,expenseBarData,800,800,5,0.6f,0,0,24);
        }
        chart.setAxisForBarChart(false,0, getXAxisValues(), 12, 1,0.5f, 0.5f,12,0);
    }
    //end

    // bind bar chart data bind
    private void setExpenseBarData() {
        BarDataSet set = new BarDataSet(getExpenseEntries(), "Cost per month");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        expenseBarData = new BarData(set);
        expenseBarData.setValueFormatter(new LargeValueFormatter());
    }
    //end

    // bind bar chart data bind
    private void setSavingsBarData() {
        BarDataSet set = new BarDataSet(getSavingsEntries(), "Savings per month");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        savingsBarData = new BarData(set);
        savingsBarData.setValueFormatter(new LargeValueFormatter());
    }
    //end

    //region data for barChart
    private List<BarEntry> getExpenseEntries() {
        List<BarEntry> entries = new ArrayList<>();

        String[] monthNames = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        for (int start = 0; start < monthNames.length; start++) {
            entries.add(new BarEntry(start, databaseHelper.getCostOfMonthWithRecordType(monthNames[start],"Expense")));
        }
        return entries;
    }
    //endregion

    //region data for barChart
    private List<BarEntry> getSavingsEntries() {
        List<BarEntry> entries = new ArrayList<>();

        String[] monthNames = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        for (int start = 0; start < monthNames.length; start++) {
            entries.add(new BarEntry(start, databaseHelper.getCostOfMonthWithRecordType(monthNames[start],"Savings")));
        }
        return entries;
    }
    //endregion

    //region xAxis labels
    private ArrayList getXAxisValues() {
        return dataManager.getMonthNameForLabel();
    }
    //end

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RecordsReportActivity.this, DashboardActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.push_up_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
        tools = null;
        ux = null;
        dataManager = null;
        chart = null;
    }
}
