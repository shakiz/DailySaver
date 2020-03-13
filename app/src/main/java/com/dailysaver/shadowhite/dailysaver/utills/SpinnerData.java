package com.dailysaver.shadowhite.dailysaver.utills;

import android.content.Context;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;

public class SpinnerData {
    private Context context;
    private DatabaseHelper databaseHelper;

    public SpinnerData(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public String[] currencyData(){
        return new String[]{context.getResources().getString(R.string.select_currency),"BDT Tk.","US Dollar"};
    }

    public String[] walletTypeData(){
        return new String[]{context.getResources().getString(R.string.select_wallet_type),"Savings","Expense"};
    }


    public String[] getWalletTitle(){
        String[] walletTitleList = databaseHelper.getWalletTitle();;
        return walletTitleList;
    }
}
