package com.dailysaver.shadowhite.dailysaver;

public class Category {
    private String Title;
    private int IconRes;

    public Category(String title, int iconRes) {
        this.Title = title;
        this.IconRes = iconRes;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getIconRes() {
        return IconRes;
    }

    public void setIconRes(int iconRes) {
        IconRes = iconRes;
    }
}

