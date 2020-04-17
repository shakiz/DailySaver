package com.dailysaver.shadowhite.dailysaver.activities.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.dashboard.DashboardActivity;
import com.dailysaver.shadowhite.dailysaver.activities.records.RecordsActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.monthlyexpense.MonthlyExpenseAdapter;
import com.dailysaver.shadowhite.dailysaver.models.record.Record;
import com.dailysaver.shadowhite.dailysaver.models.wallet.Wallet;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import java.util.ArrayList;
import in.codeshuffle.typewriterview.TypeWriterView;

public class WalletDetailsActivity extends AppCompatActivity {
    private CoordinatorLayout mainLayout;
    private TextView TotalCost, Amount, WalletName, CostHeading;
    private TypeWriterView CurrentBalance;
    private Toolbar toolbar;
    private UX ux;
    private int walletId = 0;
    private String walletName = "";
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

        if (getIntent().getSerializableExtra("wallet") != null){
            loadRecord();
        }

        ux.setToolbar(toolbar,this, DashboardActivity.class,"","");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_white);
        tools.setAnimation(mainLayout);

        bindUIWithComponents();
    }


    //will init all the components and new instances
    private void init() {
        recyclerView = findViewById(R.id.mRecyclerView);
        noBudgetData = findViewById(R.id.NoDataBudget);
        Amount = findViewById(R.id.Amount);
        WalletName = findViewById(R.id.WalletName);
        TotalCost = findViewById(R.id.TotalCost);
        CostHeading = findViewById(R.id.CostHeading);
        CurrentBalance = findViewById(R.id.CurrentBalance);
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.home_layout);
        ux = new UX(this, mainLayout);
        tools = new Tools(this);
        databaseHelper = new DatabaseHelper(this);
    }

    //load wallet record
    private void loadRecord() {
        Wallet wallet = (Wallet) getIntent().getSerializableExtra("wallet");
        walletId = wallet.getId();
        walletName = wallet.getTitle();
        CurrentBalance.setWithMusic(false);
        CurrentBalance.setDelay(0);
        if (wallet.getWalletType().equals("Savings")){
            CostHeading.setText("Additional Savings");
            CurrentBalance.animateText(""+(wallet.getAmount() + databaseHelper.singleWalletTotalCost(walletName)));
        }
        else{
            CurrentBalance.animateText(""+(wallet.getAmount() - databaseHelper.singleWalletTotalCost(walletName)));
        }
        Amount.setText(""+wallet.getAmount());
        WalletName.setText(wallet.getTitle());
        TotalCost.setText(""+databaseHelper.singleWalletTotalCost(walletName));
    }

    private void bindUIWithComponents() {
        setAdapter(databaseHelper.getAllExpenseItems(walletName));
    }

    //set adapter for records for the specific wallet
    private void setAdapter(ArrayList<Record> allRecordItems) {
        if (allRecordItems.size() <=0 ){
            noBudgetData.setVisibility(View.VISIBLE);
        }
        else {
            monthlyExpenseAdapter = new MonthlyExpenseAdapter(allRecordItems, this,new MonthlyExpenseAdapter.onItemClick() {
                @Override
                public void itemClick(Record record) {

                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(monthlyExpenseAdapter);
            monthlyExpenseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WalletDetailsActivity.this, RecordsActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.push_up_out);
    }
}
