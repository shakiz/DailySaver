package com.dailysaver.shadowhite.dailysaver.models.expensewallet;

public class ExpenseModel {
    private String Title,Type,ExpenseDate;
    private int Amount;

    public ExpenseModel(String title, String type, int amount, String expenseDate) {
        Title = title;
        Type = type;
        Amount = amount;
        ExpenseDate = expenseDate;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }


    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getExpenseDate() {
        return ExpenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        ExpenseDate = expenseDate;
    }
}
