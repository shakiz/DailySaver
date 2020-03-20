package com.dailysaver.shadowhite.dailysaver.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.expensewallet.ExpenseActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.monthlyexpense.MonthlyExpenseAdapter;
import com.dailysaver.shadowhite.dailysaver.models.expense.Expense;
import java.util.ArrayList;

public class ExpenseFilterableFragment extends Fragment {
    private ArrayList<Expense> expenseList;
    private TextView NoData;
    private RecyclerView mRecyclerViewExpense;

    public void setExpense(ArrayList<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public ExpenseFilterableFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expense_filterable, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUI(view);
        bindUiWithComponents();
    }

    private void initUI(View view) {
        NoData = view.findViewById(R.id.NoData);
        mRecyclerViewExpense = view.findViewById(R.id.mRecyclerView);
    }

    private void bindUiWithComponents() {
        setRecyclerAdapter();
    }

    private void setRecyclerAdapter() {
        if (expenseList.size() > 0){
            MonthlyExpenseAdapter monthlyExpenseAdapter = new MonthlyExpenseAdapter(expenseList, new MonthlyExpenseAdapter.onItemClick() {
                @Override
                public void itemClick(Expense expense) {
                    getActivity().startActivity(new Intent(getActivity(), ExpenseActivity.class).putExtra("expense", expense));
                }
            });
            mRecyclerViewExpense.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerViewExpense.setAdapter(monthlyExpenseAdapter);
            monthlyExpenseAdapter.notifyDataSetChanged();
        }
        else{
            NoData.setVisibility(View.VISIBLE);
        }
    }
}