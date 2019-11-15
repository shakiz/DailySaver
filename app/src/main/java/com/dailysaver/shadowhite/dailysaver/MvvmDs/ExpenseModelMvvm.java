package com.dailysaver.shadowhite.dailysaver.MvvmDs;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_table")
public class ExpenseModelMvvm {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String Title;
    private String Type;
    private String ExpenseDate;
    private int Amount;

    public ExpenseModelMvvm(String title, String type, int amount, String expenseDate) {
        Title = title;
        Type = type;
        Amount = amount;
        ExpenseDate = expenseDate;
    }

    public ExpenseModelMvvm() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public String getType() {
        return Type;
    }

    public String getExpenseDate() {
        return ExpenseDate;
    }

    public int getAmount() {
        return Amount;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setExpenseDate(String expenseDate) {
        ExpenseDate = expenseDate;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

}
