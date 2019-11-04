package com.dailysaver.shadowhite.dailysaver;

public class CardItemModel {
    private String Title;
    private int TodayCost,TotalCost,RemainingBalance;

    public CardItemModel(String title, int todayCost, int totalCost, int remainingBalance) {
        this.Title = title;
        this.TodayCost = todayCost;
        this.TotalCost = totalCost;
        this.RemainingBalance = remainingBalance;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public int getTodayCost() {
        return TodayCost;
    }

    public void setTodayCost(int todayCost) {
        this.TodayCost = todayCost;
    }

    public int getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(int totalCost) {
        this.TotalCost = totalCost;
    }

    public int getRemainingBalance() {
        return RemainingBalance;
    }

    public void setRemainingBalance(int remainingBalance) {
        this.RemainingBalance = remainingBalance;
    }
}
