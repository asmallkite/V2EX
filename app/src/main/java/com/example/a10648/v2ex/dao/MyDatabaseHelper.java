package com.example.a10648.v2ex.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 10648 on 2016/7/16 0016.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_MEMBER = " create table Topic ( " +
            " id integer primary key autoincrement , " +
            " title text ,  " +
            " url text , " +
            " content text ," +
            " avatar text , " +
            " username text , " +
            " created integer , " +
            " replies integer , " +
            " nodename text ) ";


    private Context mContext;


    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MEMBER);
        Toast.makeText(mContext, "Create table", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
