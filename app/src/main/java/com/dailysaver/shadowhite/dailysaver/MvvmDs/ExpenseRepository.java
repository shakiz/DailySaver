package com.dailysaver.shadowhite.dailysaver.MvvmDs;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.dailysaver.shadowhite.dailysaver.models.expensewallet.ExpenseModel;
import java.util.List;


public class ExpenseRepository {
    private ExpenseDAO expenseDAO;
    private LiveData<List<ExpenseModel>> allExpenses;

    public ExpenseRepository(Application application){
        TheDatabase database = TheDatabase.getInstance(application);
        expenseDAO = database.expenseDAO();
        allExpenses = expenseDAO.getAllExpenses();
    }

    public void insert(ExpenseModel expenseModel){
        new InsertExpenseAsyncTask(expenseDAO).execute(expenseModel);
    }

    public void update(ExpenseModel expenseModel){
        new UpdateExpenseAsyncTask(expenseDAO).execute(expenseModel);
    }

    public void delete(ExpenseModel expenseModel){
        new DeleteExpenseAsyncTask(expenseDAO).execute(expenseModel);
    }

    public void deleteAll(){
        new DeleteAllExpensesAsyncTask(expenseDAO).execute();
    }

    public LiveData<List<ExpenseModel>> getAllExpenses(){
        return allExpenses;
    }

    public static class InsertExpenseAsyncTask extends AsyncTask<ExpenseModel,Void,Void>{

        private ExpenseDAO expenseDAO;

        public InsertExpenseAsyncTask(ExpenseDAO expenseDAO){
            this.expenseDAO = expenseDAO;
        }

        @Override
        protected Void doInBackground(ExpenseModel... expenseModels) {
            expenseDAO.insert(expenseModels[0]);
            return null;
        }
    }

    public static class UpdateExpenseAsyncTask extends AsyncTask<ExpenseModel,Void,Void>{

        private ExpenseDAO expenseDAO;

        public UpdateExpenseAsyncTask(ExpenseDAO expenseDAO){
            this.expenseDAO = expenseDAO;
        }

        @Override
        protected Void doInBackground(ExpenseModel... expenseModels) {
            expenseDAO.update(expenseModels[0]);
            return null;
        }
    }

    public static class DeleteExpenseAsyncTask extends AsyncTask<ExpenseModel,Void,Void>{

        private ExpenseDAO expenseDAO;

        public DeleteExpenseAsyncTask(ExpenseDAO expenseDAO){
            this.expenseDAO = expenseDAO;
        }

        @Override
        protected Void doInBackground(ExpenseModel... expenseModels) {
            expenseDAO.delete(expenseModels[0]);
            return null;
        }
    }

    public static class DeleteAllExpensesAsyncTask extends AsyncTask<Void,Void,Void>{

        private ExpenseDAO expenseDAO;

        public DeleteAllExpensesAsyncTask(ExpenseDAO expenseDAO){
            this.expenseDAO = expenseDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            expenseDAO.deleteAllExpenses();
            return null;
        }
    }
}
