package com.dailysaver.shadowhite.dailysaver.utills;

import android.content.Context;
import android.os.AsyncTask;
import com.dailysaver.shadowhite.dailysaver.models.expensewallet.Expense;
import com.dailysaver.shadowhite.dailysaver.models.savingswallet.Wallet;
import java.util.ArrayList;

public class DataLoader {
    private Context context;
    private UX ux;
    private onItemCompleted onItemCompleted;

    public DataLoader(Context context) {
        this.context = context;
        ux = new UX(context);
    }

    public void setOnItemCompleted(DataLoader.onItemCompleted onItemCompleted) {
        this.onItemCompleted = onItemCompleted;
    }

    public interface onItemCompleted{
        void onComplete();
    }

    private class LoadDataInBackground extends AsyncTask<String,Void, ArrayList<?>>{

        private String flag;

        public LoadDataInBackground(String flag) {
            this.flag = flag;
        }

        @Override
        protected void onPreExecute() {
            ux.getLoadingView();
        }

        @Override
        protected ArrayList<?> doInBackground(String... strings) {
            if (flag.equals("expense")) return getExpenseWalletList();
            else return getSavingsWalletList();
        }

        @Override
        protected void onPostExecute(ArrayList<?> objects) {
            if (objects != null){
                if (objects.size() != 0){
                    if (ux.loadingDialog.isShowing()){
                        ux.removeLoadingView();
                    }
                }
            }
        }
    }

    private ArrayList<Expense> getExpenseWalletList(){
        ArrayList<Expense> expenseList = new ArrayList<>();
        return expenseList;
    }

    private ArrayList<Wallet> getSavingsWalletList(){
        ArrayList<Wallet> walletList = new ArrayList<>();
        return walletList;
    }

}
