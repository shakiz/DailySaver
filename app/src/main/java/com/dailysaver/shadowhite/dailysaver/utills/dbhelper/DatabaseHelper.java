package com.dailysaver.shadowhite.dailysaver.utills.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "dailySaver.db";
    private static final int DB_VERSION = 1;

    private static final String WALLET_TABLE = "Wallet";
    private static final String SAVINGS_TABLE = "Savings";
    private static final String EXPENSE_TABLE = "Expense";


    private static String CREATE_WALLET_TABLE = " ";
    private static String CREATE_SAVINGS_TABLE = " ";
    private static String CREATE_EXPENSE_TABLE = " ";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
