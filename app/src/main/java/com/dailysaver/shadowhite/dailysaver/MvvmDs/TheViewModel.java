package com.dailysaver.shadowhite.dailysaver.MvvmDs;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;


public class TheViewModel extends AndroidViewModel {
    private TheRepository repository;
    private LiveData<List<ExpenseModelMvvm>> allExpenses;

    public TheViewModel(@NonNull Application application) {
        super(application);
        repository = new TheRepository(application);
        allExpenses = repository.getAllExpenses();
    }

    public void insert(ExpenseModelMvvm expenseModel){
        repository.insert(expenseModel);
    }

    public void update(ExpenseModelMvvm expenseModel){
        repository.update(expenseModel);
    }

    public void delete(ExpenseModelMvvm expenseModel){
        repository.delete(expenseModel);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<ExpenseModelMvvm>> getAllExpenses(){
        return allExpenses;
    }
}
