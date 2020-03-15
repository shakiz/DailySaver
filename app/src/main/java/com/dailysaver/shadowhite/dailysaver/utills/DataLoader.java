package com.dailysaver.shadowhite.dailysaver.utills;

import android.content.Context;
import android.os.AsyncTask;
import com.dailysaver.shadowhite.dailysaver.models.expense.Expense;
import com.dailysaver.shadowhite.dailysaver.models.wallet.Wallet;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import java.util.ArrayList;

public class DataLoader {
    private Context context;
    private UX ux;
    private onWalletItemsCompleted onWalletItemsCompleted;
    private onBudgetItemsCompleted onBudgetItemsCompleted;
    private DatabaseHelper databaseHelper;

    public DataLoader(Context context) {
        this.context = context;
        ux = new UX(context);
        databaseHelper = new DatabaseHelper(context);
    }

    public interface onWalletItemsCompleted{
        void onComplete(ArrayList<Wallet> walletList);
    }

    public interface onBudgetItemsCompleted{
        void onComplete(ArrayList<Expense> expenseList);
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

    private class LoadBudgetsInBackground extends AsyncTask<String,Void, ArrayList<Expense>>{

        @Override
        protected void onPreExecute() {
            ux.getLoadingView();
        }

        @Override
        protected ArrayList<Expense> doInBackground(String... strings) {
            return databaseHelper.getAllExpenseItems(0);
        }

        @Override
        protected void onPostExecute(ArrayList<Expense> expenses) {

                    onBudgetItemsCompleted.onComplete(expenses);
                    if (ux.loadingDialog.isShowing()) {
                        ux.removeLoadingView();
                    }
        }
    }
}
