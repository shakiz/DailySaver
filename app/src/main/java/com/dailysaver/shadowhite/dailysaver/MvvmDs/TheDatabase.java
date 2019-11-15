package com.dailysaver.shadowhite.dailysaver.MvvmDs;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ExpenseModelMvvm.class},version = 2)
public abstract class TheDatabase extends RoomDatabase {
    private static TheDatabase instance;
    public abstract ThoseDAO expenseDAO();

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

        private ThoseDAO thoseDAO;

        public PopulateDBAsyncTask(TheDatabase theDatabase){
            this.thoseDAO = theDatabase.expenseDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //TODO Dummy data
            thoseDAO.insert(new ExpenseModelMvvm("Office transport expense","Transport","Nothing fancy","22-Jun-2019",50));
            thoseDAO.insert(new ExpenseModelMvvm("Food expense","Food","Nothing fancy","21-July-2019",250));
            thoseDAO.insert(new ExpenseModelMvvm("Office food expense","Food","Nothing fancy","22-Jun-2019",50));
            return null;
        }
    }
}
