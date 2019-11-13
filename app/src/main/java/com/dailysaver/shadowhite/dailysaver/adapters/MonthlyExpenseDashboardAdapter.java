package com.dailysaver.shadowhite.dailysaver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.models.expense.ExpenseModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MonthlyExpenseDashboardAdapter extends RecyclerView.Adapter<MonthlyExpenseDashboardAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ExpenseModel> expenseModelData;

    public MonthlyExpenseDashboardAdapter(Context context, ArrayList<ExpenseModel> expenseModelData) {
        this.context = context;
        this.expenseModelData = expenseModelData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_layout_monthly_expense_dashboard,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpenseModel dashboardModel = expenseModelData.get(position);
        holder.Title.setText(dashboardModel.getTitle());
        holder.Type.setText(dashboardModel.getType());
        holder.Amount.setText(""+dashboardModel.getAmount());
        holder.ExpenseDate.setText(dashboardModel.getExpenseDate());
        setTypeIcon(holder.Icon,dashboardModel.getType());
    }

    private void setTypeIcon(CircleImageView icon, String type) {
        if (type.equals("Food")) icon.setImageResource(R.drawable.ic_food_icon);
        else if (type.equals("Transport")) icon.setImageResource(R.drawable.ic_transport);
        else if (type.equals("Electricity")) icon.setImageResource(R.drawable.ic_electricity);
        else if (type.equals("Education")) icon.setImageResource(R.drawable.ic_education);
        else if (type.equals("Shopping")) icon.setImageResource(R.drawable.ic_cshopping);
        else if (type.equals("Entertainment")) icon.setImageResource(R.drawable.ic_entertainment);
        else if (type.equals("Family")) icon.setImageResource(R.drawable.ic_family);
        else if (type.equals("Friends")) icon.setImageResource(R.drawable.ic_friends);
        else if (type.equals("Work")) icon.setImageResource(R.drawable.ic_work);
        else if (type.equals("Gift")) icon.setImageResource(R.drawable.ic_gift);
    }

    @Override
    public int getItemCount() {
        return expenseModelData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title,Type,Amount,ExpenseDate;
        CircleImageView Icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            Type = itemView.findViewById(R.id.Type);
            Amount = itemView.findViewById(R.id.Amount);
            Icon = itemView.findViewById(R.id.Icon);
            ExpenseDate = itemView.findViewById(R.id.ExpenseDate);
        }
    }
}