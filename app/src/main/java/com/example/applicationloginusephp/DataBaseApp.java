package com.example.applicationloginusephp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;


public class DataBaseApp extends SQLiteOpenHelper {
    final static private String DB_NAME = "appData";
    final static private int VERSION = 2;
    final static private String TABLE_NAME = "table_data";
    final static private String ID = "id";
    final static private String NAME = "name";
    final static private String EMAIL = "email";
    final static private String PASSWORD = "PASSWORD";
    final static private String SAVEDATA = "savedate";



    public DataBaseApp(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table IF NOT EXISTS " + TABLE_NAME +
                "(" + ID + " integer primary key" +
                "," + NAME + " text " +
                "," + EMAIL + " text " +
                "," + PASSWORD + " text "+
                "," + SAVEDATA + " integer )" ;
        db.execSQL(sql);

        ContentValues contentValues = new ContentValues( );
        contentValues.put(NAME,"null");
        contentValues.put(EMAIL,"null");
        contentValues.put(PASSWORD,"null");
        contentValues.put(SAVEDATA,"0");
        db.insert(TABLE_NAME,null,contentValues) ;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public String getChcked(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase() ;
        String sql ="select "+SAVEDATA+" from "+TABLE_NAME ;
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        cursor.moveToFirst() ;
        String check = cursor.getString(cursor.getColumnIndex(SAVEDATA));
        return check ;
    }
    public String getUserName(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase() ;
        String sql ="select "+NAME+" from "+TABLE_NAME ;
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        cursor.moveToFirst() ;
        String username = cursor.getString(cursor.getColumnIndex(NAME));
        return username ;
    }
    public String getPassword(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase() ;
        String sql ="select "+PASSWORD+" from "+TABLE_NAME ;
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        cursor.moveToFirst() ;
        String password = cursor.getString(cursor.getColumnIndex(PASSWORD));
        return password ;
    }

     public  boolean updateData_Registre(String username,String email,String password,String savedata){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase() ;
        ContentValues contentValues = new ContentValues() ;
        contentValues.put(NAME,username);
         contentValues.put(EMAIL,email);
         contentValues.put(PASSWORD,password);
         contentValues.put(SAVEDATA,savedata);
         sqLiteDatabase.update(TABLE_NAME,contentValues,"id= ?",new String[]{"1"}   ) ;

       return true ;
     }

    public  boolean updateData_Login(String username,String password,String savedata){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase() ;
        ContentValues contentValues = new ContentValues() ;
        contentValues.put(NAME,username);
        contentValues.put(PASSWORD,password);
        contentValues.put(SAVEDATA,savedata);
        sqLiteDatabase.update(TABLE_NAME,contentValues,"id= ?",new String[]{"1"}   ) ;

        return true ;
    }






}