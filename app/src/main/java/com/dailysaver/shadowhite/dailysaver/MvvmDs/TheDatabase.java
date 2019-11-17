package com.dailysaver.shadowhite.dailysaver.MvvmDs;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.dailysaver.shadowhite.dailysaver.models.expensewallet.ExpenseModel;
import com.dailysaver.shadowhite.dailysaver.models.savingswallet.WalletModel;

@Database(entities = {ExpenseModel.class},version = 2)
public abstract class TheDatabase extends RoomDatabase {
    private static TheDatabase instance;
    public abstract ExpenseDAO expenseDAO();
    public abstract SavingsDAO savingsDAO();

    public static synchronized TheDatabase getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TheDatabase.class,"daily_saver_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            new PopulateDBAsyncTask(instance).execute();
            super.onCreate(db);
        }
    };

    public static class PopulateDBAsyncTask extends AsyncTask<Void,Void,Void> {

        private ExpenseDAO expenseDAO;
        private SavingsDAO savingsDAO;

        public PopulateDBAsyncTask(TheDatabase theDatabase){
            this.expenseDAO = theDatabase.expenseDAO();
            this.savingsDAO = theDatabase.savingsDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //TODO Dummy data
            expenseDAO.insert(new ExpenseModel("Office transport expense","Transport","Nothing fancy","22-Jun-2019",50));
            expenseDAO.insert(new ExpenseModel("Food expense","Food","Nothing fancy","21-July-2019",250));
            expenseDAO.insert(new ExpenseModel("Office food expense","Food","Nothing fancy","22-Jun-2019",50));


            savingsDAO.insert(new WalletModel("Office food expense",12000,"Tk.","22-Jun-2019","Savings"));
            savingsDAO.insert(new WalletModel("Good expense",1000,"Tk.","20-Jun-2019","Expense"));
            savingsDAO.insert(new WalletModel("Office food expense",2000,"Tk.","02-July-2019","Savings"));

            return null;
        }
    }


}
