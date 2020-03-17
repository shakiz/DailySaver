package com.dailysaver.shadowhite.dailysaver.activities.wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.expensewallet.ExpenseActivity;
import com.dailysaver.shadowhite.dailysaver.activities.onboard.HomeActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.monthlyexpense.MonthlyExpenseAdapter;
import com.dailysaver.shadowhite.dailysaver.models.expense.Expense;
import com.dailysaver.shadowhite.dailysaver.models.wallet.Wallet;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import in.codeshuffle.typewriterview.TypeWriterView;

public class WalletDetailsActivity extends AppCompatActivity {
    private RelativeLayout mainLayout;
    private TextView TotalCost, Amount, ExpiresOn;
    private TypeWriterView CurrentBalance;
    private Toolbar toolbar;
    private UX ux;
    private int walletId = 0;
    private Tools tools;
    private RecyclerView recyclerView;
    private MonthlyExpenseAdapter monthlyExpenseAdapter;
    private DatabaseHelper databaseHelper;
    private TextView noBudgetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_details);

        init();

        getId();

        if (getIntent().getSerializableExtra("wallet") != null){
            loadRecord();
        }

        ux.setToolbar(toolbar,this,HomeActivity.class);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_white);
        tools.setAnimation(mainLayout);

        bindUIWithComponents();
    }

    private void loadRecord() {
        Wallet wallet = (Wallet) getIntent().getSerializableExtra("wallet");
        Amount.setText(""+wallet.getAmount());
        TotalCost.setText(""+databaseHelper.singleWalletTotalCost(walletId));
        CurrentBalance = findViewById(R.id.CurrentBalance);
        CurrentBalance.animateText(""+(wallet.getAmount()-databaseHelper.singleWalletTotalCost(walletId)));
        CurrentBalance.setDelay(0);
        ExpiresOn.setText(wallet.getExpiresOn());
    }

    private void getId() {
        if (getIntent().getIntExtra("id",0) != 0){
            walletId = getIntent().getIntExtra("id",0);
        }
    }

    private void init() {
        recyclerView = findViewById(R.id.mRecyclerView);
        noBudgetData = findViewById(R.id.NoDataBudget);
        Amount = findViewById(R.id.Amount);
        TotalCost = findViewById(R.id.TotalCost);
        ExpiresOn = findViewById(R.id.ExpiresOn);
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.home_layout);
        ux = new UX(this);
        tools = new Tools(this);
        databaseHelper = new DatabaseHelper(this);
    }

    private void bindUIWithComponents() {
        //getPieChart();
        setAdapter(databaseHelper.getAllExpenseItems(walletId));
    }

    private void setAdapter(ArrayList<Expense> allExpenseItems) {
        if (allExpenseItems.size() <=0 ){
            noBudgetData.setVisibility(View.VISIBLE);
        }
        else {
            monthlyExpenseAdapter = new MonthlyExpenseAdapter(allExpenseItems, new MonthlyExpenseAdapter.onItemClick() {
                @Override
                public void itemClick(Expense expense) {
                    startActivity(new Intent(WalletDetailsActivity.this, ExpenseActivity.class).putExtra("expense", expense));
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(monthlyExpenseAdapter);
            monthlyExpenseAdapter.notifyDataSetChanged();
        }
    }

    private void getPieChart() {
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WalletDetailsActivity.this,HomeActivity.class));
    }
}
