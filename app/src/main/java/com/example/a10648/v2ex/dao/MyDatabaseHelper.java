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
        db.execSQL(tech);
        db.execSQL(creative);
        db.execSQL(play);
        db.execSQL(apple);
        db.execSQL(jobs);
        db.execSQL(deals);
        db.execSQL(city);
        db.execSQL(qna);
        db.execSQL(alls);
        db.execSQL(r2);
//        Toast.makeText(mContext, "Create table", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //虽然这个方法简单粗暴，但怒前不需要 oldVersion的判断
        db.execSQL("drop table if exists Topic");
        db.execSQL("drop table if exists Topic2");
        db.execSQL("drop table if exists tech");
        db.execSQL("drop table if exists creative");
        db.execSQL("drop table if exists play");
        db.execSQL("drop table if exists apple");
        db.execSQL("drop table if exists jobs");
        db.execSQL("drop table if exists deals");
        db.execSQL("drop table if exists city");
        db.execSQL("drop table if exists qna");
        db.execSQL("drop table if exists alls");
        db.execSQL("drop table if exists r2");

        onCreate(db);

    }


    public static final String tech = " CREATE TABLE IF NOT EXISTS tech ( " +
            " id integer primary key autoincrement , " +
            " Javatar text ,  " +
            " Jurl text , " +
            " Jtitle text ," +
            " Jnodename text , " +
            " Jcreated text , " +
            " Jusername text , " +
            " Jreplies text ) ";

    public static final String creative = " CREATE TABLE IF NOT EXISTS creative ( " +
            " id integer primary key autoincrement , " +
            " Javatar text ,  " +
            " Jurl text , " +
            " Jtitle text ," +
            " Jnodename text , " +
            " Jcreated text , " +
            " Jusername text , " +
            " Jreplies text ) ";

    public static final String play = " CREATE TABLE IF NOT EXISTS play ( " +
            " id integer primary key autoincrement , " +
            " Javatar text ,  " +
            " Jurl text , " +
            " Jtitle text ," +
            " Jnodename text , " +
            " Jcreated text , " +
            " Jusername text , " +
            " Jreplies text ) ";

    public static final String apple = " CREATE TABLE IF NOT EXISTS apple ( " +
            " id integer primary key autoincrement , " +
            " Javatar text ,  " +
            " Jurl text , " +
            " Jtitle text ," +
            " Jnodename text , " +
            " Jcreated text , " +
            " Jusername text , " +
            " Jreplies text ) ";

    public static final String jobs = " CREATE TABLE IF NOT EXISTS jobs ( " +
            " id integer primary key autoincrement , " +
            " Javatar text ,  " +
            " Jurl text , " +
            " Jtitle text ," +
            " Jnodename text , " +
            " Jcreated text , " +
            " Jusername text , " +
            " Jreplies text ) ";

    public static final String deals = " CREATE TABLE IF NOT EXISTS deals ( " +
            " id integer primary key autoincrement , " +
            " Javatar text ,  " +
            " Jurl text , " +
            " Jtitle text ," +
            " Jnodename text , " +
            " Jcreated text , " +
            " Jusername text , " +
            " Jreplies text ) ";

    public static final String city = " CREATE TABLE IF NOT EXISTS city ( " +
            " id integer primary key autoincrement , " +
            " Javatar text ,  " +
            " Jurl text , " +
            " Jtitle text ," +
            " Jnodename text , " +
            " Jcreated text , " +
            " Jusername text , " +
            " Jreplies text ) ";

    public static final String qna = " CREATE TABLE IF NOT EXISTS qna ( " +
            " id integer primary key autoincrement , " +
            " Javatar text ,  " +
            " Jurl text , " +
            " Jtitle text ," +
            " Jnodename text , " +
            " Jcreated text , " +
            " Jusername text , " +
            " Jreplies text ) ";

    public static final String alls = " CREATE TABLE IF NOT EXISTS alls ( " +
            " id integer primary key autoincrement , " +
            " Javatar text ,  " +
            " Jurl text , " +
            " Jtitle text ," +
            " Jnodename text , " +
            " Jcreated text , " +
            " Jusername text , " +
            " Jreplies text ) ";

    public static final String r2 = " CREATE TABLE IF NOT EXISTS r2 ( " +
            " id integer primary key autoincrement , " +
            " Javatar text ,  " +
            " Jurl text , " +
            " Jtitle text ," +
            " Jnodename text , " +
            " Jcreated text , " +
            " Jusername text , " +
            " Jreplies text ) ";


}
