package com.guoyaohua.chemicals;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by John Kwok on 2016/7/9.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASENAME = "chemicals.db";
    public static final int DATABASEVERSION = 1;
    public static final String TABLENAME = "chemicals";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
