package com.dailysaver.shadowhite.dailysaver.activities.records;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.expensewallet.AddNewRecordActivity;
import com.dailysaver.shadowhite.dailysaver.activities.dashboard.DashboardActivity;
import com.dailysaver.shadowhite.dailysaver.activities.wallet.AddNewWalletActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.menu.IconMenuAdapter;
import com.dailysaver.shadowhite.dailysaver.adapters.monthlyexpense.MonthlyExpenseAdapter;
import com.dailysaver.shadowhite.dailysaver.models.expense.Expense;
import com.dailysaver.shadowhite.dailysaver.models.menu.IconPowerMenuItem;
import com.dailysaver.shadowhite.dailysaver.utills.DataLoader;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skydoves.powermenu.CircularEffect;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {
    private MonthlyExpenseAdapter monthlyExpenseAdapter;
    private FloatingActionButton addNew;
    private CustomPowerMenu powerMenu;
    private RecyclerView recyclerView;
    private TextView noBudgetData;
    private RelativeLayout mainLayout;
    private Toolbar toolbar;
    private DataLoader dataLoader;
    private Tools tools;
    private UX ux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        init();
        //Set toolbar
        ux.setToolbar(toolbar,RecordsActivity.this, DashboardActivity.class,"","");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);
        tools.setAnimation(mainLayout);

        bindUiWithComponents();
    }

    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        recyclerView = findViewById(R.id.mRecyclerView);
        noBudgetData = findViewById(R.id.NoDataBudget);
        addNew = findViewById(R.id.add);
        mainLayout = findViewById(R.id.mainLayout);
        dataLoader = new DataLoader(this,mainLayout);
        tools = new Tools(this);
        ux = new UX(this, mainLayout);
    }

    private void bindUiWithComponents() {
        setMenu();

        dataLoader.setOnBudgetItemsCompleted(new DataLoader.onBudgetItemsCompleted() {
            @Override
            public void onComplete(ArrayList<Expense> expenseList) {
                if (expenseList != null){
                    if (expenseList.size() != 0){
                        setBudgetAdapter(expenseList);
                    }
                }
            }
        });
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

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                powerMenu.showAsAnchorCenter(mainLayout);
            }
        });
    }

    private OnMenuItemClickListener<IconPowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<IconPowerMenuItem>() {
        @Override
        public void onItemClick(int position, IconPowerMenuItem item) {
            if (position==0)startActivity(new Intent(RecordsActivity.this, AddNewRecordActivity.class).putExtra("from","record"));
            else if (position==1) startActivity(new Intent(RecordsActivity.this, AddNewWalletActivity.class).putExtra("from","record"));
            powerMenu.dismiss();
        }
    };

    private void setBudgetAdapter(ArrayList<Expense> expenseList) {
        if (expenseList.size() <= 0 ){
            noBudgetData.setVisibility(View.VISIBLE);
        }
        else {
            monthlyExpenseAdapter = new MonthlyExpenseAdapter(expenseList, this,new MonthlyExpenseAdapter.onItemClick() {
                @Override
                public void itemClick(Expense expense) {
                    startActivity(new Intent(RecordsActivity.this, AddNewRecordActivity.class).putExtra("expense", expense).putExtra("from","record"));
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(monthlyExpenseAdapter);
            monthlyExpenseAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RecordsActivity.this, DashboardActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.push_up_out);
    }
}
