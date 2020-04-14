package com.dailysaver.shadowhite.dailysaver.utills.chart;

import android.content.Context;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.renderer.DataRenderer;

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
        if (isBarChart){
            barChart.getBarData().setBarWidth(barWidth);
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
}
