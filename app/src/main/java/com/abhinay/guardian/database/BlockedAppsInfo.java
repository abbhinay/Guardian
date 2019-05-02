package com.abhinay.guardian.database;

public class BlockedAppsInfo {
    private int _id;
    private String _packageName;
    private int _hours;
    private int _currentHours;

    public BlockedAppsInfo(){

    }

    public BlockedAppsInfo(String packageName, int hours){
        this._packageName = packageName;
        this._hours = hours;
        this._currentHours = 0;
    }

    public void set_id(int id){
        this._id = id;
    }

    public void set_packageName(String packageName){
        this._packageName = packageName;
    }

    public void set_hours(int hours){
        this._hours = hours;
    }

    public void set_currentHours(int currentHours){
        this._currentHours = currentHours;
    }

    public int get_id(){
        return _id;
    }

    public String get_packageName(){
        return _packageName;
    }

    public int get_hours(){
        return _hours;
    }

    public int get_currentHours(){
        return _currentHours;
    }
}
