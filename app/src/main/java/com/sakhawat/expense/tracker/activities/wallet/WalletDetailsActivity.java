package com.sakhawat.expense.tracker.activities.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sakhawat.expense.tracker.R;
import com.sakhawat.expense.tracker.activities.dashboard.DashboardActivity;
import com.sakhawat.expense.tracker.adapters.allrecords.AllRecordsAdapter;
import com.sakhawat.expense.tracker.models.record.Record;
import com.sakhawat.expense.tracker.models.wallet.Wallet;
import com.sakhawat.expense.tracker.utills.Tools;
import com.sakhawat.expense.tracker.utills.UX;
import com.sakhawat.expense.tracker.utills.dbhelper.DatabaseHelper;
import java.util.ArrayList;
import pl.droidsonroids.gif.GifImageView;

public class WalletDetailsActivity extends AppCompatActivity {
    private RelativeLayout mainLayout;
    private TextView TotalCost, Amount, WalletName, CostHeading, noBudgetData, CurrentBalance;
    private GifImageView noDataGif;
    private Toolbar toolbar;
    private UX ux;
    private String walletName = "";
    private Tools tools;
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private ImageView editButton;
    private Wallet wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_details);

        init();

        if (getIntent().getSerializableExtra("wallet") != null){
            loadRecord();
        }

        ux.setToolbar(toolbar,this, DashboardActivity.class,"","");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);
        }
        tools.setAnimation(mainLayout);

        bindUIWithComponents();
    }


    //will init all the components and new instances
    private void init() {
        recyclerView = findViewById(R.id.mRecyclerView);
        noBudgetData = findViewById(R.id.NoData);
        noDataGif = findViewById(R.id.NoDataGif);
        Amount = findViewById(R.id.Amount);
        WalletName = findViewById(R.id.WalletName);
        TotalCost = findViewById(R.id.TotalCost);
        CostHeading = findViewById(R.id.CostHeading);
        CurrentBalance = findViewById(R.id.CurrentBalance);
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.home_layout);
        editButton = findViewById(R.id.editIcon);
        ux = new UX(this, mainLayout);
        tools = new Tools(this);
        databaseHelper = DatabaseHelper.getHelper(this);
    }

    //load wallet record
    private void loadRecord() {
        wallet = (Wallet) getIntent().getSerializableExtra("wallet");
        walletName = wallet.getTitle();
        if (wallet.getWalletType().equals("Savings")){
            CostHeading.setText("Additional Savings");
            CurrentBalance.setText(""+(wallet.getAmount() + databaseHelper.singleWalletTotalCost(walletName)));
        }
        else{
            CurrentBalance.setText(""+(wallet.getAmount() - databaseHelper.singleWalletTotalCost(walletName)));
        }
        Amount.setText(""+wallet.getAmount());
        WalletName.setText(wallet.getTitle());
        TotalCost.setText(""+databaseHelper.singleWalletTotalCost(walletName));
    }

    private void bindUIWithComponents() {

        databaseHelper.getAllRecords(walletName).observe(this, new Observer<ArrayList<Record>>() {
            @Override
            public void onChanged(ArrayList<Record> records) {
                if (records != null){
                    if (records.size() > 0){
                        setAdapter(records);
                    }
                    else {
                        noBudgetData.setVisibility(View.VISIBLE);
                        noDataGif.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ux.showDialog(R.layout.dialog_update_confirmation, getResources().getString(R.string.update_wallet_information),new UX.onDialogOkListener() {
                    @Override
                    public void onClick() {
                        startActivity(new Intent(WalletDetailsActivity.this, AddNewWalletActivity.class).putExtra("from","walletDetails").putExtra("wallet",wallet));
                    }
                });
            }
        });
    }

    //set adapter for records for the specific wallet
    private void setAdapter(ArrayList<Record> allRecordItems) {
        AllRecordsAdapter allRecordsAdapter;
        if (allRecordItems.size() <=0 ){
            noBudgetData.setVisibility(View.VISIBLE);
            noDataGif.setVisibility(View.VISIBLE);
        }
        else {
            allRecordsAdapter = new AllRecordsAdapter(allRecordItems, this);
            allRecordsAdapter.onItemClickListener(new AllRecordsAdapter.onItemClick() {
                @Override
                public void itemClick(Record record) {
                    ux.showRecordDetailsDialog(R.layout.dialog_record_short_details, record, new UX.onDialogOkListener() {
                        @Override
                        public void onClick() {
                        }
                    });
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(allRecordsAdapter);
            allRecordsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WalletDetailsActivity.this, DashboardActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.push_up_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
