package com.sakhawat.expense.tracker.activities.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import androidx.lifecycle.Observer;
import com.sakhawat.expense.tracker.R;
import com.sakhawat.expense.tracker.activities.newrecord.AddNewRecordActivity;
import com.sakhawat.expense.tracker.activities.records.RecordsActivity;
import com.sakhawat.expense.tracker.activities.report.RecordsReportActivity;
import com.sakhawat.expense.tracker.activities.wallet.AddNewWalletActivity;
import com.sakhawat.expense.tracker.activities.wallet.WalletDetailsActivity;
import com.sakhawat.expense.tracker.adapters.dashboardwallet.WalletDetailsSliderAdapter;
import com.sakhawat.expense.tracker.adapters.menu.IconMenuAdapter;
import com.sakhawat.expense.tracker.models.menu.IconPowerMenuItem;
import com.sakhawat.expense.tracker.models.wallet.Wallet;
import com.sakhawat.expense.tracker.utills.DataManager;
import com.sakhawat.expense.tracker.utills.Tools;
import com.sakhawat.expense.tracker.utills.UX;
import com.sakhawat.expense.tracker.utills.chart.Chart;
import com.sakhawat.expense.tracker.utills.dbhelper.DatabaseHelper;
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
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import java.util.ArrayList;
import java.util.List;
import pl.droidsonroids.gif.GifImageView;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView noWalletData, TotalExpense, TotalSavings;
    private GifImageView noDataGif;
    private RelativeLayout mainLayout;
    private Toolbar toolbar;
    private FloatingActionButton addButton;
    private CustomPowerMenu powerMenu;
    private PieChart pieChart;
    private BarChart groupedBarChart;
    private Tools tools;
    private UX ux;
    private PieData pieData;
    private BarData groupedBarData;
    private DatabaseHelper databaseHelper;
    private final float barWidth = 0.3f ,barSpace = 0.1f ,groupSpace = 0.2f;
    private DataManager dataManager;
    private Chart chart;
    private ArrayList<BarEntry> yLabels1, yLabels2;
    private DiscreteScrollView itemPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();

        setSupportActionBar(toolbar);
        if (getActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);
        }

        tools.setAnimation(mainLayout);
        bindUiWithComponents();
    }

    //responsible for initiating all the components
    private void init() {
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.home_layout);
        itemPicker = findViewById(R.id.wallet_slider);
        TotalExpense = findViewById(R.id.TotalExpense);
        TotalSavings = findViewById(R.id.TotalSavings);
        pieChart = findViewById(R.id.pieChart);
        groupedBarChart = findViewById(R.id.GroupedBarChart);
        addButton = findViewById(R.id.add);
        noWalletData = findViewById(R.id.NoData);
        noDataGif = findViewById(R.id.NoDataGif);
        tools = new Tools(this);
        dataManager = new DataManager(this);
        databaseHelper = new DatabaseHelper(this);
        ux = new UX(this, mainLayout);
        chart = new Chart(this);
    }
    //initiating done

    //bind components with UI
    private void bindUiWithComponents() {
        //set the navigation drawer
        setNavDrawer();
        //set the wallet card slider
        setCardSlider();
        //set the popUp menu
        setMenu();
        //make teh category pie chart for savings/expense structure
        makeCategoryPieChart();
        //make the bar chart for savings vs expense
        makeExpenseVsSavingsBarChart();

        //amount details for savings vs expense bar chart
        TotalExpense.setText(""+databaseHelper.getAllCostBasedOnRecord("Expense"));
        TotalSavings.setText(""+databaseHelper.getAllCostBasedOnRecord("Savings"));
    }
    //bind UI end

    //set the navigation drawer
    private void setNavDrawer() {
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    //nav drawer built

    //set the category pie chart
    private void makeCategoryPieChart() {
        chart.setPieChart(pieChart);
        setCategoryPieData();
        chart.getLegendForPieChart();
        if (getPieData().size() > 0) {
            chart.buildPieChart("Categories",12,6,800, 800,pieData,R.color.md_white_1000,R.color.md_grey_800);
        }
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
        ArrayList<PieEntry> categoryData = new ArrayList();
        String[] categoryLabels = new String[]{"Gift","Food","Transport","Energy","Education","Shopping","Fun","Family","Friends","Work"};
        float[] componentValues = new float[categoryLabels.length];
        float totalValue = 0;

        //getting individual component value from db
        for (int startIndex = 0; startIndex < categoryLabels.length; startIndex++) {
            componentValues[startIndex] = databaseHelper.getCategoryCount(categoryLabels[startIndex]) / 100;
        }

        //getting the total of of component value
        for (double value: componentValues) {
            totalValue += value;
        }

        //finally calculating the pie chart value from component and total value
        for (int startIndex = 0; startIndex < componentValues.length; startIndex++) {
            if (componentValues[startIndex] > 0) {
                categoryData.add(new PieEntry(chart.getSinglePieValue(componentValues[startIndex], chart.roundValueIntoTwoDecimal(totalValue)), categoryLabels[startIndex]));
            }
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
        chart.buildBarChart(true,groupedBarData, 800,800, 5,barWidth,barSpace,groupSpace,18);
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
                .setAnimation(MenuAnimation.SHOW_UP_CENTER)
                .setMenuRadius(16f) // sets the corner radius.
                .setMenuShadow(16f) // sets the shadow.
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
                if (databaseHelper.getWalletCount() == 0){
                    ux.showDialog(R.layout.dialog_no_wallet, "No wallet found", new UX.onDialogOkListener() {
                        @Override
                        public void onClick(View dialog, int id) {
                            startActivity(new Intent(DashboardActivity.this, AddNewWalletActivity.class).putExtra("from","main"));
                        }
                    });
                }
                else{ startActivity(new Intent(DashboardActivity.this, AddNewRecordActivity.class).putExtra("from","main")); }
            }
            else if (position==1) startActivity(new Intent(DashboardActivity.this, AddNewWalletActivity.class).putExtra("from","main"));
            powerMenu.dismiss();
        }
    };
    //menu click listener end

    //set up the card slider for wallet dashboard
    private void setCardSlider() {
        databaseHelper.getAllWalletItems().observe(this, new Observer<ArrayList<Wallet>>() {
            @Override
            public void onChanged(ArrayList<Wallet> wallets) {
                if (wallets != null){
                    if (wallets.size() != 0){ setWalletAdapter(wallets); }
                    else{
                        noWalletData.setVisibility(View.VISIBLE);
                        noDataGif.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
    //wallet dashboard setup done

    //set up the card slider for wallet dashboard data
    private void setWalletAdapter(ArrayList<Wallet> walletList) {
        WalletDetailsSliderAdapter sliderAdapter = new WalletDetailsSliderAdapter(walletList, this);
        itemPicker.setAdapter(sliderAdapter);
        sliderAdapter.onItemClickListener(new WalletDetailsSliderAdapter.onItemClick() {
            @Override
            public void itemClick(Wallet wallet, int id) {
                startActivity(new Intent(DashboardActivity.this, WalletDetailsActivity.class).putExtra("wallet",wallet));
            }
        });
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());
        itemPicker.setSlideOnFling(false);
        itemPicker.scrollToPosition(0);
        itemPicker.setOverScrollEnabled(false);
    }

    //wallet dashboard data setup done
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handling navigation view item clicks based on their respective ids.
        int id = item.getItemId();
        switch (id) {
            //for item menu generate invoice
            case R.id.report:
                if (databaseHelper.getTotalWalletBalance() > 0) {
                    startActivity(new Intent(DashboardActivity.this, RecordsReportActivity.class));
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
            case R.id.note:
                ux.showDialog(R.layout.dialog_coming_soon, "Coming Soon ......", new UX.onDialogOkListener() {
                    @Override
                    public void onClick(View dialog, int id) {
                    }
                });
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.about:
                ux.showDialog(R.layout.about_developer, "About Developer", new UX.onDialogOkListener() {
                    @Override
                    public void onClick(View dialog, int id) {

                    }
                });
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.rating:
                tools.rateApp();
                drawerLayout.closeDrawer(GravityCompat.START);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper = null;
        tools = null;
        ux = null;
        dataManager = null;
        chart = null;
    }
}

