package com.example.okusurichecker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "dataName";
    private static final String CREATE_TABLE = "CREATE TABLE TODO (" +     //テーブル定義 "TODOはテーブル名
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "title TEXT , content TEXT, radioTextA TEXT, radioTextB TEXT, editMedicine TEXT" +
            ");";

    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
