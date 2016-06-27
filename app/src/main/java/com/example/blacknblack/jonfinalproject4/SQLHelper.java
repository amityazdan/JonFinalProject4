package com.example.blacknblack.jonfinalproject4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLHelper extends SQLiteOpenHelper {



    public SQLHelper(Context context) {
        super(context, "places.db", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String command=" CREATE TABLE "+DBConstants.TABLENAME+"( "+DBConstants.ID+"   INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBConstants.NAME+" TEXT , " +
                DBConstants.ADDRESS+" TEXT , " +
                DBConstants.ICON+" TEXT , " +
                DBConstants.OPEN+" REAL , " +
                DBConstants.PHOTO+" TEXT , " +
                DBConstants.TYPE+" TEXT , " +
                DBConstants.LATITUDE+" REAL , " +
                DBConstants.LONGITUDE+" REAL  )";
        db.execSQL(command);

        String command2=" CREATE TABLE "+ DBfavorites.TABLENAME+"( "+ DBfavorites.ID+"   INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBfavorites.NAME+" TEXT , " +
                DBfavorites.ADDRESS+" TEXT , " +
                DBfavorites.ICON+" TEXT , " +
                DBfavorites.OPEN+" REAL , " +
                DBfavorites.PHOTO+" TEXT , " +
                DBfavorites.TYPE+" TEXT , " +
                DBfavorites.LATITUDE+" REAL , " +
                DBfavorites.LONGITUDE+" REAL  )";
        db.execSQL(command2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

}
