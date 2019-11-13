package com.dailysaver.shadowhite.dailysaver.models.wallet;

public class WalletModel {
    private String Title,ExpiresOn,Type;
    private int TodayCost,TotalCost,RemainingBalance;

    public WalletModel(String title, String expiresOn, String type, int todayCost, int totalCost, int remainingBalance) {
        Title = title;
        ExpiresOn = expiresOn;
        Type = type;
        TodayCost = todayCost;
        TotalCost = totalCost;
        RemainingBalance = remainingBalance;
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

    public String getExpiresOn() {
        return ExpiresOn;
    }

    public void setExpiresOn(String expiresOn) {
        ExpiresOn = expiresOn;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
