package com.dailysaver.shadowhite.dailysaver.utills.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.dailysaver.shadowhite.dailysaver.models.budget.Budget;
import com.dailysaver.shadowhite.dailysaver.models.wallet.Wallet;
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

    private static String CREATE_WALLET_TABLE = "CREATE TABLE " + BUDGET_TABLE + "("
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
