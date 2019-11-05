package com.dailysaver.shadowhite.dailysaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.github.lzyzsd.circleprogress.ArcProgress;
import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private ArrayList<CardItemModel> cardItemList;
    private Context context;

    public CardAdapter(ArrayList<CardItemModel> cardItemList, Context context) {
        this.cardItemList = cardItemList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.swipe_cards_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardItemModel itemModel = cardItemList.get(position);
        holder.Title.setText(itemModel.getTitle());
        holder.TodayCost.setText(""+itemModel.getTodayCost());
        holder.RemainingBalance.setMax(itemModel.getTotalCost());
        holder.RemainingBalance.setSuffixText("");
        holder.RemainingBalance.setFinishedStrokeColor(context.getResources().getColor(R.color.md_red_400));
        holder.RemainingBalance.setUnfinishedStrokeColor(context.getResources().getColor(R.color.md_green_400));
        holder.RemainingBalance.setTextSize(80);
        holder.RemainingBalance.setBottomText(context.getResources().getString(R.string.remaining));
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
