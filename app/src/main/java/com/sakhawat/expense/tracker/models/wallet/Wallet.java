package com.sakhawat.expense.tracker.models.wallet;

import java.io.Serializable;

public class Wallet implements Serializable {
    private int Id;
    private int Amount;
    private String Title;
    private int Currency;
    private String WalletType;
    private String Note;
    private String ExpiresOn;


    public Wallet(String title, int amount, int currency, String expiresOn, String walletType,String note) {
        Title = title;
        Amount = amount;
        Currency = currency;
        ExpiresOn = expiresOn;
        WalletType = walletType;
        Note = note;
    }

    public Wallet() {
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

    public int getCurrency() {
        return Currency;
    }

    public void setCurrency(int currency) {
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

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
