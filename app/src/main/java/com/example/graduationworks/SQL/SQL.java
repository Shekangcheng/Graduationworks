package com.example.graduationworks.SQL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL  {
    MySQL mysql;
    SQLiteDatabase db;
    //读写
    public void Writable(Context context){
        mysql=new MySQL(context);
        db=mysql.getWritableDatabase();
    }
    //只读
    public void Readable(Context context){
        mysql=new MySQL(context);
        db=mysql.getReadableDatabase();
    }
    //增
    public void AddDB(String statements){
        db.execSQL(statements);
    }
    //删
    public void DeleteDB(String statements){
        db.execSQL(statements);
    }
    //改
    public void UpdataDB(String statements){
        db.execSQL(statements);
    }
    //查
    public Cursor SelectDB(String statements){
        return db.rawQuery(statements,null);
    }
    //关闭数据库
    public void CloseDB(){
        if (db.isOpen()){
            db.close();
        }
    }
    public static class MySQL extends SQLiteOpenHelper{
        private static final String SQLname="Main.db";
        static final int VERSION = 1;
        public static final String TABLE_User= "TABLE_User";//用户table_name
        public MySQL(Context context) {
            super(context, SQLname, null, VERSION);
        }
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("create table " + MySQL.TABLE_User + "(_id integer primary key autoincrement,user integer not null,password varchar not null,phone varchar not null)");
            }
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }
        }
}
