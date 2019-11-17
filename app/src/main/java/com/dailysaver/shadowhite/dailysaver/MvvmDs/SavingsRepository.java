package com.dailysaver.shadowhite.dailysaver.MvvmDs;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.dailysaver.shadowhite.dailysaver.models.expensewallet.ExpenseModel;
import com.dailysaver.shadowhite.dailysaver.models.savingswallet.WalletModel;

import java.util.List;


public class SavingsRepository {
    private SavingsDAO savingsDAO;
    private LiveData<List<WalletModel>> allWallets;

    public SavingsRepository(Application application){
        TheDatabase database = TheDatabase.getInstance(application);
        savingsDAO = database.savingsDAO();
        allWallets = savingsDAO.getAllWallets();
    }

    public void insert(WalletModel walletModel){
        new InsertWalletAsyncTask(savingsDAO).execute(walletModel);
    }

    public void update(WalletModel walletModel){
        new UpdateWalletAsyncTask(savingsDAO).execute(walletModel);
    }

    public void delete(WalletModel walletModel){
        new DeleteWalletAsyncTask(savingsDAO).execute(walletModel);
    }

    public void deleteAllWallets(){
        new DeleteAllWalletsAsyncTask(savingsDAO).execute();
    }

    public LiveData<List<WalletModel>> getAllWallets(){
        return allWallets;
    }

    public static class InsertWalletAsyncTask extends AsyncTask<WalletModel,Void,Void>{

        private SavingsDAO savingsDAO;

        public InsertWalletAsyncTask(SavingsDAO savingsDAO){
            this.savingsDAO = savingsDAO;
        }

        @Override
        protected Void doInBackground(WalletModel... walletModels) {
            savingsDAO.insert(walletModels[0]);
            return null;
        }
    }

    public static class UpdateWalletAsyncTask extends AsyncTask<WalletModel,Void,Void>{

        private SavingsDAO savingsDAO;

        public UpdateWalletAsyncTask(SavingsDAO savingsDAO){
            this.savingsDAO= savingsDAO;
        }

        @Override
        protected Void doInBackground(WalletModel... walletModels) {
            savingsDAO.update(walletModels[0]);
            return null;
        }
    }

    public static class DeleteWalletAsyncTask extends AsyncTask<WalletModel,Void,Void>{

        private SavingsDAO savingsDAO;

        public DeleteWalletAsyncTask(SavingsDAO savingsDAO){
            this.savingsDAO = savingsDAO;
        }

        @Override
        protected Void doInBackground(WalletModel... walletModels) {
            savingsDAO.delete(walletModels[0]);
            return null;
        }
    }

    public static class DeleteAllWalletsAsyncTask extends AsyncTask<Void,Void,Void>{

        private SavingsDAO savingsDAO;

        public DeleteAllWalletsAsyncTask(SavingsDAO savingsDAO){
            this.savingsDAO = savingsDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            savingsDAO.deleteAllWallets();
            return null;
        }
    }
}
