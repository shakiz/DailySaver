package com.dailysaver.shadowhite.dailysaver.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.onboard.HomeActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow);
        Animation a = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mainLayout.startAnimation(a);

        bindUIWithComponents();
    }

    private void bindUIWithComponents() {
        PieChart pieChart = findViewById(R.id.piechart);

        PieDataSet pieDataSet = new PieDataSet(getEntries(), "");
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setSliceSpace(5f);

        pieChart.animateXY(2000, 2000);
    }

    private ArrayList<PieEntry> getEntries() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(2f, 0));
        pieEntries.add(new PieEntry(4f, 1));
        pieEntries.add(new PieEntry(6f, 2));
        pieEntries.add(new PieEntry(8f, 3));
        pieEntries.add(new PieEntry(7f, 4));
        pieEntries.add(new PieEntry(3f, 5));
        return pieEntries;
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
