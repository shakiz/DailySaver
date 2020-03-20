package com.dailysaver.shadowhite.dailysaver.activities.wallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.expensewallet.ExpenseActivity;
import com.dailysaver.shadowhite.dailysaver.activities.onboard.HomeActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.monthlyexpense.MonthlyExpenseAdapter;
import com.dailysaver.shadowhite.dailysaver.adapters.pager.ViewPagerAdapter;
import com.dailysaver.shadowhite.dailysaver.models.expense.Expense;
import com.dailysaver.shadowhite.dailysaver.models.wallet.Wallet;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import in.codeshuffle.typewriterview.TypeWriterView;
import me.relex.circleindicator.CircleIndicator;

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
    private CircleIndicator indicator;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Expense> expenseList;

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
        viewPager = findViewById(R.id.swipePager);
        indicator = findViewById(R.id.indicator);
        tabLayout = findViewById(R.id.sliding_tabs);
        noBudgetData = findViewById(R.id.NoDataBudget);
        Amount = findViewById(R.id.Amount);
        TotalCost = findViewById(R.id.TotalCost);
        ExpiresOn = findViewById(R.id.ExpiresOn);
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.home_layout);
        filter = findViewById(R.id.filter);
        layoutBottomSheet = findViewById(R.id.layoutBottomSheet);
        ux = new UX(this);
        tools = new Tools(this);
        databaseHelper = new DatabaseHelper(this);
        expenseList = new ArrayList<>();
    }

    private void bindUIWithComponents() {
        //getPieChart();
        setAdapter(databaseHelper.getAllExpenseItems(walletId));

        bottomSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        //setMonthAdapter();

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

        setViewpager();
    }

    private void setViewpager() {
        String[] tabTitles = new String[]{"Monthly","Weekly","Daily"};
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),tabTitles, expenseList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WalletDetailsActivity.this,HomeActivity.class));
    }
}
