package com.dailysaver.shadowhite.dailysaver.MvvmDs;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.ArrayList;
import java.util.List;


public class TheRepository {
    private ThoseDAO thoseDAO;
    private LiveData<List<ExpenseModelMvvm>> allExpenses;

    public TheRepository(Application application){
        TheDatabase database = TheDatabase.getInstance(application);
        thoseDAO = database.expenseDAO();
        allExpenses = thoseDAO.getAllExpenses();
    }

    public void insert(ExpenseModelMvvm expenseModelMvvm){
        new InsertExpenseAsyncTask(thoseDAO).execute(expenseModelMvvm);
    }

    public void update(ExpenseModelMvvm expenseModelMvvm){
        new UpdateExpenseAsyncTask(thoseDAO).execute(expenseModelMvvm);
    }

    public void delete(ExpenseModelMvvm expenseModelMvvm){
        new DeleteExpenseAsyncTask(thoseDAO).execute(expenseModelMvvm);
    }

    public void deleteAll(){
        new DeleteAllExpensesAsyncTask(thoseDAO).execute();
    }

    public LiveData<List<ExpenseModelMvvm>> getAllExpenses(){
        return allExpenses;
    }

    public static class InsertExpenseAsyncTask extends AsyncTask<ExpenseModelMvvm,Void,Void>{

        private ThoseDAO thoseDAO;

        public InsertExpenseAsyncTask(ThoseDAO thoseDAO){
            this.thoseDAO = thoseDAO;
        }

        @Override
        protected Void doInBackground(ExpenseModelMvvm... expenseModelMvvms) {
            thoseDAO.insert(expenseModelMvvms[0]);
            return null;
        }
    }

    public static class UpdateExpenseAsyncTask extends AsyncTask<ExpenseModelMvvm,Void,Void>{

        private ThoseDAO thoseDAO;

        public UpdateExpenseAsyncTask(ThoseDAO thoseDAO){
            this.thoseDAO = thoseDAO;
        }

        @Override
        protected Void doInBackground(ExpenseModelMvvm... expenseModelMvvms) {
            thoseDAO.update(expenseModelMvvms[0]);
            return null;
        }
    }

    public static class DeleteExpenseAsyncTask extends AsyncTask<ExpenseModelMvvm,Void,Void>{

        private ThoseDAO thoseDAO;

        public DeleteExpenseAsyncTask(ThoseDAO thoseDAO){
            this.thoseDAO = thoseDAO;
        }

        @Override
        protected Void doInBackground(ExpenseModelMvvm... expenseModelMvvms) {
            thoseDAO.delete(expenseModelMvvms[0]);
            return null;
        }
    }

    public static class DeleteAllExpensesAsyncTask extends AsyncTask<Void,Void,Void>{

        private ThoseDAO thoseDAO;

        public DeleteAllExpensesAsyncTask(ThoseDAO thoseDAO){
            this.thoseDAO = thoseDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            thoseDAO.deleteAllExpenses();
            return null;
        }
    }
}
