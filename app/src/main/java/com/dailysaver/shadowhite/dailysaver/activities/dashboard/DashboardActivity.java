package com.dailysaver.shadowhite.dailysaver.activities.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.amitshekhar.DebugDB;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.expensewallet.AddNewRecordActivity;
import com.dailysaver.shadowhite.dailysaver.activities.records.RecordsActivity;
import com.dailysaver.shadowhite.dailysaver.activities.report.ExpenseReportActivity;
import com.dailysaver.shadowhite.dailysaver.activities.wallet.AddNewWalletActivity;
import com.dailysaver.shadowhite.dailysaver.activities.wallet.WalletDetailsActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.menu.IconMenuAdapter;
import com.dailysaver.shadowhite.dailysaver.adapters.dashboardwallet.WalletDetailsSliderAdapter;
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
import com.google.android.material.navigation.NavigationView;
import com.skydoves.powermenu.CircularEffect;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
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
    private ArrayList yLabels1, yLabels2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();

        Log.v("db",""+DebugDB.getAddressLog());

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);

        tools.setAnimation(mainLayout);
        bindUiWithComponents();
    }

    //bind components with UI
    private void bindUiWithComponents() {
        setNavDrawer();
        setCardSlider();
        setMenu();
        makeCategoryPieChart();
        makeExpenseVsSavingsBarChart();

        TotalExpense.setText(""+databaseHelper.getAllCostBasedOnRecord("Expense"));
        TotalSavings.setText(""+databaseHelper.getAllCostBasedOnRecord("Savings"));
    }

    private void setNavDrawer() {
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    //bind UI end

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handling navigation view item clicks based on their respective ids.
        int id = item.getItemId();
        switch (id) {
            //for item menu generate invoice
            case R.id.report:
                if (databaseHelper.getTotalWalletBalance() > 0) {
                    startActivity(new Intent(DashboardActivity.this, ExpenseReportActivity.class));
                } else {
                    ux.showDialog(R.layout.dialog_no_expense_wallet, "No expense wallet found", new UX.onDialogOkListener() {
                        @Override
                        public void onClick(View dialog, int id) {
                            startActivity(new Intent(DashboardActivity.this, AddNewWalletActivity.class).putExtra("from","main"));
                        }
                    });
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.records:
                startActivity(new Intent(DashboardActivity.this, RecordsActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //set the category pie chart
    private void makeCategoryPieChart() {
        chart.setPieChart(pieChart);
        setCategoryPieData();
        chart.getLegendForPieChart();
        chart.buildPieChart("Categories",12,6,2000,2000,pieData,R.color.md_white_1000,R.color.md_grey_800);
    }
    //pie chart build done

    //get category pie data and set it to PieDataSet
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
    //PieDataSet end

    //will get pie data from database
    private List<PieEntry> getPieData() {
        ArrayList categoryData = new ArrayList();
        String[] categoryLabels = new String[]{"Gift","Food","Transport","Energy","Education","Shopping","Fun","Family","Friends","Work"};
        float[] componentValues = new float[categoryLabels.length];
        float totalValue = 0;

        //getting individual component value from db
        for (int startIndex = 0; startIndex < categoryLabels.length; startIndex++) {
            componentValues[startIndex] = databaseHelper.getCategoryCount(categoryLabels[startIndex]) / 100;
        }

        //getting the tootal of of component value
        for (double value: componentValues) {
            totalValue += value;
        }

        //finally calculating the pie chart value from component and total value
        for (int startIndex = 0; startIndex < componentValues.length; startIndex++) {
            if (componentValues[startIndex] > 0) { categoryData.add(new PieEntry(chart.getSinglePieValue(componentValues[startIndex], chart.roundValueIntoTwoDecimal(totalValue)), categoryLabels[startIndex])); }
            else{ continue; }
        }

        return categoryData;
    }
    //pie data from database end

    //responsible for making the savings vs expense grouped bar chart
    private void makeExpenseVsSavingsBarChart() {
        chart.setBarChart(groupedBarChart);
        setSavingsVsExpenseBarData();
        chart.setLegendForGroupedBarChart();
        chart.setAxisForBarChart(true, 35, dataManager.getMonthNameForLabel(), 12, 1, 0.5f,0.5f, 12, 0);
        chart.buildBarChart(true,groupedBarData, 1000,1000, 5,barWidth,barSpace,groupSpace,18);
    }
    //end of making the savings vs expense grouped bar chart

    //get data for savings vs expense from database and set it to barDataSet
    private void setSavingsVsExpenseBarData(){
        BarDataSet dataSet1, dataSet2;
        getSavingsVsExpenseBarData();
        dataSet1 = new BarDataSet(yLabels1, "Savings");
        dataSet1.setColor(Color.RED);
        dataSet2 = new BarDataSet(yLabels2, "Expense");
        dataSet2.setColor(Color.BLUE);
        groupedBarData = new BarData(dataSet1, dataSet2);
        dataSet1.setColor(getResources().getColor(R.color.md_green_400));
        dataSet2.setColor(getResources().getColor(R.color.md_red_400));
        groupedBarData.setValueFormatter(new LargeValueFormatter());
    }
    //barDataSet end

    //get expense vs savings grouped bar chart from database
    private void getSavingsVsExpenseBarData() {
        String[] monthNames = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        yLabels1 = new ArrayList();
        yLabels2 = new ArrayList();

        for (int startIndex = 0; startIndex < monthNames.length; startIndex++) {
            yLabels1.add(new BarEntry(startIndex,databaseHelper.getCostOfMonthWithRecordType(monthNames[startIndex],"Savings")));
            yLabels2.add(new BarEntry(startIndex,databaseHelper.getCostOfMonthWithRecordType(monthNames[startIndex],"Expense")));
        }
    }
    // grouped bar chart from database end

    //setting the pop up menu
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
    //menu set up end

    //popup menu click listener
    private OnMenuItemClickListener<IconPowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<IconPowerMenuItem>() {
        @Override
        public void onItemClick(int position, IconPowerMenuItem item) {
            if (position==0){
                //check for any wallet exist or not
                if (databaseHelper.getAllWalletItems().size() == 0){
                    ux.showDialog(R.layout.dialog_no_wallet, "No wallet found", new UX.onDialogOkListener() {
                        @Override
                        public void onClick(View dialog, int id) {
                            startActivity(new Intent(DashboardActivity.this, AddNewWalletActivity.class));
                        }
                    });
                }
                else{
                    startActivity(new Intent(DashboardActivity.this, AddNewRecordActivity.class).putExtra("from","main"));
                }
            }
            else if (position==1) startActivity(new Intent(DashboardActivity.this, AddNewWalletActivity.class).putExtra("from","main"));
            powerMenu.dismiss();
        }
    };
    //menu click listener end

    //responsible for initiating all the components
    private void init() {
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawerLayout);
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
    //initiating done

    //set up the card slider for wallet dashboard
    private void setCardSlider() {
        dataLoader.setOnWalletItemsCompleted(new DataLoader.onWalletItemsCompleted() {
            @Override
            public void onComplete(ArrayList<Wallet> walletList) {
                if (walletList != null){
                    if (walletList.size() != 0){
                        setWalletAdapter(walletList);
                    }
                    else{
                        sliderView.setVisibility(View.GONE);
                        noWalletData.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
    //wallet dashboard setup done

    //set up the card slider for wallet dashboard data
    private void setWalletAdapter(ArrayList<Wallet> walletList) {
        sliderView.setSliderAdapter(new WalletDetailsSliderAdapter(walletList, this, new WalletDetailsSliderAdapter.onItemClick() {
            @Override
            public void itemClick(Wallet wallet, int id) {
                startActivity(new Intent(DashboardActivity.this, WalletDetailsActivity.class).putExtra("wallet",wallet));
            }
        }));
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorPadding(8);
    }
    //wallet dashboard data setup done

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle != null && toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            tools.exitApp();
        }
    }
}

