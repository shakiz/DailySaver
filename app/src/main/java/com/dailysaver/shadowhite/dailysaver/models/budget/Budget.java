package com.dailysaver.shadowhite.dailysaver.models.budget;

import java.io.Serializable;

public class Budget implements Serializable {
    private int Id;
    private int Amount;
    private String Currency;
    private String Category;
    private String Wallet;
    private String WalletType;
    private String Note;
    private String ExpenseDate;

    public Budget() {
    }

    public Budget(int id, int amount, String currency, String category, String walletId, String walletTypeId, String note, String expenseDate) {
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

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getWallet() {
        return Wallet;
    }

    public void setWalletId(String wallet) {
        Wallet = wallet;
    }

    public String getWalletTypeId() {
        return WalletType;
    }

    public void setWalletTypeId(String walletType) {
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
