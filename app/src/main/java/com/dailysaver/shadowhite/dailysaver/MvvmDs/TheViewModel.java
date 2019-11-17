package com.dailysaver.shadowhite.dailysaver.MvvmDs;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.dailysaver.shadowhite.dailysaver.models.expensewallet.ExpenseModel;
import com.dailysaver.shadowhite.dailysaver.models.savingswallet.WalletModel;

import java.util.List;


public class TheViewModel extends AndroidViewModel {
    private ExpenseRepository expenseRepository;
    private SavingsRepository savingsRepository;
    private LiveData<List<ExpenseModel>> allExpenses;
    private LiveData<List<WalletModel>> allWallets;

    public TheViewModel(@NonNull Application application) {
        super(application);
        expenseRepository = new ExpenseRepository(application);
        savingsRepository = new SavingsRepository(application);
        allExpenses = expenseRepository.getAllExpenses();
        allWallets = savingsRepository.getAllWallets();
    }

    public void insert(ExpenseModel expenseModel){
        expenseRepository.insert(expenseModel);
    }

    public void insert(WalletModel walletModel){
        savingsRepository.insert(walletModel);
    }

    public void update(ExpenseModel expenseModel){
        expenseRepository.update(expenseModel);
    }

    public void update(WalletModel walletModel){
        savingsRepository.update(walletModel);
    }

    public void delete(ExpenseModel expenseModel){
        expenseRepository.delete(expenseModel);
    }

    public void delete(WalletModel walletModel){
        savingsRepository.delete(walletModel);
    }

    public void deleteAll(){
        expenseRepository.deleteAll();
    }

    public void deleteAllWallets(){
        savingsRepository.deleteAllWallets();
    }

    public LiveData<List<ExpenseModel>> getAllExpenses(){
        return allExpenses;
    }

    public LiveData<List<WalletModel>> getAllWallets(){
        return allWallets;
    }
}
