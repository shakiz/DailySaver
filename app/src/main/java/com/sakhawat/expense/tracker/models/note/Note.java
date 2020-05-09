package com.sakhawat.expense.tracker.models.note;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String Title;
    private String Description;
    private String Date;

    public Note() {
    }

    public Note(String title, String description, String date) {
        this.Title = title;
        this.Description = description;
        this.Date = date;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getDate() {
        return Date;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setDate(String date) {
        Date = date;
    }
}
