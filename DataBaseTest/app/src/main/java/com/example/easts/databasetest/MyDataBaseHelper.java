package com.example.easts.databasetest;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;

/**
 * Created by easts on 2017/11/13.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "create table Book("
            +"id integer primary key autoincrement,"
            +"author text,"
            +"price real,"
            +"pages integer,"
            +"name text)";

    public static final String CREATE_CATEGORY = "create table Category("
            +"id integer primary key autoincrement,"
            +"category_name text"
            +"category_code integer)";

    private Context mContext;

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle("信息")
                .setMessage("建表成功")
                .setPositiveButton("确认",null);

        builder.show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}
