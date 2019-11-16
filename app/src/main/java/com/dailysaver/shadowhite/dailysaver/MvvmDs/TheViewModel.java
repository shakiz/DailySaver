package com.dailysaver.shadowhite.dailysaver.MvvmDs;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.dailysaver.shadowhite.dailysaver.models.expensewallet.ExpenseModel;
import java.util.List;


public class TheViewModel extends AndroidViewModel {
    private TheRepository repository;
    private LiveData<List<ExpenseModel>> allExpenses;

    public TheViewModel(@NonNull Application application) {
        super(application);
        repository = new TheRepository(application);
        allExpenses = repository.getAllExpenses();
    }

    public void insert(ExpenseModel expenseModel){
        repository.insert(expenseModel);
    }

    public void update(ExpenseModel expenseModel){
        repository.update(expenseModel);
    }

    public void delete(ExpenseModel expenseModel){
        repository.delete(expenseModel);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<ExpenseModel>> getAllExpenses(){
        return allExpenses;
    }
}
