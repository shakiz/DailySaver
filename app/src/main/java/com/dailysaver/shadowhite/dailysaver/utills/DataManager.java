package com.dailysaver.shadowhite.dailysaver.utills;

import android.content.Context;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.models.category.Category;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import java.util.ArrayList;

public class DataManager {
    private Context context;
    private DatabaseHelper databaseHelper;

    public DataManager(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public ArrayList<String> currencyData(){
        ArrayList<String> currencyList = new ArrayList<>();
        currencyList.add(context.getResources().getString(R.string.select_currency));
        currencyList.add("BDT Tk.");
        currencyList.add("US Dollar");
        return currencyList;
    }

    public ArrayList<String> transactionTypeData(){
        ArrayList<String> currencyList = new ArrayList<>();
        currencyList.add(context.getResources().getString(R.string.select_currency));
        currencyList.add("Savings");
        currencyList.add("Expense");
        return currencyList;
    }

    public ArrayList<String> getWalletTitle(){
        ArrayList<String> walletTitleList = databaseHelper.getWalletTitle();;
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
