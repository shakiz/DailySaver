package com.dailysaver.shadowhite.dailysaver.MvvmDs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface ThoseDAO {
    @Insert
    void insert(ExpenseModelMvvm expenseModelMvvm);

    @Delete
    void delete(ExpenseModelMvvm expenseModelMvvm);

    @Update
    void update(ExpenseModelMvvm expenseModelMvvm);

    @Query("DELETE FROM expense_table")
    void deleteAllExpenses();

    @Query("SELECT * FROM expense_table ORDER BY Id desc")
    LiveData<List<ExpenseModelMvvm>> getAllExpenses();
}
