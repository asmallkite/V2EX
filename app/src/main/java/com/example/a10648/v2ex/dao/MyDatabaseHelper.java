package com.example.a10648.v2ex.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 10648 on 2016/7/16 0016.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_MEMBER = " CREATE TABLE IF NOT EXISTS Topic ( " +
            " id integer primary key autoincrement , " +
            " title text ,  " +
            " url text , " +
            " content text ," +
            " avatar text , " +
            " username text , " +
            " created integer , " +
            " replies integer , " +
            " nodename text ) ";

    public static final String CREATE_MEMBER_2 = " CREATE TABLE IF NOT EXISTS Topic2 ( " +
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
        db.execSQL(CREATE_MEMBER_2);
//        Toast.makeText(mContext, "Create table", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //虽然这个方法简单粗暴，但怒前不需要 oldVersion的判断
        db.execSQL("drop table if exists Topic");
        db.execSQL("drop table if exists Topic2");
        onCreate(db);

    }
}
