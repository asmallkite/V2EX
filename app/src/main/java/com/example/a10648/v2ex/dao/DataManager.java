package com.example.a10648.v2ex.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a10648.v2ex.model.JtopicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10648 on 2016/7/23 0023.
 */
public class DataManager {

    public static List<JtopicModel> getDataFromDb(String tableName, int currentItemCount,
                                                  Context context, String dbName,
                                                  SQLiteDatabase.CursorFactory cursorFactory,
                                                  int version) {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(context, dbName,  cursorFactory, version);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        List<JtopicModel> modelList = new ArrayList<>();

        if (cursor.moveToLast() && cursor.move(-currentItemCount)) {
            for (int i = 0; i < 10; i++) {
                String Javatar = cursor.getString(cursor.getColumnIndex("Javatar"));
                String Jurl = cursor.getString(cursor.getColumnIndex("Jurl"));
                String Jtitle = cursor.getString(cursor.getColumnIndex("Jtitle"));
                String Jnodename = cursor.getString(cursor.getColumnIndex("Jnodename"));
                String Jcreated = cursor.getString(cursor.getColumnIndex("Jcreated"));
                String Jusername = cursor.getString(cursor.getColumnIndex("Jusername"));
                String Jreplies = cursor.getString(cursor.getColumnIndex("Jreplies"));

                JtopicModel jtopicModel = new JtopicModel(Javatar, Jurl, Jtitle, Jnodename, Jcreated, Jusername, Jreplies);
                modelList.add(jtopicModel);
                if (cursor.isFirst()) {
                    break;
                }
                cursor.moveToPrevious();
            }
        }
        cursor.close();
        db.close();
        return modelList;

    }
}
