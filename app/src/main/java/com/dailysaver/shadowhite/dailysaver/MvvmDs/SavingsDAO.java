package com.dailysaver.shadowhite.dailysaver.MvvmDs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.dailysaver.shadowhite.dailysaver.models.savingswallet.WalletModel;
import java.util.List;

@Dao
public interface SavingsDAO {
    @Insert
    void insert(WalletModel walletModel);

    @Delete
    void delete(WalletModel walletModel);

    @Update
    void update(WalletModel walletModel);

    @Query("DELETE FROM wallet_table")
    void deleteAllWallets();

    @Query("SELECT * FROM expense_table ORDER BY Id desc")
    LiveData<List<WalletModel>> getAllWallets();
}
