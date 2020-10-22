package com.example.banking2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final  String DATABASE_NAME = "Users.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_0 = "id";
    public static final String COL_1 = "account_number";
    public static final String COL_2= "name";
    public static final String COL_3 = "email";
    public static final String COL_4 = "balance";

    public static final String TABLE_2 ="transactions";
  //  public static final String C0 = "id";
    public static final String C1 = "sender";
    public static final String C2 = "recipient";
    public static final String C3 = "dtime";
    public static final String C4 = "transfer_amt";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDatabase) {

        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, account_number INTEGER," +
                "name TEXT,email TEXT, balance REAL)");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_2+ "(sender TEXT , recipient TEXT, dtime TEXT, transfer_amt INTEGER) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase myDatabase, int oldVersion, int newVersion) {
        myDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        myDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_2);
        onCreate(myDatabase);
    }
    public void insertData(String account_number, String name, String email, String balance){
    SQLiteDatabase myDatabase = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(COL_1,account_number);
    contentValues.put(COL_2,name);
    contentValues.put(COL_3,email);
    contentValues.put(COL_4,balance);
    myDatabase.insert(TABLE_NAME,null,contentValues);
    myDatabase.close();
    }
    public Cursor getData(){
    SQLiteDatabase myDatabase = this.getWritableDatabase();
    Cursor c =  myDatabase.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    return c;
    }

    public Cursor getSelectedData(String name){
        SQLiteDatabase myDatabase = this.getReadableDatabase();
        Cursor c = myDatabase.rawQuery("SELECT * FROM "+TABLE_NAME+ " WHERE name='"+name+"'",null);
        return c;
    }
    public boolean updateData(String id, String account_number, String name, String email, double balance){
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,account_number);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,balance);
        myDatabase.update(TABLE_NAME,contentValues,"id=?",new String[] {id});
       // myDatabase.execSQL("UPDATE "+TABLE_NAME+ " SET balance='"+balance+"'"+ " WHERE name='"+name+"'");
        myDatabase.close();
        return true;
    }

    public boolean deleteData(String id){
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        myDatabase.delete(TABLE_NAME,"id=?",new String[] {id});
        return true;
    }
    public boolean addTransaction( String sender, String recipient,String transfer_amt ){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dtime = sdf.format(d);
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C1,sender);
        values.put(C2,recipient);
        values.put(C3,String.valueOf(dtime));
        values.put(C4,transfer_amt);
        myDatabase.insert(TABLE_2,null,values);
        myDatabase.close();
        return true;
    }
    public ArrayList<Transact> getTransactions(){
        ArrayList<Transact> transacts = new ArrayList<>();
        SQLiteDatabase myDatabase = getReadableDatabase();
        Cursor c = myDatabase.rawQuery("SELECT * FROM "+TABLE_2,null);

        while(c.moveToNext()){
            Transact t = new Transact();
            t.setSender(c.getString(c.getColumnIndex("sender")));
            t.setRecipient(c.getString(c.getColumnIndex("recipient")));
            t.setDtime(c.getString(c.getColumnIndex("dtime")));
            t.setTransfer_amt(c.getString(c.getColumnIndex("transfer_amt")));

            transacts.add(t);
        }
        return transacts;

    }

}
