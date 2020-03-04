package com.dailysaver.shadowhite.dailysaver.adapters.wallet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.WalletDetailsActivity;
import com.dailysaver.shadowhite.dailysaver.models.savingswallet.Wallet;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.ArrayList;

public class WalletDetailsSliderAdapter extends SliderViewAdapter<WalletDetailsSliderAdapter.SliderAdapterVH> {

    private ArrayList<Wallet> cardItemList;
    private Context context;

    public WalletDetailsSliderAdapter(ArrayList<Wallet> cardItemList, Context context) {
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
        Wallet itemModel = cardItemList.get(position);
        viewHolder.Position.setText(""+(++position));
        viewHolder.Title.setText(itemModel.getTitle());
        viewHolder.Amount.setText(""+itemModel.getAmount());
        viewHolder.Type.setText(itemModel.getWalletType());
        viewHolder.ExpiresOn.setText(itemModel.getExpiresOn());
        //TODO here totalCost is set to 100 by default , later we need to calculate total cost
        setProgressData(itemModel.getAmount(),100,viewHolder.RemainingBalance);
        viewHolder.TotalCost.setText(""+100);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, WalletDetailsActivity.class));
            }
        });
    }

    private void setProgressData(int totalAmount, int totalCost, ArcProgress RemainingBalanceArc) {
        RemainingBalanceArc.setMax(totalAmount);
        RemainingBalanceArc.setSuffixText("");
        RemainingBalanceArc.setFinishedStrokeColor(context.getResources().getColor(R.color.md_red_400));
        RemainingBalanceArc.setUnfinishedStrokeColor(context.getResources().getColor(R.color.md_green_400));
        RemainingBalanceArc.setTextSize(40);
        RemainingBalanceArc.setBottomText(null);
        RemainingBalanceArc.setTextColor(context.getResources().getColor(R.color.md_grey_600));
        RemainingBalanceArc.setProgress((totalAmount-totalCost));
    }

    @Override
    public int getCount() {
        return cardItemList.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        TextView Title,Amount,TotalCost,Position,Type,ExpiresOn;
        ArcProgress RemainingBalance;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            Position = itemView.findViewById(R.id.Position);
            Amount = itemView.findViewById(R.id.Amount);
            RemainingBalance = itemView.findViewById(R.id.RemainingBalance);
            TotalCost = itemView.findViewById(R.id.TotalCost);
            Type = itemView.findViewById(R.id.Type);
            ExpiresOn = itemView.findViewById(R.id.ExpiresOn);
            this.itemView = itemView;
        }
    }
}