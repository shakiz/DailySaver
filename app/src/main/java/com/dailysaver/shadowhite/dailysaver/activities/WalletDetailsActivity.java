package com.dailysaver.shadowhite.dailysaver.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.onboard.HomeActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class WalletDetailsActivity extends AppCompatActivity {
    private RelativeLayout mainLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_details);

        init();

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WalletDetailsActivity.this, HomeActivity.class));
            }
        });

        Animation a = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mainLayout.startAnimation(a);

        bindUIWithComponents();
    }

    private void bindUIWithComponents() {
        PieChart pieChart = findViewById(R.id.piechart);
        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new PieEntry(945f, 0));
        NoOfEmp.add(new PieEntry(1040f, 1));
        NoOfEmp.add(new PieEntry(1133f, 2));
        NoOfEmp.add(new PieEntry(1240f, 3));
        NoOfEmp.add(new PieEntry(1369f, 4));
        NoOfEmp.add(new PieEntry(1487f, 5));
        NoOfEmp.add(new PieEntry(1501f, 6));
        NoOfEmp.add(new PieEntry(1645f, 7));
        NoOfEmp.add(new PieEntry(1578f, 8));
        NoOfEmp.add(new PieEntry(1695f, 9));
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");

        ArrayList year = new ArrayList();
        year.add("2008");
        year.add("2009");
        year.add("2010");
        year.add("2011");
        year.add("2012");
        year.add("2013");
        year.add("2014");
        year.add("2015");
        year.add("2016");
        year.add("2017");
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(2000, 2000);
    }

    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.home_layout);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WalletDetailsActivity.this,HomeActivity.class));
    }
}
