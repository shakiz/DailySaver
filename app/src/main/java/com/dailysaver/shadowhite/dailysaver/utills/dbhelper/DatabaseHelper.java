package com.dailysaver.shadowhite.dailysaver.utills.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import com.dailysaver.shadowhite.dailysaver.models.budget.Budget;
import com.dailysaver.shadowhite.dailysaver.models.wallet.Wallet;

import java.util.ArrayList;

import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_BUDGET_AMOUNT;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_BUDGET_CATEGORY;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_BUDGET_CURRENCY;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_BUDGET_EXPENSE_DATE;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_BUDGET_ID;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_BUDGET_NOTE;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_BUDGET_WALLET;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_BUDGET_WALLET_TYPE;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_WALLET_AMOUNT;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_WALLET_CURRENCY;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_WALLET_EXPIRES_ON;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_WALLET_ID;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_WALLET_NOTE;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_WALLET_TITLE;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_WALLET_TYPE;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "dailySaver.db";
    private static final int DB_VERSION = 1;

    private static final String WALLET_TABLE = "Wallet";
    private static final String BUDGET_TABLE = "Budget";

    private static String CREATE_BUDGET_TABLE = "CREATE TABLE " + BUDGET_TABLE + "("
            + COLUMN_BUDGET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_BUDGET_AMOUNT + " REAL,"
            + COLUMN_BUDGET_CURRENCY + " TEXT," + COLUMN_BUDGET_CATEGORY + " TEXT," + COLUMN_BUDGET_NOTE + " TEXT,"
            + COLUMN_BUDGET_WALLET + " TEXT," + COLUMN_BUDGET_WALLET_TYPE + " TEXT,"
            + COLUMN_BUDGET_EXPENSE_DATE + " TEXT" + ")";

    private static String CREATE_WALLET_TABLE = "CREATE TABLE " + WALLET_TABLE + "("
            + COLUMN_WALLET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_WALLET_AMOUNT + " REAL,"
            + COLUMN_WALLET_CURRENCY + " TEXT," + COLUMN_WALLET_TITLE + " TEXT," + COLUMN_WALLET_NOTE + " TEXT,"
            + COLUMN_WALLET_TYPE + " TEXT," + COLUMN_WALLET_EXPIRES_ON + " TEXT" + ")";

    private static String DROP_WALLET_TABLE = "DROP TABLE IF EXISTS "+WALLET_TABLE;
    private static String DROP_BUDGET_TABLE = "DROP TABLE IF EXISTS "+BUDGET_TABLE;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BUDGET_TABLE);
        db.execSQL(CREATE_WALLET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_BUDGET_TABLE);
        db.execSQL(DROP_WALLET_TABLE);
    }

    /**
     * This method is to create budget record
     *
     * @param budget
     */
    public void addNewBudget(Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_BUDGET_AMOUNT, budget.getAmount());
        values.put(COLUMN_BUDGET_CURRENCY, budget.getCurrency());
        values.put(COLUMN_BUDGET_CATEGORY, budget.getCategory());
        values.put(COLUMN_BUDGET_WALLET, budget.getWallet());
        values.put(COLUMN_BUDGET_WALLET_TYPE,budget.getWalletTypeId());
        values.put(COLUMN_BUDGET_NOTE,budget.getNote());
        values.put(COLUMN_BUDGET_EXPENSE_DATE,budget.getExpenseDate());

        // Inserting Row
        db.insert(BUDGET_TABLE, null, values);
        db.close();
    }

    public ArrayList<Budget> getAllBudgetItems() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_BUDGET_ID,
                COLUMN_BUDGET_AMOUNT,
                COLUMN_BUDGET_CATEGORY,
                COLUMN_BUDGET_EXPENSE_DATE,
                COLUMN_BUDGET_CURRENCY,
                COLUMN_BUDGET_NOTE,
                COLUMN_BUDGET_WALLET,
                COLUMN_BUDGET_WALLET_TYPE
        };
        // sorting orders
        String sortOrder =
                COLUMN_BUDGET_ID + " DESC";
        ArrayList<Budget> budgetList = new ArrayList<Budget>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the fooditem table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this is query function
         */
        Cursor cursor = db.query(BUDGET_TABLE, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    Budget budget = new Budget();

                    budget.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_BUDGET_ID)));
                    budget.setAmount(cursor.getInt(cursor.getColumnIndex(COLUMN_BUDGET_AMOUNT)));
                    budget.setCategory((cursor.getString(cursor.getColumnIndex(COLUMN_BUDGET_CATEGORY))));
                    budget.setExpenseDate((cursor.getString(cursor.getColumnIndex(COLUMN_BUDGET_EXPENSE_DATE))));
                    budget.setCurrency(cursor.getString(cursor.getColumnIndex(COLUMN_BUDGET_CURRENCY)));
                    budget.setNote(cursor.getString(cursor.getColumnIndex(COLUMN_BUDGET_NOTE)));
                    budget.setWalletId(cursor.getString(cursor.getColumnIndex(COLUMN_BUDGET_WALLET)));
                    budget.setWalletTypeId(cursor.getString(cursor.getColumnIndex(COLUMN_BUDGET_WALLET_TYPE)));

                    // Adding food item record to list
                    budgetList.add(budget);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return budgetList;
    }

    /**
     * This method is to create wallet record
     *
     * @param wallet
     */
    public void addNewWallet(Wallet wallet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WALLET_AMOUNT, wallet.getAmount());
        values.put(COLUMN_WALLET_TITLE, wallet.getTitle());
        values.put(COLUMN_WALLET_CURRENCY, wallet.getCurrency());
        values.put(COLUMN_WALLET_NOTE, wallet.getNote());
        values.put(COLUMN_WALLET_TYPE,wallet.getWalletType());
        values.put(COLUMN_WALLET_EXPIRES_ON,wallet.getExpiresOn());

        // Inserting Row
        db.insert(BUDGET_TABLE, null, values);
        db.close();
    }

}
