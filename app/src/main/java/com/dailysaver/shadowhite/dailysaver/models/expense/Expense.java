package com.dailysaver.shadowhite.dailysaver.models.expense;

import java.io.Serializable;

public class Expense implements Serializable {
    private int Id;
    private int Amount;
    private int Currency;
    private String Category;
    private int Wallet;
    private String Note;
    private String ExpenseDate;

    public Expense() {
    }

    public Expense(int id, int amount, int currency, String category, int walletId, String note, String expenseDate) {
        Id = id;
        Amount = amount;
        Currency = currency;
        Category = category;
        Wallet = walletId;
        Note = note;
        ExpenseDate = expenseDate;
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

    public int getWallet() {
        return Wallet;
    }

    public void setWalletId(int wallet) {
        Wallet = wallet;
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
