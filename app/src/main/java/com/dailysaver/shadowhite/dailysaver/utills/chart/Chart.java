package com.dailysaver.shadowhite.dailysaver.utills.chart;

import android.content.Context;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.renderer.DataRenderer;
import java.util.ArrayList;

public class Chart {
    private Context context;
    private BarChart barChart;
    private PieChart pieChart;

    public Chart(Context context) {
        this.context = context;
    }

    public void setBarChart(BarChart barChart) {
        this.barChart = barChart;
    }

    public void setPieChart(PieChart pieChart) {
        this.pieChart = pieChart;
    }

    public void buildPieChart(String centerText, float centerTextSize, float entryLabelTextSize, int durationMillisX, int durationMillisY, PieData pieData, int entryLabelColor, int centerTextColor){
        pieChart.setEntryLabelColor(context.getResources().getColor(entryLabelColor));
        pieChart.setCenterText(centerText);
        pieChart.setCenterTextColor(context.getResources().getColor(centerTextColor));
        pieChart.setCenterTextSize(centerTextSize);
        pieChart.setEntryLabelTextSize(entryLabelTextSize);
        pieChart.animateXY(durationMillisX, durationMillisY);
        pieChart.getDescription().setEnabled(false);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    public void buildBarChart(boolean isBarChart , BarData barData, int durationMillisX, int durationMillisY, int visibleXRangeMaximum, float barWidth, float barSpace, float groupSpace, int barRadius){
        //remove extra space from bottom if value is zero
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisRight().setAxisMinimum(0f);
        //end
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.setData(barData);
        barChart.setVisibleXRangeMaximum(visibleXRangeMaximum);
        barChart.resetViewPortOffsets();
        barChart.animateXY(durationMillisX, durationMillisY);
        barChart.setData(barData);
        barChart.getData().setHighlightEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setRenderer(getGroupedBarRenderer(barRadius));
        barChart.setFitBars(true);
        barChart.setDescription(null);
        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);
        barChart.getBarData().setBarWidth(barWidth);
        if (isBarChart){
            barChart.groupBars(0, groupSpace, barSpace);
        }
        barChart.invalidate();
    }

    //region data render
    private DataRenderer getGroupedBarRenderer(int radius) {
        CustomBarChartRenderer customBarChartRenderer = new CustomBarChartRenderer(barChart, barChart.getAnimator(), barChart.getViewPortHandler());
        customBarChartRenderer.setRadius(radius);
        return customBarChartRenderer;
    }
    //endregion

    public void setLegendForGroupedBarChart() {
        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(20f);
        l.setXOffset(0f);
        l.setTextSize(8f);
    }

    public void getLegendForPieChart() {
        Legend l = pieChart.getLegend(); // get legend of pie
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER); //vertical alignment for legend
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); //horizontal alignment for legend
        l.setOrientation(Legend.LegendOrientation.VERTICAL); //orientation for legend
        l.setDrawInside(false); //if legend should be drawn inside or not
    }

    public void setAxisForBarChart(boolean isGroupedBarChart, int spaceTop, ArrayList xLabels , int labelCount, float granularity, float mSpaceMax, float mSpaceMin, float axisMax, float axisMin) {
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(granularity);
        xAxis.setSpaceMax(mSpaceMax);
        xAxis.setSpaceMin(mSpaceMin);
        xAxis.setAxisMaximum(axisMax);
        xAxis.setAxisMinimum(axisMin);
        xAxis.setLabelCount(labelCount);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
        xAxis.setAvoidFirstLastClipping(false);

        if (isGroupedBarChart) {
            barChart.getAxisRight().setEnabled(false);
            xAxis.setCenterAxisLabels(true);
            YAxis leftAxis = barChart.getAxisLeft();
            leftAxis.setSpaceTop(spaceTop);
        }
        else xAxis.setCenterAxisLabels(false);

    }
}
