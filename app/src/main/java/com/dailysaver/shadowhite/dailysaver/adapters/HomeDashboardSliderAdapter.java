package com.dailysaver.shadowhite.dailysaver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.models.dashboard.WalletDashboardItemModel;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.ArrayList;

public class HomeDashboardSliderAdapter extends SliderViewAdapter<HomeDashboardSliderAdapter.SliderAdapterVH> {

    private ArrayList<WalletDashboardItemModel> cardItemList;
    private Context context;

    public HomeDashboardSliderAdapter(ArrayList<WalletDashboardItemModel> cardItemList, Context context) {
        this.cardItemList = cardItemList;
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_home_dashboard
                , null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        WalletDashboardItemModel itemModel = cardItemList.get(position);
        viewHolder.Position.setText(""+(++position));
        viewHolder.Title.setText(itemModel.getTitle());
        viewHolder.TodayCost.setText(""+itemModel.getTodayCost());
        viewHolder.Type.setText(itemModel.getType());
        viewHolder.ExpiresOn.setText(itemModel.getExpiresOn());
        setProgressData(itemModel.getTotalCost(),itemModel.getRemainingBalance(),viewHolder.RemainingBalance);
        viewHolder.TotalCost.setText(""+itemModel.getTotalCost());
    }

    private void setProgressData(int balanceRemaining, int totalCost, ArcProgress RemainingBalanceArc) {
        RemainingBalanceArc.setMax(totalCost);
        RemainingBalanceArc.setSuffixText("");
        RemainingBalanceArc.setFinishedStrokeColor(context.getResources().getColor(R.color.md_red_400));
        RemainingBalanceArc.setUnfinishedStrokeColor(context.getResources().getColor(R.color.md_green_400));
        RemainingBalanceArc.setTextSize(40);
        RemainingBalanceArc.setBottomText(null);
        RemainingBalanceArc.setTextColor(context.getResources().getColor(R.color.md_grey_600));
        RemainingBalanceArc.setProgress(balanceRemaining);
    }

    @Override
    public int getCount() {
        return cardItemList.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        TextView Title,TodayCost,TotalCost,Position,Type,ExpiresOn;
        ArcProgress RemainingBalance;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            Position = itemView.findViewById(R.id.Position);
            TodayCost = itemView.findViewById(R.id.TodayCost);
            RemainingBalance = itemView.findViewById(R.id.RemainingBalance);
            TotalCost = itemView.findViewById(R.id.TotalCost);
            Type = itemView.findViewById(R.id.Type);
            ExpiresOn = itemView.findViewById(R.id.ExpiresOn);
            this.itemView = itemView;
        }
    }
}