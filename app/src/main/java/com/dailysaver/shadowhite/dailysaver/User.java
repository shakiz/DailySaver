package com.dailysaver.shadowhite.dailysaver;

public class User {
    //private variables
    private int _id;
    private double _savings;
    private double _current;
    private double _inerestRate;
    private String time;
    private String date;


    // Empty constructor
    public User(){

    }
    // constructor
    public User(int id, double savings, double current, double inerestRate,String time,String date){
        this._id = id;
        this._savings = savings;
        this._current = current;
        this._inerestRate = inerestRate;
        this.time=time;
        this.date=date;
    }

    // constructor
    public User(double savings, double current, double inerestRate,String time,String date){
        this._savings = savings;
        this._current = current;
        this._inerestRate = inerestRate;
        this.time=time;
        this.date=date;
    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public double get_savings() {
        return _savings;
    }

    public void set_savings(double _savings) {
        this._savings = _savings;
    }

    public double get_current() {
        return _current;
    }

    public void set_current(double _current) {
        this._current = _current;
    }

    public double get_inerestRate() {
        return _inerestRate;
    }

    public void set_inerestRate(double _inerestRate) {
        this._inerestRate = _inerestRate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
