package com.dailysaver.shadowhite.dailysaver.models.expense;

import java.io.Serializable;

public class Expense implements Serializable {
    private int Id;
    private int Amount;
    private int Currency;
    private String Category;
    private String WalletTitle;
    private int WalletId;
    private String Note;
    private String ExpenseDate;

    public Expense() {
    }

    public Expense(int amount, int currency, String category, String walletTitle,int walletId, String note, String expenseDate) {
        this.Amount = amount;
        this.Currency = currency;
        this.Category = category;
        this.WalletTitle = walletTitle;
        this.WalletId = walletId;
        this.Note = note;
        this.ExpenseDate = expenseDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getCurrency() {
        return Currency;
    }

    public void setCurrency(int currency) {
        Currency = currency;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getWalletTitle() {
        return WalletTitle;
    }

    public void setWalletTitle(String walletTitle) {
        WalletTitle = walletTitle;
    }

    public int getWalletId() {
        return WalletId;
    }

    public void setWalletId(int walletId) {
        WalletId = walletId;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getExpenseDate() {
        return ExpenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        ExpenseDate = expenseDate;
    }
}
