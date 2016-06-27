package com.example.blacknblack.jonfinalproject4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by jbt on 5/18/2016.
 */
public class DBHelper {


    Context context;


    public DBHelper(Context context) {
        this.context = context;
    }

    public void addPlace(MyPlace p) {

        SQLHelper sqlHelper = new SQLHelper(context);


        ContentValues cv = new ContentValues();
        cv.put(DBConstants.NAME, p.getName());
        cv.put(DBConstants.ADDRESS, p.getAddress());
        cv.put(DBConstants.ICON, p.getIcon());
        cv.put(DBConstants.OPEN, p.getOpen());
        cv.put(DBConstants.LATITUDE, p.getLatitude());
        cv.put(DBConstants.LONGITUDE, p.getLongitude());
        cv.put(DBConstants.PHOTO, p.getPhoto());
        cv.put(DBConstants.TYPE, p.getType());

        sqlHelper.getWritableDatabase().insert(DBConstants.TABLENAME, null, cv);
        context.getContentResolver().notifyChange(Uri.parse("content://com.example.blacknblack.jonfinalproject4.tablename/" + DBConstants.TABLENAME), null);


    }

    public void addToFavorites(MyPlace p) {

        SQLHelper sqlHelper = new SQLHelper(context);

        ContentValues cv = new ContentValues();
        cv.put(DBfavorites.NAME, p.getName());
        cv.put(DBfavorites.ADDRESS, p.getAddress());
        cv.put(DBfavorites.ICON, p.getIcon());
        cv.put(DBfavorites.OPEN, p.getOpen());
        cv.put(DBfavorites.LATITUDE, p.getLatitude());
        cv.put(DBfavorites.LONGITUDE, p.getLongitude());
        cv.put(DBfavorites.PHOTO, p.getPhoto());
        cv.put(DBfavorites.TYPE, p.getType());

        sqlHelper.getWritableDatabase().insert(DBfavorites.TABLENAME, null, cv);
        Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();

    }


    public Cursor getAllPlacesAsCursor() {

        SQLHelper mySQLHelper = new SQLHelper(context);

        Cursor cursor = mySQLHelper.getReadableDatabase().query(DBConstants.TABLENAME, null, null, null, null, null, null);

        return cursor;
    }

    public void deleteAllPlaces() {
        SQLHelper mySQLHelper = new SQLHelper(context);
        mySQLHelper.getWritableDatabase().delete(DBConstants.TABLENAME, null, null);

    }
}
