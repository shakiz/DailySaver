package com.sakhawat.expense.tracker.utills;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.sakhawat.expense.tracker.models.record.Record;
import com.sakhawat.expense.tracker.models.wallet.Wallet;
import com.sakhawat.expense.tracker.utills.dbhelper.DatabaseHelper;
import java.util.ArrayList;

public class DataLoader {
    private Context context;
    private UX ux;
    private onWalletItemsCompleted onWalletItemsCompleted;
    private onBudgetItemsCompleted onBudgetItemsCompleted;
    private DatabaseHelper databaseHelper;
    private View view;

    public DataLoader(Context context,View view) {
        this.context = context;
        this.view = view;
        ux = new UX(context, view);
        databaseHelper = new DatabaseHelper(context);
    }

    public interface onWalletItemsCompleted{
        void onComplete(ArrayList<Wallet> walletList);
    }

    public interface onBudgetItemsCompleted{
        void onComplete(ArrayList<Record> recordList);
    }

    public void setOnWalletItemsCompleted(onWalletItemsCompleted onWalletItemsCompleted) {
        this.onWalletItemsCompleted = onWalletItemsCompleted;
        new LoadWalletsInBackground().execute();
    }

    public void setOnBudgetItemsCompleted(onBudgetItemsCompleted onBudgetItemsCompleted) {
        this.onBudgetItemsCompleted = onBudgetItemsCompleted;
        new LoadBudgetsInBackground().execute();
    }

    private class LoadWalletsInBackground extends AsyncTask<String,Void, ArrayList<Wallet>>{

        @Override
        protected void onPreExecute() {
            ux.getLoadingView();
        }

        @Override
        protected ArrayList<Wallet> doInBackground(String... strings) {
            return databaseHelper.getAllWalletItems();
        }

        @Override
        protected void onPostExecute(ArrayList<Wallet> wallets) {
            onWalletItemsCompleted.onComplete(wallets);
            if (ux.loadingDialog.isShowing()){
                ux.removeLoadingView();
            }
        }
    }

    private class LoadBudgetsInBackground extends AsyncTask<String,Void, ArrayList<Record>>{

        @Override
        protected void onPreExecute() {
            ux.getLoadingView();
        }

        @Override
        protected ArrayList<Record> doInBackground(String... strings) {
            return databaseHelper.getAllExpenseItems("");
        }

        @Override
        protected void onPostExecute(ArrayList<Record> expense) {
            onBudgetItemsCompleted.onComplete(expense);
            if (ux.loadingDialog.isShowing()) {
                ux.removeLoadingView();
            }
        }
    }
}
