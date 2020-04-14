package com.dailysaver.shadowhite.dailysaver.utills;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.wallet.AddNewWalletActivity;
import com.dailysaver.shadowhite.dailysaver.models.category.Category;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import java.util.ArrayList;

public class DataManager {
    private Context context;
    private DatabaseHelper databaseHelper;
    private UX ux;

    public DataManager(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        ux = new UX(context);
    }

    public ArrayList<String> currencyData(){
        ArrayList<String> currencyList = new ArrayList<>();
        currencyList.add(context.getResources().getString(R.string.select_currency));
        currencyList.add("BDT Tk.");
        currencyList.add("US Dollar");
        return currencyList;
    }

    public ArrayList<String> recordTypeData(){
        ArrayList<String> currencyList = new ArrayList<>();
        currencyList.add(context.getResources().getString(R.string.select_currency));
        currencyList.add("Savings");
        currencyList.add("Expense");
        return currencyList;
    }

    public ArrayList<String> getWalletTitle(String walletType){
        ArrayList<String> walletTitleList = databaseHelper.getWalletTitle(walletType);
        if (walletTitleList.size() != 0){
            if (walletTitleList.get(0).equals("No Data")){
                //TODO add a dialog here to prevent amount related bug
                ux.showDialog(R.layout.dialog_no_savings_wallet, "No savings wallet found", new UX.onDialogOkListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        context.startActivity(new Intent(context, AddNewWalletActivity.class).putExtra("from","newRecord"));
                    }
                });
            }
        }
        return walletTitleList;
    }

    public ArrayList<Category> setCategoryDataData() {
        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Food",R.drawable.ic_food_icon));
        categoryList.add(new Category("Transport",R.drawable.ic_transport));
        categoryList.add(new Category("Energy",R.drawable.ic_electricity));
        categoryList.add(new Category("Education",R.drawable.ic_education));
        categoryList.add(new Category("Shopping",R.drawable.ic_cshopping));
        categoryList.add(new Category("Fun",R.drawable.ic_entertainment));
        categoryList.add(new Category("Family",R.drawable.ic_family));
        categoryList.add(new Category("Friends",R.drawable.ic_friends));
        categoryList.add(new Category("Work",R.drawable.ic_work));
        categoryList.add(new Category("Gift",R.drawable.ic_gift));
        return categoryList;
    }

    public ArrayList getMonthNameForLabel(){
        ArrayList xLabels = new ArrayList();
        xLabels.add("Jan");
        xLabels.add("Feb");
        xLabels.add("Mar");
        xLabels.add("Apr");
        xLabels.add("May");
        xLabels.add("Jun");
        xLabels.add("July");
        xLabels.add("Aug");
        xLabels.add("Sep");
        xLabels.add("Oct");
        xLabels.add("Nov");
        xLabels.add("Dec");

        return xLabels;
    }
}
