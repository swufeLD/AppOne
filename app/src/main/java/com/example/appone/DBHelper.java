package com.example.appone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private  static final int version=1;
    private  static final String DB_NAME="myrate.db";
    public   static final String TB_NAME="tb_rates";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }
    public DBHelper(Context context){
        super(context,DB_NAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table "+TB_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CURNAME text,CURRATE text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
