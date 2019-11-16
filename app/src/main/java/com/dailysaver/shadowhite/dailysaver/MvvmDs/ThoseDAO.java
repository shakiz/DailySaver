package com.dailysaver.shadowhite.dailysaver.MvvmDs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.dailysaver.shadowhite.dailysaver.models.expensewallet.ExpenseModel;
import java.util.List;

@Dao
public interface ThoseDAO {
    @Insert
    void insert(ExpenseModel expenseModel);

    @Delete
    void delete(ExpenseModel expenseModel);

    @Update
    void update(ExpenseModel expenseModel);

    @Query("DELETE FROM expense_table")
    void deleteAllExpenses();

    @Query("SELECT * FROM expense_table ORDER BY Id desc")
    LiveData<List<ExpenseModel>> getAllExpenses();
}
