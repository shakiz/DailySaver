package com.dailysaver.shadowhite.dailysaver.models.expensewallet;

public class Expense {
    private int Id;
    private String Category;
    private String Currency;
    private String Note;
    private String ExpenseDate;
    private int Amount;

    public Expense() {
    }


    public Expense(String category, String currency, String note, String expenseDate, int amount) {
        Category = category;
        Currency = currency;
        Note = note;
        ExpenseDate = expenseDate;
        Amount = amount;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
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

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
