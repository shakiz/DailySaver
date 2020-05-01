package com.sakhawat.expense.tracker.utills.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import com.sakhawat.expense.tracker.models.record.Record;
import com.sakhawat.expense.tracker.models.wallet.Wallet;
import java.util.ArrayList;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_RECORD_AMOUNT;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_RECORD_CATEGORY;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_RECORD_CURRENCY;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_RECORD_DATE;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_RECORD_ID;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_RECORD_NOTE;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_RECORD_TYPE;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_RECORD_WALLET;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_RECORD_WALLET_ID;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_WALLET_AMOUNT;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_WALLET_CURRENCY;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_WALLET_EXPIRES_ON;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_WALLET_ID;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_WALLET_NOTE;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_WALLET_TITLE;
import static com.sakhawat.expense.tracker.utills.dbhelper.DBColumns.COLUMN_WALLET_TYPE;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "dailySaver.db";
    private static final int DB_VERSION = 1;

    private static final String WALLET_TABLE = "Wallet";
    private static final String RECORD_TABLE = "Record";

    private static final String CREATE_EXPENSE_TABLE = "CREATE TABLE " + RECORD_TABLE + "("
            + COLUMN_RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_RECORD_AMOUNT + " INTEGER," + COLUMN_RECORD_TYPE + " TEXT,"
            + COLUMN_RECORD_CURRENCY + " INTEGER," + COLUMN_RECORD_CATEGORY + " TEXT," + COLUMN_RECORD_NOTE + " TEXT,"+ COLUMN_RECORD_WALLET_ID + " INTEGER,"
            + COLUMN_RECORD_WALLET + " TEXT," + COLUMN_RECORD_DATE + " TEXT" + ")";

    private static final String CREATE_WALLET_TABLE = "CREATE TABLE " + WALLET_TABLE + "("
            + COLUMN_WALLET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_WALLET_AMOUNT + " REAL,"
            + COLUMN_WALLET_CURRENCY + " INTEGER," + COLUMN_WALLET_TITLE + " TEXT," + COLUMN_WALLET_NOTE + " TEXT,"
            + COLUMN_WALLET_TYPE + " INTEGER," + COLUMN_WALLET_EXPIRES_ON + " TEXT" + ")";

    private static final String DROP_WALLET_TABLE = "DROP TABLE IF EXISTS "+WALLET_TABLE;
    private static final String DROP_EXPENSE_TABLE = "DROP TABLE IF EXISTS "+ RECORD_TABLE;


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
     * @param record
     */
    public void addNewExpense(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECORD_AMOUNT, record.getAmount());
        values.put(COLUMN_RECORD_CURRENCY, record.getCurrency());
        values.put(COLUMN_RECORD_TYPE, record.getRecordType());
        values.put(COLUMN_RECORD_CATEGORY, record.getCategory());
        values.put(COLUMN_RECORD_WALLET, record.getWalletTitle());
        values.put(COLUMN_RECORD_WALLET_ID, record.getWalletId());
        values.put(COLUMN_RECORD_NOTE, record.getNote());
        values.put(COLUMN_RECORD_DATE, record.getExpenseDate());

        // Inserting Row
        db.insert(RECORD_TABLE, null, values);
        db.close();
    }

    /**
     * This method is to update EXPENSE record
     *
     * @param record
     */
    public void updateExpense(Record record, int expenseId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RECORD_AMOUNT, record.getAmount());
        values.put(COLUMN_RECORD_CURRENCY, record.getCurrency());
        values.put(COLUMN_RECORD_TYPE, record.getRecordType());
        values.put(COLUMN_RECORD_CATEGORY, record.getCategory());
        values.put(COLUMN_RECORD_WALLET, record.getWalletTitle());
        values.put(COLUMN_RECORD_WALLET_ID, record.getWalletId());
        values.put(COLUMN_RECORD_NOTE, record.getNote());
        values.put(COLUMN_RECORD_DATE, record.getExpenseDate());

        // Inserting Row
        db.update(RECORD_TABLE, values, COLUMN_RECORD_ID+" = "+expenseId, null);
        db.close();
    }

    /**
     * This method is to get all expense record
     *
     * @param walletName
     */
    public MutableLiveData<ArrayList<Record>> getAllRecords(String walletName) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_RECORD_ID,
                COLUMN_RECORD_AMOUNT,
                COLUMN_RECORD_CATEGORY,
                COLUMN_RECORD_DATE,
                COLUMN_RECORD_CURRENCY,
                COLUMN_RECORD_TYPE,
                COLUMN_RECORD_NOTE,
                COLUMN_RECORD_WALLET,
                COLUMN_RECORD_WALLET_ID
        };
        // sorting orders
        String sortOrder =
                COLUMN_RECORD_ID + " DESC";
        MutableLiveData<ArrayList<Record>> mutableRecordList = new MutableLiveData<ArrayList<Record>>();
        ArrayList<Record> recordList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;

        Log.v("fromDBWalletName",""+walletName);
        if (TextUtils.isEmpty(walletName)) {
            cursor = db.query(RECORD_TABLE, columns, null, null, null, null, sortOrder);
        }
        else {
            String where = "" + COLUMN_RECORD_WALLET + " = '"+ walletName +"'";
            cursor = db.query(RECORD_TABLE, columns, where, null, null, null, sortOrder);
        }

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    Record record = new Record();

                    record.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_RECORD_ID)));
                    record.setAmount(cursor.getInt(cursor.getColumnIndex(COLUMN_RECORD_AMOUNT)));
                    record.setRecordType(cursor.getString(cursor.getColumnIndex(COLUMN_RECORD_TYPE)));
                    record.setCategory((cursor.getString(cursor.getColumnIndex(COLUMN_RECORD_CATEGORY))));
                    record.setExpenseDate((cursor.getString(cursor.getColumnIndex(COLUMN_RECORD_DATE))));
                    record.setCurrency(cursor.getInt(cursor.getColumnIndex(COLUMN_RECORD_CURRENCY)));
                    record.setNote(cursor.getString(cursor.getColumnIndex(COLUMN_RECORD_NOTE)));
                    record.setWalletTitle(cursor.getString(cursor.getColumnIndex(COLUMN_RECORD_WALLET)));
                    record.setWalletId(cursor.getInt(cursor.getColumnIndex(COLUMN_RECORD_WALLET_ID)));
                    Log.v("Record::","wallet : "+cursor.getString(cursor.getColumnIndex(COLUMN_RECORD_WALLET)));
                    recordList.add(record);
                } while (cursor.moveToNext());
            }
        }
        mutableRecordList.postValue(recordList);
        cursor.close();
        db.close();
        return mutableRecordList;
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
    public MutableLiveData<ArrayList<Wallet>> getAllWalletItems() {
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
        MutableLiveData<ArrayList<Wallet>> walletLiveData = new MutableLiveData<ArrayList<Wallet>>();
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
        walletLiveData.postValue(walletList);
        cursor.close();
        db.close();
        return walletLiveData;
    }

    /**
     * This method is to get wallet title
     *
     */
    public ArrayList<String> getWalletTitle(String walletType) {
        ArrayList<String> walletTitle = new ArrayList<>();
        Cursor cursor;
        String where;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String[] columns = {
                COLUMN_WALLET_TYPE,
                COLUMN_WALLET_TITLE
        };

        if (!TextUtils.isEmpty(walletType)){
            where = "" + COLUMN_WALLET_TYPE + " = '"+ walletType +"' ";
            cursor = sqLiteDatabase.query(WALLET_TABLE, columns ,where , null , null , null , null);
        }
        else{
            cursor = sqLiteDatabase.query(WALLET_TABLE, columns ,null , null , null , null , null);
        }
        int counter = 0;
        if (cursor != null){
            if (cursor.getCount() > 0){
                if (cursor.moveToFirst()){
                    walletTitle.add(0,"Select Wallet");
                    do{
                        counter++;
                        walletTitle.add(counter,cursor.getString(cursor.getColumnIndex(COLUMN_WALLET_TITLE)));
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
     * @param walletName
     */
    public int singleWalletTotalCost(String walletName){
        int totalCost = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {
                COLUMN_RECORD_ID,
                COLUMN_RECORD_WALLET,
                COLUMN_RECORD_AMOUNT
        };
        String where = "" + COLUMN_RECORD_WALLET + " = '" + walletName + "'";
        Cursor cursor = sqLiteDatabase.query(RECORD_TABLE, columns, where, null, null, null, null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    totalCost += cursor.getInt(2);
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
        String[] columns = {
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
     * This method is to get all the wallet total balance
     */
    public int getTotalWalletBalance(){
        int totalBalance = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {
                COLUMN_WALLET_ID,
                COLUMN_WALLET_TYPE,
                COLUMN_WALLET_AMOUNT
        };
        String where = "" + COLUMN_WALLET_TYPE + " = 'Expense'";
        Cursor cursor = sqLiteDatabase.query(WALLET_TABLE, columns, where, null, null, null, null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    totalBalance += cursor.getInt(2);
                }while (cursor.moveToNext());
            }
        }
        else{
            totalBalance = 0;
        }

        cursor.close();
        sqLiteDatabase.close();
        return totalBalance;
    }

    /**
     * This method is to get all the costs
     */
    public int getAllCost(){
        int totalCost = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {
                COLUMN_RECORD_AMOUNT
        };
        Cursor cursor = sqLiteDatabase.query(RECORD_TABLE, columns, null, null, null, null, null);

        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    totalCost += cursor.getInt(cursor.getColumnIndex(COLUMN_RECORD_AMOUNT));
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
     * This method is to all cost for a single month
     *
     */
    public int getCostOfMonth(String monthName) {
        int totalCost = 0;
        // array of columns to fetch
        String[] columns = {
                COLUMN_RECORD_ID,
                COLUMN_RECORD_AMOUNT,
                COLUMN_RECORD_DATE
        };
        // sorting orders
        String sortOrder =
                COLUMN_RECORD_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        String where = "" + COLUMN_RECORD_DATE + " LIKE '%"+ monthName +"%' ";

        Cursor cursor = db.query(RECORD_TABLE, columns, where, null, null, null, sortOrder);

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    totalCost += cursor.getInt(cursor.getColumnIndex(COLUMN_RECORD_AMOUNT));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return totalCost;
    }

    /**
     * This method is to all cost based on month and record type [savings or expense]
     * @param monthName
     * @param recordType
     */
    public int getCostOfMonthWithRecordType(String monthName, String recordType) {
        int totalCost = 0;
        // array of columns to fetch
        String[] columns = {
                COLUMN_RECORD_ID,
                COLUMN_RECORD_AMOUNT,
                COLUMN_RECORD_DATE,
                COLUMN_RECORD_TYPE
        };
        // sorting orders
        String sortOrder =
                COLUMN_RECORD_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        String where = "" + COLUMN_RECORD_DATE + " LIKE '%"+ monthName +"%' and " + COLUMN_RECORD_TYPE + " = '" + recordType + "'";

        Cursor cursor = db.query(RECORD_TABLE, columns, where, null, null, null, sortOrder);

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    totalCost += cursor.getInt(cursor.getColumnIndex(COLUMN_RECORD_AMOUNT));
                } while (cursor.moveToNext());
            }
        }
        else{
            totalCost = 0;
        }
        cursor.close();
        db.close();
        return totalCost;
    }

    /**
     * This method is to get all the costs based on recordType
     * @param recordType
     */
    public int getAllCostBasedOnRecord(String recordType){
        int totalCost = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {
                COLUMN_RECORD_AMOUNT,
                COLUMN_RECORD_TYPE
        };
        String where = "" + COLUMN_RECORD_TYPE + " = '"+ recordType +"' ";

        Cursor cursor = sqLiteDatabase.query(RECORD_TABLE, columns, where, null, null, null, null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    totalCost += cursor.getInt(cursor.getColumnIndex(COLUMN_RECORD_AMOUNT));
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
     * This method is to get count of expense or savings record from database
     * @param categoryLabel
     */
    public float getCategoryCount(String categoryLabel) {

        String countQuery = "SELECT  * FROM " + RECORD_TABLE +" WHERE " + COLUMN_RECORD_CATEGORY +" = '"+categoryLabel+"' ";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        float count = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();

        if (count > 0){
            return count;
        }
        else return 0;
    }
}
