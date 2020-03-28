package com.dailysaver.shadowhite.dailysaver.utills.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.dailysaver.shadowhite.dailysaver.models.expense.Expense;
import com.dailysaver.shadowhite.dailysaver.models.wallet.Wallet;
import java.util.ArrayList;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_AMOUNT;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_CATEGORY;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_CURRENCY;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_DATE;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_ID;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_NOTE;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_WALLET;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_WALLET_ID;
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
    private static final String EXPENSE_TABLE = "Expense";

    private static String CREATE_EXPENSE_TABLE = "CREATE TABLE " + EXPENSE_TABLE + "("
            + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_EXPENSE_AMOUNT + " INTEGER,"
            + COLUMN_EXPENSE_CURRENCY + " INTEGER," + COLUMN_EXPENSE_CATEGORY + " TEXT," + COLUMN_EXPENSE_NOTE + " TEXT,"+ COLUMN_EXPENSE_WALLET_ID + " INTEGER,"
            + COLUMN_EXPENSE_WALLET + " TEXT," + COLUMN_EXPENSE_DATE + " TEXT" + ")";

    private static String CREATE_WALLET_TABLE = "CREATE TABLE " + WALLET_TABLE + "("
            + COLUMN_WALLET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_WALLET_AMOUNT + " REAL,"
            + COLUMN_WALLET_CURRENCY + " INTEGER," + COLUMN_WALLET_TITLE + " TEXT," + COLUMN_WALLET_NOTE + " TEXT,"
            + COLUMN_WALLET_TYPE + " INTEGER," + COLUMN_WALLET_EXPIRES_ON + " TEXT" + ")";

    private static String DROP_WALLET_TABLE = "DROP TABLE IF EXISTS "+WALLET_TABLE;
    private static String DROP_EXPENSE_TABLE = "DROP TABLE IF EXISTS "+EXPENSE_TABLE;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EXPENSE_TABLE);
        db.execSQL(CREATE_WALLET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_EXPENSE_TABLE);
        db.execSQL(DROP_WALLET_TABLE);
    }

    /**
     * This method is to create EXPENSE record
     *
     * @param expense
     */
    public void addNewExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXPENSE_AMOUNT, expense.getAmount());
        values.put(COLUMN_EXPENSE_CURRENCY, expense.getCurrency());
        values.put(COLUMN_EXPENSE_CATEGORY, expense.getCategory());
        values.put(COLUMN_EXPENSE_WALLET, expense.getWalletTitle());
        values.put(COLUMN_EXPENSE_WALLET_ID, expense.getWalletId());
        values.put(COLUMN_EXPENSE_NOTE, expense.getNote());
        values.put(COLUMN_EXPENSE_DATE, expense.getExpenseDate());

        // Inserting Row
        db.insert(EXPENSE_TABLE, null, values);
        db.close();
    }

    /**
     * This method is to update EXPENSE record
     *
     * @param expense
     */
    public void updateExpense(Expense expense, int expenseId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EXPENSE_AMOUNT, expense.getAmount());
        values.put(COLUMN_EXPENSE_CURRENCY, expense.getCurrency());
        values.put(COLUMN_EXPENSE_CATEGORY, expense.getCategory());
        values.put(COLUMN_EXPENSE_WALLET, expense.getWalletTitle());
        values.put(COLUMN_EXPENSE_WALLET_ID, expense.getWalletId());
        values.put(COLUMN_EXPENSE_NOTE, expense.getNote());
        values.put(COLUMN_EXPENSE_DATE, expense.getExpenseDate());

        // Inserting Row
        db.update(EXPENSE_TABLE, values, COLUMN_EXPENSE_ID+" = "+expenseId, null);
        db.close();
    }

    /**
     * This method is to get all expense record
     *
     * @param walletId
     */
    public ArrayList<Expense> getAllExpenseItems(int walletId) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_EXPENSE_ID,
                COLUMN_EXPENSE_AMOUNT,
                COLUMN_EXPENSE_CATEGORY,
                COLUMN_EXPENSE_DATE,
                COLUMN_EXPENSE_CURRENCY,
                COLUMN_EXPENSE_NOTE,
                COLUMN_EXPENSE_WALLET,
                COLUMN_EXPENSE_WALLET_ID
        };
        // sorting orders
        String sortOrder =
                COLUMN_EXPENSE_ID + " DESC";
        ArrayList<Expense> expenseList = new ArrayList<Expense>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (walletId == 0) {
            cursor = db.query(EXPENSE_TABLE, columns, null, null, null, null, sortOrder);
        }
        else {
            String where = "" + COLUMN_EXPENSE_WALLET_ID + " = "+ walletId +"";
            cursor = db.query(EXPENSE_TABLE, columns, where, null, null, null, sortOrder);
        }

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    Expense expense = new Expense();

                    expense.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENSE_ID)));
                    expense.setAmount(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENSE_AMOUNT)));
                    expense.setCategory((cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_CATEGORY))));
                    expense.setExpenseDate((cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_DATE))));
                    expense.setCurrency(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENSE_CURRENCY)));
                    expense.setNote(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_NOTE)));
                    expense.setWalletTitle(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_WALLET)));
                    expense.setWalletId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENSE_WALLET_ID)));
                    expenseList.add(expense);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return expenseList;
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
        db.insert(WALLET_TABLE, null, values);
        db.close();
    }

    /**
     * This method is to update wallet record
     *
     * @param wallet
     */
    public void updateWallet(Wallet wallet, int walletId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WALLET_TITLE, wallet.getTitle());
        values.put(COLUMN_WALLET_AMOUNT, wallet.getAmount());
        values.put(COLUMN_WALLET_CURRENCY, wallet.getCurrency());
        values.put(COLUMN_WALLET_NOTE, wallet.getNote());
        values.put(COLUMN_WALLET_EXPIRES_ON, wallet.getExpiresOn());
        values.put(COLUMN_WALLET_TYPE, wallet.getWalletType());

        // Inserting Row
        db.update(WALLET_TABLE, values, COLUMN_WALLET_ID + " = "+walletId, null);
        db.close();
    }

    /**
     * This method is to get all wallet record
     *
     */
    public ArrayList<Wallet> getAllWalletItems() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_WALLET_ID,
                COLUMN_WALLET_AMOUNT,
                COLUMN_WALLET_TITLE,
                COLUMN_WALLET_CURRENCY,
                COLUMN_WALLET_NOTE,
                COLUMN_WALLET_EXPIRES_ON,
                COLUMN_WALLET_TYPE
        };
        // sorting orders
        String sortOrder =
                COLUMN_WALLET_ID + " DESC";
        ArrayList<Wallet> walletList = new ArrayList<Wallet>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(WALLET_TABLE, columns, null, null, null, null, sortOrder); //The sort order

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    Wallet wallet = new Wallet();

                    wallet.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_WALLET_ID)));
                    wallet.setAmount(cursor.getInt(cursor.getColumnIndex(COLUMN_WALLET_AMOUNT)));
                    wallet.setTitle((cursor.getString(cursor.getColumnIndex(COLUMN_WALLET_TITLE))));
                    wallet.setNote((cursor.getString(cursor.getColumnIndex(COLUMN_WALLET_NOTE))));
                    wallet.setCurrency(cursor.getInt(cursor.getColumnIndex(COLUMN_WALLET_CURRENCY)));
                    wallet.setExpiresOn(cursor.getString(cursor.getColumnIndex(COLUMN_WALLET_EXPIRES_ON)));
                    wallet.setWalletType(cursor.getString(cursor.getColumnIndex(COLUMN_WALLET_TYPE)));

                    walletList.add(wallet);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return walletList;
    }

    /**
     * This method is to get wallet title
     *
     */
    public ArrayList<String> getWalletTitle() {
        ArrayList<String> walletTitle = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String[] columns = {
                COLUMN_WALLET_TITLE
        };

        Cursor cursor = sqLiteDatabase.query(WALLET_TABLE, columns ,null , null , null , null , null);
        int counter = 0;
        if (cursor != null){
            if (cursor.getCount() > 0){
                if (cursor.moveToFirst()){
                    walletTitle.add(0,"Select Wallet");
                    do{
                        counter++;
                        walletTitle.add(counter,cursor.getString(0));
                    }while (cursor.moveToNext());
                }
            }
            else{
                walletTitle.add("No Data");
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return walletTitle;
    }

    /**
     * This method is to get wallet cost of total
     *
     * @param walletId
     */
    public int singleWalletTotalCost(int walletId){
        int totalCost = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String columns[] = {
                COLUMN_EXPENSE_ID,
                COLUMN_EXPENSE_AMOUNT
        };
        String where = "" + COLUMN_EXPENSE_WALLET_ID + " = "+ walletId +"";
        Cursor cursor = sqLiteDatabase.query(EXPENSE_TABLE, columns, where, null, null, null, null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    totalCost += cursor.getInt(1);
                }while (cursor.moveToNext());
            }
        }
        else{
            totalCost = 0;
        }

        cursor.close();
        sqLiteDatabase.close();
        return totalCost;
    }

    /**
     * This method is to get the wallet main balance
     *
     * @param walletId
     */
    public int getWalletBalance(int walletId){
        int balance = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String columns[] = {
                COLUMN_WALLET_ID,
                COLUMN_WALLET_AMOUNT
        };
        String where = "" + COLUMN_WALLET_ID + " = "+ walletId +"";
        Cursor cursor = sqLiteDatabase.query(WALLET_TABLE, columns, where, null, null, null, null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    balance += cursor.getInt(1);
                }while (cursor.moveToNext());
            }
        }
        else{
            balance = 0;
        }

        cursor.close();
        sqLiteDatabase.close();
        return balance;
    }

    /**
     * This method is to get id of a wallet
     *
     * @param title
     */
    public int getWalletId(String title) {
        int walletId = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String columns[] = {
                COLUMN_EXPENSE_WALLET_ID
        };
        String where = "" + COLUMN_EXPENSE_WALLET + " = '"+ title +"'";
        Cursor cursor = sqLiteDatabase.query(EXPENSE_TABLE, columns, where, null, null, null, null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    walletId = cursor.getInt(0);
                }while (cursor.moveToNext());
            }
        }
        else{
            walletId = 0;
        }

        sqLiteDatabase.close();
        return walletId;
    }

    /**
     * This method is to all cost for a single month
     *
     */
    public int getCostOfMonth(long date) {
        int totalCost = 0;
        // array of columns to fetch
        String[] columns = {
                COLUMN_EXPENSE_ID,
                COLUMN_EXPENSE_AMOUNT,
                COLUMN_EXPENSE_DATE
        };
        // sorting orders
        String sortOrder =
                COLUMN_EXPENSE_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        String where = "" + COLUMN_EXPENSE_DATE + " = '"+ date +"'";

        Cursor cursor = db.query(EXPENSE_TABLE, columns, where, null, null, null, sortOrder); //The sort order

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    totalCost += cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENSE_AMOUNT));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return totalCost;
    }
}
