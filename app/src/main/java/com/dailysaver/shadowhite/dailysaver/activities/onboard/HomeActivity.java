package com.dailysaver.shadowhite.dailysaver.activities.onboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amitshekhar.DebugDB;
import com.dailysaver.shadowhite.dailysaver.activities.records.RecordsActivity;
import com.dailysaver.shadowhite.dailysaver.activities.report.ExpenseReportActivity;
import com.dailysaver.shadowhite.dailysaver.activities.wallet.WalletDetailsActivity;
import com.dailysaver.shadowhite.dailysaver.activities.expensewallet.AddNewRecordActivity;
import com.dailysaver.shadowhite.dailysaver.activities.wallet.AddNewWalletActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.wallet.WalletDetailsSliderAdapter;
import com.dailysaver.shadowhite.dailysaver.adapters.menu.IconMenuAdapter;
import com.dailysaver.shadowhite.dailysaver.models.wallet.Wallet;
import com.dailysaver.shadowhite.dailysaver.models.menu.IconPowerMenuItem;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.utills.DataLoader;
import com.dailysaver.shadowhite.dailysaver.utills.DataManager;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.dailysaver.shadowhite.dailysaver.utills.bar_chart.CustomBarChartRenderer;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skydoves.powermenu.CircularEffect;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView noWalletData, TotalExpense, TotalSavings;
    private RelativeLayout mainLayout;
    private Toolbar toolbar;
    private FloatingActionButton addButton;
    private CustomPowerMenu powerMenu;
    private SliderView sliderView;
    private PieChart pieChart;
    private BarChart groupedBarChart;
    private DataLoader dataLoader;
    private Tools tools;
    private UX ux;
    private PieData pieData;
    private BarData groupedBarData;
    private DatabaseHelper databaseHelper;
    float barWidth = 0.3f;
    float barSpace = 0.1f;
    float groupSpace = 0.2f;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        Log.v("db",""+DebugDB.getAddressLog());

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tools.exitApp();
            }
        });

        tools.setAnimation(mainLayout);
        bindUiWithComponents();
    }

    private void bindUiWithComponents() {
        setCardSlider();
        setMenu();
        setCategoryPieChart();
        setExpenseVsSavingsBarChart();

        TotalExpense.setText(""+databaseHelper.getAllCostBasedOnRecord("Expense"));
        TotalSavings.setText(""+databaseHelper.getAllCostBasedOnRecord("Savings"));
    }

    private void setCategoryPieChart() {
        setCategoryPieData();
        makeCategoryPieChart();
    }

    private void makeCategoryPieChart() {
        getLegendForPieChart();
        pieChart.setEntryLabelColor(getResources().getColor(R.color.md_white_1000));
        pieChart.setCenterText("Categories");
        pieChart.setCenterTextColor(getResources().getColor(R.color.md_grey_800));
        pieChart.setCenterTextSize(12f);
        pieChart.setEntryLabelTextSize(6f);
        pieChart.animateXY(2000, 2000);
        pieChart.getDescription().setEnabled(false);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void getLegendForPieChart() {
        Legend l = pieChart.getLegend(); // get legend of pie
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER); //vertical alignment for legend
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); //horizontal alignment for legend
        l.setOrientation(Legend.LegendOrientation.VERTICAL); //orientation for legend
        l.setDrawInside(false); //if legend should be drawn inside or not
    }

    private void setCategoryPieData() {
        PieDataSet dataSet = new PieDataSet(getPieData(), "");
        pieData = new PieData(dataSet);
        pieData.setValueTextColor(getResources().getColor(R.color.md_white_1000));
        pieData.setValueTextSize(10f);
        pieData.setValueFormatter(new PercentFormatter());
        dataSet.setColors(getResources().getColor(R.color.md_red_300), getResources().getColor(R.color.md_green_500),
                getResources().getColor(R.color.md_blue_500), getResources().getColor(R.color.md_grey_500),
                getResources().getColor(R.color.md_cyan_500), getResources().getColor(R.color.md_blue_grey_500),
                getResources().getColor(R.color.md_yellow_700), getResources().getColor(R.color.md_teal_500),
                getResources().getColor(R.color.md_light_green_500), getResources().getColor(R.color.md_lime_700));
    }

    private List<PieEntry> getPieData() {
        ArrayList categoryData = new ArrayList();

        categoryData.add(new PieEntry(15, "Gift"));
        categoryData.add(new PieEntry(15, "Food"));
        categoryData.add(new PieEntry(10, "Transport"));
        categoryData.add(new PieEntry(10, "Energy"));
        categoryData.add(new PieEntry(10, "Education"));
        categoryData.add(new PieEntry(8, "Shopping"));
        categoryData.add(new PieEntry(7, "Fun"));
        categoryData.add(new PieEntry(9, "Family"));
        categoryData.add(new PieEntry(9, "Friends"));
        categoryData.add(new PieEntry(10, "Work"));

        return categoryData;
    }

    private void setExpenseVsSavingsBarChart() {
        ArrayList xLabels = dataManager.getMonthNameForLabel();
        String[] monthNames = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        
        ArrayList yLabels1 = new ArrayList();
        ArrayList yLabels2 = new ArrayList();

        for (int startIndex = 0; startIndex < monthNames.length; startIndex++) {
            yLabels1.add(new BarEntry(startIndex,databaseHelper.getCostOfMonthWithRecordType(monthNames[startIndex],"Savings")));
            yLabels2.add(new BarEntry(startIndex,databaseHelper.getCostOfMonthWithRecordType(monthNames[startIndex],"Expense")));
        }

        BarDataSet dataSet1, dataSet2;
        dataSet1 = new BarDataSet(yLabels1, "Savings");
        dataSet1.setColor(Color.RED);
        dataSet2 = new BarDataSet(yLabels2, "Expense");
        dataSet2.setColor(Color.BLUE);
        groupedBarData = new BarData(dataSet1, dataSet2);
        dataSet1.setColor(getResources().getColor(R.color.md_green_400));
        dataSet2.setColor(getResources().getColor(R.color.md_red_400));
        groupedBarData.setValueFormatter(new LargeValueFormatter());
        
        makeGroupedBarChart();
        setLegendForGroupedBarChart();
        setXAndYAxisGroupedBarChart(xLabels);
    }

    //region data render
    private DataRenderer getGroupedBarRenderer() {
        CustomBarChartRenderer customBarChartRenderer = new CustomBarChartRenderer(groupedBarChart, groupedBarChart.getAnimator(), groupedBarChart.getViewPortHandler());
        customBarChartRenderer.setRadius(18);
        return customBarChartRenderer;
    }
    //endregion

    private void setXAndYAxisGroupedBarChart(ArrayList xLabels) {
        //X-axis
        XAxis xAxis = groupedBarChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(12f);
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        //Y-axis
        groupedBarChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = groupedBarChart.getAxisLeft();
        leftAxis.setSpaceTop(35f);
    }

    private void setLegendForGroupedBarChart() {
        Legend l = groupedBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(20f);
        l.setXOffset(0f);
        l.setTextSize(8f);
    }

    private void makeGroupedBarChart() {

        //remove extra space from bottom if value is zero
        groupedBarChart.getAxisLeft().setAxisMinimum(0f);
        groupedBarChart.getAxisRight().setAxisMinimum(0f);
        //end

        groupedBarChart.getAxisRight().setDrawLabels(false);
        groupedBarChart.getAxisRight().setDrawGridLines(false);
        groupedBarChart.getAxisLeft().setDrawGridLines(false);
        groupedBarChart.getXAxis().setDrawGridLines(false);
        groupedBarChart.setData(groupedBarData);
        groupedBarChart.setVisibleXRangeMaximum(5);
        groupedBarChart.resetViewPortOffsets();
        groupedBarChart.animateXY(1000, 1000);
        groupedBarChart.setData(groupedBarData);
        groupedBarChart.getData().setHighlightEnabled(false);
        groupedBarChart.setDoubleTapToZoomEnabled(false);
        groupedBarChart.setRenderer(getGroupedBarRenderer());
        groupedBarChart.setFitBars(true);
        groupedBarChart.setDescription(null);
        groupedBarChart.setPinchZoom(false);
        groupedBarChart.setScaleEnabled(false);
        groupedBarChart.setDrawBarShadow(false);
        groupedBarChart.setDrawGridBackground(false);
        groupedBarChart.getBarData().setBarWidth(barWidth);
        groupedBarChart.groupBars(0, groupSpace, barSpace);
        groupedBarChart.invalidate();
    }

    private void setMenu() {
        powerMenu =new CustomPowerMenu.Builder<>(this, new IconMenuAdapter())
                .addItem(new IconPowerMenuItem(ContextCompat.getDrawable(this, R.drawable.ic_new_record), getResources().getString(R.string.add_new_record)))
                .addItem(new IconPowerMenuItem(ContextCompat.getDrawable(this, R.drawable.ic_wallet), getResources().getString(R.string.add_new_wallet)))
                .setAnimation(MenuAnimation.SHOW_UP_CENTER) // Animation start point (TOP | LEFT).
                .setMenuRadius(15f) // sets the corner radius.
                .setMenuShadow(10f) // sets the shadow.
                .setWidth(800)
                .setCircularEffect(CircularEffect.INNER)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                powerMenu.showAsAnchorCenter(mainLayout);
            }
        });
    }

    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        sliderView = findViewById(R.id.Slider);
        mainLayout = findViewById(R.id.home_layout);
        TotalExpense = findViewById(R.id.TotalExpense);
        TotalSavings = findViewById(R.id.TotalSavings);
        pieChart = findViewById(R.id.pieChart);
        groupedBarChart = findViewById(R.id.GroupedBarChart);
        addButton = findViewById(R.id.add);
        noWalletData = findViewById(R.id.NoDataWallet);
        tools = new Tools(this);
        dataManager = new DataManager(this);
        dataLoader = new DataLoader(this, mainLayout);
        databaseHelper = new DatabaseHelper(this);
        ux = new UX(this, mainLayout);
    }

    private OnMenuItemClickListener<IconPowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<IconPowerMenuItem>() {
        @Override
        public void onItemClick(int position, IconPowerMenuItem item) {
            if (position==0)startActivity(new Intent(HomeActivity.this, AddNewRecordActivity.class).putExtra("from","main"));
            else if (position==1) startActivity(new Intent(HomeActivity.this, AddNewWalletActivity.class).putExtra("from","main"));
            powerMenu.dismiss();
        }
    };

    private void setCardSlider() {
        dataLoader.setOnWalletItemsCompleted(new DataLoader.onWalletItemsCompleted() {
            @Override
            public void onComplete(ArrayList<Wallet> walletList) {
                if (walletList != null){
                    if (walletList.size() != 0){
                        setWalletAdapter(walletList);
                    }
                }
            }
        });
    }

    private void setWalletAdapter(ArrayList<Wallet> walletList) {
        if (walletList.size() <= 0){
            sliderView.setVisibility(View.GONE);
            noWalletData.setVisibility(View.VISIBLE);
        }
        else {
            sliderView.setSliderAdapter(new WalletDetailsSliderAdapter(walletList, this, new WalletDetailsSliderAdapter.onItemClick() {
                @Override
                public void itemClick(Wallet wallet, int id) {
                    startActivity(new Intent(HomeActivity.this, WalletDetailsActivity.class).putExtra("id",id).putExtra("wallet",wallet));
                }
            }));
            sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            sliderView.setIndicatorPadding(8);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_item, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //for item menu generate invoice
            case R.id.report:
                if (databaseHelper.getTotalWalletBalance() > 0) {
                    startActivity(new Intent(HomeActivity.this, ExpenseReportActivity.class));
                } else {
                    ux.showDialog(R.layout.dialog_no_expense_wallet, "", new UX.onDialogOkListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(HomeActivity.this, AddNewWalletActivity.class).putExtra("from","main"));
                        }
                    });
                }
                return true;
            case R.id.records:
                startActivity(new Intent(HomeActivity.this, RecordsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        tools.exitApp();
    }
}

