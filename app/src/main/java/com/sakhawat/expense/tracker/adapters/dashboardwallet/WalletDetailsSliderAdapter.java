package com.sakhawat.expense.tracker.adapters.dashboardwallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sakhawat.expense.tracker.R;
import com.sakhawat.expense.tracker.models.wallet.Wallet;
import com.sakhawat.expense.tracker.utills.dbhelper.DatabaseHelper;
import com.github.lzyzsd.circleprogress.ArcProgress;
import java.util.ArrayList;

public class WalletDetailsSliderAdapter extends RecyclerView.Adapter<WalletDetailsSliderAdapter.SliderAdapterVH> {

    private ArrayList<Wallet> cardItemList;
    private Context context;
    private onItemClick onItemClick;
    private DatabaseHelper databaseHelper;

    public WalletDetailsSliderAdapter(ArrayList<Wallet> cardItemList, Context context) {
        this.cardItemList = cardItemList;
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public void onItemClickListener(onItemClick onItemClick){
        this.onItemClick = onItemClick;
    }

    public interface onItemClick{
        void itemClick(Wallet wallet, int id);
    }

    @NonNull
    @Override
    public SliderAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_wallet_dashboard, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        final Wallet itemModel = cardItemList.get(position);
        final String walletName = itemModel.getTitle();
        final int walletId = itemModel.getId();
        viewHolder.Position.setText(""+(++position));
        viewHolder.Title.setText(itemModel.getTitle());
        viewHolder.WalletType.setText(""+itemModel.getWalletType());

        //Changing the wallet type color based on wallet type
        if (itemModel.getWalletType().equals("Expense")) {
            viewHolder.Amount.setText(""+itemModel.getAmount());
            viewHolder.WalletType.setTextColor(context.getResources().getColor(R.color.md_red_600));
        }
        else if (itemModel.getWalletType().equals("Savings")){
            viewHolder.Amount.setText(""+itemModel.getAmount());
            viewHolder.TotalHeading.setText("Savings");
            viewHolder.RemainingHeading.setText("Total Amount");
            viewHolder.WalletType.setTextColor(context.getResources().getColor(R.color.md_green_600));
        }
        //setting the progress details
        setProgressData(itemModel,databaseHelper.singleWalletTotalCost(walletName),viewHolder.RemainingBalance);
        viewHolder.TotalCost.setText(""+databaseHelper.singleWalletTotalCost(walletName));

        //onClick listener
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.itemClick(itemModel, walletId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    private void setProgressData(Wallet wallet, int totalCost, ArcProgress RemainingBalanceArc) {
        RemainingBalanceArc.setSuffixText("");
        RemainingBalanceArc.setFinishedStrokeColor(context.getResources().getColor(R.color.md_green_400));
        RemainingBalanceArc.setUnfinishedStrokeColor(context.getResources().getColor(R.color.md_red_400));
        RemainingBalanceArc.setTextSize(24);
        RemainingBalanceArc.setBottomText(null);
        RemainingBalanceArc.setTextColor(context.getResources().getColor(R.color.md_grey_600));
        if (wallet.getWalletType().equals("Expense")) {
            RemainingBalanceArc.setMax(wallet.getAmount());
            RemainingBalanceArc.setProgress((wallet.getAmount() - totalCost));
        }
        else{
            RemainingBalanceArc.setMax((wallet.getAmount() + totalCost));
            RemainingBalanceArc.setProgress((wallet.getAmount() + totalCost));
            RemainingBalanceArc.setFinishedStrokeColor(context.getResources().getColor(R.color.md_green_400));
        }
    }

    public static class SliderAdapterVH extends RecyclerView.ViewHolder {

        View itemView;
        TextView Title,Amount,TotalCost,Position, WalletType,ExpiresOn, TotalHeading, RemainingHeading;
        ArcProgress RemainingBalance;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            Position = itemView.findViewById(R.id.Position);
            Amount = itemView.findViewById(R.id.Amount);
            RemainingBalance = itemView.findViewById(R.id.RemainingBalance);
            TotalCost = itemView.findViewById(R.id.TotalCost);
            WalletType = itemView.findViewById(R.id.WalletType);
            ExpiresOn = itemView.findViewById(R.id.ExpiresOn);
            TotalHeading = itemView.findViewById(R.id.TotalHeading);
            RemainingHeading = itemView.findViewById(R.id.RemainingHeading);
            this.itemView = itemView;
        }
    }
}