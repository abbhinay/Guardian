package com.abhinay.guardian.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BlockedAppsInfoHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BlockedAppsInfo.db";
    public static final String TABLE_APPS = "apps";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PACKAGENAME = "packageName";
    public static final String COLUMN_HOURS = "hours";
    public static final String COLUMN_CURRENTHOURS = "currentHours";


    public BlockedAppsInfoHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_APPS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PACKAGENAME + " TEXT, " + COLUMN_HOURS + " INTEGER, " + COLUMN_CURRENTHOURS + " INTEGER " + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPS);
        onCreate(db);
    }

    public void addApp(BlockedAppsInfo info){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PACKAGENAME, info.get_packageName());
        values.put(COLUMN_HOURS, info.get_hours());
        values.put(COLUMN_CURRENTHOURS, info.get_currentHours());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_APPS, null, values);
        db.close();
    }

    public void deleteApp(String packageName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_APPS + " WHERE " + COLUMN_PACKAGENAME + "=\"" + packageName + "\";");
    }

//    public boolean containsApp(String packageName){
//        SQLiteDatabase db = getReadableDatabase();
//        String query = "SELECT * FROM " + TABLE_APPS + " WHERE " + COLUMN_PACKAGENAME + "=\"" + packageName + "\";";
//        Cursor c = db.rawQuery(query, null);
//        if(c==null){
//            return false;
//        }else{
//            return true;
//        }
//    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_APPS + " WHERE 1";

        //cursor point to a location in your results
        Cursor c = db.rawQuery(query, null);

        //move to the first row in your results
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("packageName"))!=null){
                dbString += c.getString(c.getColumnIndex("packageName"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }
}
