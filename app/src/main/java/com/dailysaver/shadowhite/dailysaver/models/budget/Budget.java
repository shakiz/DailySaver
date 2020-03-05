package com.dailysaver.shadowhite.dailysaver.models.budget;

public class Budget {
    private int Id;
    private int Amount;
    private String Currency;
    private String Category;
    private int WalletId;
    private int WalletTypeId;
    private String Note;
    private String ExpenseDate;

    public Budget() {
    }

    public Budget(int id, int amount, String currency, String category, int walletId, int walletTypeId, String note, String expenseDate) {
        Id = id;
        Amount = amount;
        Currency = currency;
        Category = category;
        WalletId = walletId;
        WalletTypeId = walletTypeId;
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

    public int getWalletId() {
        return WalletId;
    }

    public void setWalletId(int walletId) {
        WalletId = walletId;
    }

    public int getWalletTypeId() {
        return WalletTypeId;
    }

    public void setWalletTypeId(int walletTypeId) {
        WalletTypeId = walletTypeId;
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
