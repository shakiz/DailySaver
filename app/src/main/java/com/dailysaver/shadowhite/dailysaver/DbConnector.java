package com.dailysaver.shadowhite.dailysaver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DbConnector extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 7;
    // Database Name
    private static final String DATABASE_NAME = "DataSaver.db";

    // ExpenseModel table name
    private static final String TABLE_USER = "savingsaccountdata";

    // ExpenseModel Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_SAVINGS_AMOUNT = "savings_amount";
    private static final String COLUMN_USER_CURRENT_AMOUNT = "current_amount";
    private static final String COLUMN_INTEREST_RATE = "interest_rate";
    private static final String COLUMN_TIME= "time_of_calculation";
    private static final String COLUMN_DATE= "date_of_calculation";

    // create table sql query
    //for user table
    private String CREATE_USER_LOG_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_USER_SAVINGS_AMOUNT + " DOUBLE NOT NULL,"
            + COLUMN_USER_CURRENT_AMOUNT + " DOUBLE NOT NULL,"
            + COLUMN_INTEREST_RATE + " DOUBLE NOT NULL,"
            + COLUMN_TIME + " TEXT NOT NULL,"
            + COLUMN_DATE + " TEXT NOT NULL"
            + ")";

    // drop table sql query
    private String DROP_USER_LOG_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    public DbConnector(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_USER_LOG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL(DROP_USER_LOG_TABLE);
        //create tables again
        onCreate(database);
    }

    //this is for user registration
    public boolean insertData(double savingsAmount,double currentAmount,double interestRate,String time,String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_SAVINGS_AMOUNT,savingsAmount);
        values.put(COLUMN_USER_CURRENT_AMOUNT, currentAmount);
        values.put(COLUMN_INTEREST_RATE,interestRate);
        values.put(COLUMN_TIME, time);
        Log.e("TIME","TIME IS : "+time);
        values.put(COLUMN_DATE,date);

        // Inserting Row
        try{
            db.insert(TABLE_USER,   null, values);
        }catch(Exception e){
            Log.d("ERROR","Error in : "+e.getMessage());
        }
        db.close();
        return true;
    }


}
