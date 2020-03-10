package com.dailysaver.shadowhite.dailysaver.utills;

import android.content.Context;
import com.dailysaver.shadowhite.dailysaver.R;

public class SpinnerData {
    private Context context;

    public SpinnerData(Context context) {
        this.context = context;
    }

    public String[] currencyData(){
        return new String[]{context.getResources().getString(R.string.select_currency),"BDT Tk.","US Dollar"};
    }

    public String[] walletTypeData(){
        return new String[]{context.getResources().getString(R.string.select_wallet_type),"Savings","Expense"};
    }


    public String[] getWalletTitle(){
        String[] walletTitleList = new String[]{context.getResources().getString(R.string.select_wallet),"April Wallet","May Wallet","September Wallet","August Wallet"};
        return walletTitleList;
    }
}
