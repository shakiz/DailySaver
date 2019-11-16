package com.dailysaver.shadowhite.dailysaver.models.savingswallet;

public class WalletModel {
    private int Id;
    private String Title;
    private int Amount;
    private String Currency;
    private String ExpiresOn;
    private String WalletType;


    public WalletModel(String title, int amount, String currency, String expiresOn, String walletType) {
        Title = title;
        Amount = amount;
        Currency = currency;
        ExpiresOn = expiresOn;
        WalletType = walletType;
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

    public void setTitle(String title) {
        Title = title;
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

    public String getExpiresOn() {
        return ExpiresOn;
    }

    public void setExpiresOn(String expiresOn) {
        ExpiresOn = expiresOn;
    }

    public String getWalletType() {
        return WalletType;
    }

    public void setWalletType(String walletType) {
        WalletType = walletType;
    }
}
