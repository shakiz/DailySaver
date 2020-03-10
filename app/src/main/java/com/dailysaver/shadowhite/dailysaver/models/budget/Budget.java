package com.dailysaver.shadowhite.dailysaver.models.budget;

import java.io.Serializable;

public class Budget implements Serializable {
    private int Id;
    private int Amount;
    private int Currency;
    private String Category;
    private int Wallet;
    private int WalletType;
    private String Note;
    private String ExpenseDate;

    public Budget() {
    }

    public Budget(int id, int amount, int currency, String category, int walletId, int walletTypeId, String note, String expenseDate) {
        Id = id;
        Amount = amount;
        Currency = currency;
        Category = category;
        Wallet = walletId;
        WalletType = walletTypeId;
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

    public int getWalletTypeId() {
        return WalletType;
    }

    public void setWalletTypeId(int walletType) {
        WalletType = walletType;
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
