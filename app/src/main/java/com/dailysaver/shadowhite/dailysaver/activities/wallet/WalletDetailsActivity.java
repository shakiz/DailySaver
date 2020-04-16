package com.dailysaver.shadowhite.dailysaver.activities.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.expensewallet.AddNewRecordActivity;
import com.dailysaver.shadowhite.dailysaver.activities.dashboard.DashboardActivity;
import com.dailysaver.shadowhite.dailysaver.activities.records.RecordsActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.monthlyexpense.MonthlyExpenseAdapter;
import com.dailysaver.shadowhite.dailysaver.models.expense.Expense;
import com.dailysaver.shadowhite.dailysaver.models.wallet.Wallet;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.util.ArrayList;
import in.codeshuffle.typewriterview.TypeWriterView;

public class WalletDetailsActivity extends AppCompatActivity {
    private CoordinatorLayout mainLayout;
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
    private ImageView filter;
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout layoutBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_details);

        init();

        getId();

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
        TotalCost = findViewById(R.id.TotalCost);
        ExpiresOn = findViewById(R.id.ExpiresOn);
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.home_layout);
        filter = findViewById(R.id.filter);
        layoutBottomSheet = findViewById(R.id.layoutBottomSheet);
        ux = new UX(this, mainLayout);
        tools = new Tools(this);
        databaseHelper = new DatabaseHelper(this);
    }

    //load wallet record
    private void loadRecord() {
        Wallet wallet = (Wallet) getIntent().getSerializableExtra("wallet");
        Amount.setText(""+wallet.getAmount());
        TotalCost.setText(""+databaseHelper.singleWalletTotalCost(walletId));
        CurrentBalance = findViewById(R.id.CurrentBalance);
        CurrentBalance.animateText(""+(wallet.getAmount()-databaseHelper.singleWalletTotalCost(walletId)));
        CurrentBalance.setDelay(0);
        ExpiresOn.setText(wallet.getExpiresOn());
    }

    //get wallet id from intent
    private void getId() {
        if (getIntent().getIntExtra("id",0) != 0){
            walletId = getIntent().getIntExtra("id",0);
            Log.v("walletID",""+walletId);
        }
    }

    //TODO not in used
    private void bindUIWithComponents() {
        setAdapter(databaseHelper.getAllExpenseItems(walletId));

        bottomSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

    }

    //set adapter for records for the specific wallet
    private void setAdapter(ArrayList<Expense> allExpenseItems) {
        if (allExpenseItems.size() <=0 ){
            noBudgetData.setVisibility(View.VISIBLE);
        }
        else {
            monthlyExpenseAdapter = new MonthlyExpenseAdapter(allExpenseItems, this,new MonthlyExpenseAdapter.onItemClick() {
                @Override
                public void itemClick(Expense expense) {
                    startActivity(new Intent(WalletDetailsActivity.this, AddNewRecordActivity.class).putExtra("expense", expense).putExtra("from","details"));
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
