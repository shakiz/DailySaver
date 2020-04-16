package com.dailysaver.shadowhite.dailysaver.activities.dashboard;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.amitshekhar.DebugDB;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.expensewallet.AddNewRecordActivity;
import com.dailysaver.shadowhite.dailysaver.activities.records.RecordsActivity;
import com.dailysaver.shadowhite.dailysaver.activities.report.ExpenseReportActivity;
import com.dailysaver.shadowhite.dailysaver.activities.wallet.AddNewWalletActivity;
import com.dailysaver.shadowhite.dailysaver.activities.wallet.WalletDetailsActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.menu.IconMenuAdapter;
import com.dailysaver.shadowhite.dailysaver.adapters.wallet.WalletDetailsSliderAdapter;
import com.dailysaver.shadowhite.dailysaver.models.menu.IconPowerMenuItem;
import com.dailysaver.shadowhite.dailysaver.models.wallet.Wallet;
import com.dailysaver.shadowhite.dailysaver.utills.chart.Chart;
import com.dailysaver.shadowhite.dailysaver.utills.DataLoader;
import com.dailysaver.shadowhite.dailysaver.utills.DataManager;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
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

public class DashboardActivity extends AppCompatActivity {

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
    float barWidth = 0.3f ,barSpace = 0.1f ,groupSpace = 0.2f;
    private DataManager dataManager;
    private Chart chart;

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
        chart.setPieChart(pieChart);
        chart.getLegendForPieChart();
        chart.buildPieChart("Categories",12,6,2000,2000,pieData,R.color.md_white_1000,R.color.md_grey_800);
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
        String[] categoryLabels = new String[]{"Gift","Food","Transport","Energy","Education","Shopping","Fun","Family","Friends","Work"};
        float[] componentValues = new float[categoryLabels.length];
        float totalValue = 0;

        for (int startIndex = 0; startIndex < categoryLabels.length; startIndex++) {
            componentValues[startIndex] = databaseHelper.getCategoryCount(categoryLabels[startIndex]) / 100;
        }
        for (double value: componentValues) {
            totalValue += value;
        }

        for (int startIndex = 0; startIndex < componentValues.length; startIndex++) {
            if (componentValues[startIndex] > 0) {
                categoryData.add(new PieEntry(chart.getSinglePieValue(componentValues[startIndex], chart.roundValueIntoTwoDecimal(totalValue)), categoryLabels[startIndex]));
            }
            else{
                continue;
            }
        }

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
        
        makeExpenseVsSavingsBarChart(xLabels);
    }

    private void makeExpenseVsSavingsBarChart(ArrayList xLabels) {
        chart.setBarChart(groupedBarChart);
        chart.setLegendForGroupedBarChart();
        chart.setAxisForBarChart(true, 35, xLabels, 12, 1, 0.5f,0.5f, 12, 0);
        chart.buildBarChart(true,groupedBarData, 1000,1000, 5,barWidth,barSpace,groupSpace,18);
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
        chart = new Chart(this);
    }

    private OnMenuItemClickListener<IconPowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<IconPowerMenuItem>() {
        @Override
        public void onItemClick(int position, IconPowerMenuItem item) {
            if (position==0)startActivity(new Intent(DashboardActivity.this, AddNewRecordActivity.class).putExtra("from","main"));
            else if (position==1) startActivity(new Intent(DashboardActivity.this, AddNewWalletActivity.class).putExtra("from","main"));
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
                    startActivity(new Intent(DashboardActivity.this, WalletDetailsActivity.class).putExtra("id",id).putExtra("wallet",wallet));
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
                    startActivity(new Intent(DashboardActivity.this, ExpenseReportActivity.class));
                } else {
                    ux.showDialog(R.layout.dialog_no_expense_wallet, "", new UX.onDialogOkListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(DashboardActivity.this, AddNewWalletActivity.class).putExtra("from","main"));
                        }
                    });
                }
                return true;
            case R.id.records:
                startActivity(new Intent(DashboardActivity.this, RecordsActivity.class));
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

