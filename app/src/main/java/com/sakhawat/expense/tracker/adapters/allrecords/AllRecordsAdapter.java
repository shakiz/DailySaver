package com.sakhawat.expense.tracker.adapters.allrecords;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.sakhawat.expense.tracker.models.record.Record;
import com.sakhawat.expense.tracker.R;
import java.util.ArrayList;

public class AllRecordsAdapter extends RecyclerView.Adapter<AllRecordsAdapter.ViewHolder>{
    private ArrayList<Record> recordList;
    private onItemClick onItemClick;
    private Context context;

    public AllRecordsAdapter(ArrayList<Record> recordList, Context context, onItemClick onItemClick) {
        this.recordList = recordList;
        this.onItemClick = onItemClick;
        this.context = context;
    }

    public interface onItemClick{
        void itemClick(Record record);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_generic_expense,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Record record = recordList.get(position);
        holder.Category.setText(record.getCategory());
        holder.WalletName.setText(record.getWalletTitle());
        holder.Amount.setText(""+ record.getAmount());
        holder.ExpenseDate.setText(record.getExpenseDate());
        setTypeIcon(holder.Icon, record.getCategory());
        setIndicatorColor(record.getRecordType(),holder.IndicatorView);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.itemClick(record);
            }
        });
    }

    private void setIndicatorColor(String recordType, View indicatorView) {
        if (!recordType.equals(null)){
            if (recordType.equals("Expense")) indicatorView.setBackgroundColor(context.getResources().getColor(R.color.md_red_400));
            else if (recordType.equals("Savings")) indicatorView.setBackgroundColor(context.getResources().getColor(R.color.md_green_400));
        }
    }

    private void setTypeIcon(ImageView icon, String type) {
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
        return recordList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Category,WalletName,Amount,ExpenseDate;
        ImageView Icon;
        CardView view;
        View IndicatorView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Category = itemView.findViewById(R.id.Category);
            WalletName = itemView.findViewById(R.id.WalletName);
            IndicatorView = itemView.findViewById(R.id.IndicatorView);
            Amount = itemView.findViewById(R.id.Amount);
            Icon = itemView.findViewById(R.id.Icon);
            ExpenseDate = itemView.findViewById(R.id.ExpenseDate);
            view = itemView.findViewById(R.id.cardItemLayout);
        }
    }
}