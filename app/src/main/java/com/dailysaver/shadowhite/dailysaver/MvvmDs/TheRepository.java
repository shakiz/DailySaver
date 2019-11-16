package com.dailysaver.shadowhite.dailysaver.MvvmDs;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.dailysaver.shadowhite.dailysaver.models.expensewallet.ExpenseModel;
import java.util.List;


public class TheRepository {
    private ThoseDAO thoseDAO;
    private LiveData<List<ExpenseModel>> allExpenses;

    public TheRepository(Application application){
        TheDatabase database = TheDatabase.getInstance(application);
        thoseDAO = database.expenseDAO();
        allExpenses = thoseDAO.getAllExpenses();
    }

    public void insert(ExpenseModel expenseModel){
        new InsertExpenseAsyncTask(thoseDAO).execute(expenseModel);
    }

    public void update(ExpenseModel expenseModel){
        new UpdateExpenseAsyncTask(thoseDAO).execute(expenseModel);
    }

    public void delete(ExpenseModel expenseModel){
        new DeleteExpenseAsyncTask(thoseDAO).execute(expenseModel);
    }

    public void deleteAll(){
        new DeleteAllExpensesAsyncTask(thoseDAO).execute();
    }

    public LiveData<List<ExpenseModel>> getAllExpenses(){
        return allExpenses;
    }

    public static class InsertExpenseAsyncTask extends AsyncTask<ExpenseModel,Void,Void>{

        private ThoseDAO thoseDAO;

        public InsertExpenseAsyncTask(ThoseDAO thoseDAO){
            this.thoseDAO = thoseDAO;
        }

        @Override
        protected Void doInBackground(ExpenseModel... expenseModels) {
            thoseDAO.insert(expenseModels[0]);
            return null;
        }
    }

    public static class UpdateExpenseAsyncTask extends AsyncTask<ExpenseModel,Void,Void>{

        private ThoseDAO thoseDAO;

        public UpdateExpenseAsyncTask(ThoseDAO thoseDAO){
            this.thoseDAO = thoseDAO;
        }

        @Override
        protected Void doInBackground(ExpenseModel... expenseModels) {
            thoseDAO.update(expenseModels[0]);
            return null;
        }
    }

    public static class DeleteExpenseAsyncTask extends AsyncTask<ExpenseModel,Void,Void>{

        private ThoseDAO thoseDAO;

        public DeleteExpenseAsyncTask(ThoseDAO thoseDAO){
            this.thoseDAO = thoseDAO;
        }

        @Override
        protected Void doInBackground(ExpenseModel... expenseModels) {
            thoseDAO.delete(expenseModels[0]);
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
