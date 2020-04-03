package com.dailysaver.shadowhite.dailysaver.activities.records;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.expensewallet.ExpenseActivity;
import com.dailysaver.shadowhite.dailysaver.activities.onboard.HomeActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.monthlyexpense.MonthlyExpenseAdapter;
import com.dailysaver.shadowhite.dailysaver.models.expense.Expense;
import com.dailysaver.shadowhite.dailysaver.utills.DataLoader;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {
    private MonthlyExpenseAdapter monthlyExpenseAdapter;
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
        ux.setToolbar(toolbar,RecordsActivity.this, HomeActivity.class);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);
        tools.setAnimation(mainLayout);

        bindUiWithComponents();
    }

    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        recyclerView = findViewById(R.id.mRecyclerView);
        noBudgetData = findViewById(R.id.NoDataBudget);
        mainLayout = findViewById(R.id.mainLayout);
        dataLoader = new DataLoader(this,mainLayout);
        tools = new Tools(this);
        ux = new UX(this, mainLayout);
    }

    private void bindUiWithComponents() {

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

    private void setBudgetAdapter(ArrayList<Expense> expenseList) {
        if (expenseList.size() <= 0 ){
            noBudgetData.setVisibility(View.VISIBLE);
        }
        else {
            monthlyExpenseAdapter = new MonthlyExpenseAdapter(expenseList, this,new MonthlyExpenseAdapter.onItemClick() {
                @Override
                public void itemClick(Expense expense) {
                    startActivity(new Intent(RecordsActivity.this, ExpenseActivity.class).putExtra("expense", expense));
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(monthlyExpenseAdapter);
            monthlyExpenseAdapter.notifyDataSetChanged();
        }

    }
}
