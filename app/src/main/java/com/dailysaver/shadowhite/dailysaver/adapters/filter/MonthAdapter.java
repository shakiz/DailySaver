package com.dailysaver.shadowhite.dailysaver.adapters.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.dailysaver.shadowhite.dailysaver.R;
import java.util.ArrayList;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {

    private ArrayList<String> monthList;
    private Context context;
    private onItemClickListener onItemClickListener;

    public interface onItemClickListener{
        void onItemClick(String month);
    }

    public MonthAdapter(ArrayList<String> monthList, Context context, MonthAdapter.onItemClickListener onItemClickListener) {
        this.monthList = monthList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_layout_month_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.month.setText(monthList.get(position));
        holder.cardItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(monthList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView month;
        CardView cardItemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.MonthName);
            cardItemLayout = itemView.findViewById(R.id.cardItemLayout);
        }
    }
}
