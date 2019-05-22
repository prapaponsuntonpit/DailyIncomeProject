package com.student.check.dailyincomeproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

public class DailyDatabase extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    public static final String DBName = "GetandPay";
    public static final int DBVersion = 1;

    private static final String Account_Table =
            "create table Account(" +
                    "ID integer PRIMARY KEY AUTOINCREMENT," +
                    "Name text," +
                    "date text," +
                    "money text," +
                    "userid text);";
    private static final String Income_Table =
            "create table Income(" +
            "ID integer PRIMARY KEY AUTOINCREMENT," +
            "userid text," +
            "date text," +
            "time text," +
            "money text," +
            "cate text,"+
            "acc_id integer," +
            "FOREIGN KEY(acc_id) REFERENCES Account(ID));";

    public DailyDatabase(@Nullable Context context) {
        super(context, DBName, null, DBVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(Account_Table);
       db.execSQL(Income_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public boolean addIncome(String acc_id,String date,String money,String time,String cate,String userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("acc_id",acc_id);
        contentValues.put("date",date);
        contentValues.put("cate",cate);
        contentValues.put("money",money);
        contentValues.put("time",time);
        contentValues.put("userid",userid);
        Log.d(TAG,"add Data:"+acc_id+","+date+","+money+","+userid);
        long result = db.insert("Income",null,contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean addAcc(String name,String date,String money,String userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",name);
        contentValues.put("date",date);
        contentValues.put("money",money);
        contentValues.put("userid",userid);

        Log.d(TAG,"add Data: "+name+","+date+","+money+","+userid);
        long result = db.insert("Account",null,contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }

    }
    public boolean hasAcc(String userid){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectString = "SELECT * FROM Account WHERE userid =?";
        Cursor cursor = db.rawQuery(selectString,new String[] {userid});

        boolean hasObject = false;
        if (cursor.moveToFirst()){
            hasObject = true;

            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }
            Log.d(TAG,String.format("records found",count));
        }
        cursor.close();
        db.close();
        return hasObject;
    }

    public Cursor getAllCotacts(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selected = "SELECT * FROM Account WHERE date = ?";
        Cursor res = db.rawQuery( selected, new String[]{date});
        return res;
    }
    public Cursor getAllIncome(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selected = "SELECT * FROM Income WHERE ID = ?";
        Cursor res = db.rawQuery(selected,new String[]{id});
        return res;
    }
    public boolean updateAcc(String id,String money){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("money",money);
        long result = db.update("Account",contentValues,"ID=?",new String[]{id});
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }
}
