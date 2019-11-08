package com.dailysaver.shadowhite.dailysaver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailysaver.shadowhite.dailysaver.models.dashboard.WalletDashboardItemModel;
import com.dailysaver.shadowhite.dailysaver.R;
import com.github.lzyzsd.circleprogress.ArcProgress;
import java.util.ArrayList;

public class WalletDashboardAdapter extends RecyclerView.Adapter<WalletDashboardAdapter.ViewHolder> {

    private ArrayList<WalletDashboardItemModel> cardItemList;
    private Context context;

    public WalletDashboardAdapter(ArrayList<WalletDashboardItemModel> cardItemList, Context context) {
        this.cardItemList = cardItemList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WalletDashboardItemModel itemModel = cardItemList.get(position);
        holder.Title.setText(itemModel.getTitle());
        holder.TodayCost.setText(""+itemModel.getTodayCost());
        holder.RemainingBalance.setMax(itemModel.getTotalCost());
        holder.RemainingBalance.setSuffixText("");
        holder.RemainingBalance.setFinishedStrokeColor(context.getResources().getColor(R.color.md_red_400));
        holder.RemainingBalance.setUnfinishedStrokeColor(context.getResources().getColor(R.color.md_green_400));
        holder.RemainingBalance.setTextSize(40);
        holder.RemainingBalance.setBottomText(null);
        holder.RemainingBalance.setTextColor(context.getResources().getColor(R.color.md_grey_600));
        holder.RemainingBalance.setProgress(itemModel.getRemainingBalance());
        holder.TotalCost.setText(""+itemModel.getTotalCost());
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title,TodayCost,TotalCost;
        ArcProgress RemainingBalance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            TodayCost = itemView.findViewById(R.id.TodayCost);
            RemainingBalance = itemView.findViewById(R.id.RemainingBalance);
            TotalCost = itemView.findViewById(R.id.TotalCost);
        }
    }
}
