package com.dailysaver.shadowhite.dailysaver.utills;

import android.content.Context;
import android.os.AsyncTask;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.models.budget.Budget;
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
        void onComplete(ArrayList<Budget> budgetList);
    }

    public void setOnWalletItemsCompleted(onWalletItemsCompleted onWalletItemsCompleted) {
        this.onWalletItemsCompleted = onWalletItemsCompleted;
        new LoadWalletsInBackground().execute();
    }

    public void setOnBudgetItemsCompleted(DataLoader.onBudgetItemsCompleted onBudgetItemsCompleted) {
        this.onBudgetItemsCompleted = onBudgetItemsCompleted;
    }

    private class LoadWalletsInBackground extends AsyncTask<String,Void, ArrayList<Wallet>>{

        @Override
        protected void onPreExecute() {
            ux.getLoadingView();
        }

        @Override
        protected ArrayList<Wallet> doInBackground(String... strings) {
            return getWalletList();
        }

        @Override
        protected void onPostExecute(ArrayList<Wallet> wallets) {
            if (wallets != null){
                if (wallets.size() != 0){
                    if (ux.loadingDialog.isShowing()){
                        onWalletItemsCompleted.onComplete(wallets);
                        ux.removeLoadingView();
                        new LoadBudgetsInBackground().execute();
                    }
                }
            }
        }
    }

    private class LoadBudgetsInBackground extends AsyncTask<String,Void, ArrayList<Budget>>{

        @Override
        protected void onPreExecute() {
            ux.getLoadingView();
        }

        @Override
        protected ArrayList<Budget> doInBackground(String... strings) {
            return databaseHelper.getAllBudgetItems();
        }

        @Override
        protected void onPostExecute(ArrayList<Budget> budgets) {
            if (budgets != null){
                if (budgets.size() != 0){
                    if (ux.loadingDialog.isShowing()){
                        onBudgetItemsCompleted.onComplete(budgets);
                        ux.removeLoadingView();
                    }
                }
            }
        }
    }

    private ArrayList<Wallet> getWalletList(){
        ArrayList<Wallet> walletList = new ArrayList<>();
        walletList.add(new Wallet("April Wallet",2500,"Tk.","21-Apr-19","Savings",context.getResources().getString(R.string.note_hint)));
        walletList.add(new Wallet("May Wallet",3200,"Tk.","22-May-19","Expense",context.getResources().getString(R.string.note_hint)));
        walletList.add(new Wallet("September Wallet",5000,"Tk.","21-Sep-19","Savings",context.getResources().getString(R.string.note_hint)));
        walletList.add(new Wallet("August Wallet",7500,"Tk.","10-Aug-19","Expense",context.getResources().getString(R.string.note_hint)));
        return walletList;
    }

    private ArrayList<Budget> getBudgetWalletList(){
        ArrayList<Budget> budgetList = new ArrayList<>();
        budgetList.add(new Budget(1,250,"Tk.","Family","1","1",context.getResources().getString(R.string.note_hint),"21-Apr-19"));
        budgetList.add(new Budget(2,2410,"Tk.","Friends","1","1",context.getResources().getString(R.string.note_hint),"22-May-19"));
        budgetList.add(new Budget(3,2534,"Tk.","Gift","1","1",context.getResources().getString(R.string.note_hint),"21-Sep-19"));
        budgetList.add(new Budget(4,454,"Tk.","Travel","1","1",context.getResources().getString(R.string.note_hint),"10-Aug-19"));
        budgetList.add(new Budget(5,323,"Tk.","Family","1","1",context.getResources().getString(R.string.note_hint),"10-Nov-25"));
        return budgetList;
    }

    public String[] getWalletTitle(){
        String[] walletTitleList = new String[]{context.getResources().getString(R.string.select_wallet),"April Wallet","May Wallet","September Wallet","August Wallet"};
        return walletTitleList;
    }
}
