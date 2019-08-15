package com.example.firstgrip;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;

public class DatabaseManager extends SQLiteOpenHelper {

    static final String dbName="FirstGripDB2";
    static final String alphabetTable="Alphabets";
    static final String colID="AlphabetID";
    static final String colName="Name";
    static final String colImageRef="ImageRef";
    static final String colAudioRef="AudioRef";
    static final String colRelatedWordImageRef="WordImageRef";
    static final String colRelatedWordAudioRef="WordAudioRef";

    static final String wordTable="Words";
    static final String colWordID="WordID";
    static final String colWordName="WordName";
    static final String colWordImageRef="ImageRef";
    static final String colWordAudioRef="WordAudioRef";

    public DatabaseManager(Context context) {

        super(context, dbName, null,33);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // db.execSQL("DROP TABLE IF EXISTS "+alphabetTable);

        db.execSQL("CREATE TABLE "+alphabetTable+"("+colID+" INTEGER PRIMARY KEY ,"+colName+ " TEXT ,"+colImageRef+" INTEGER ,"
                +colAudioRef+" INTEGER ,"+colRelatedWordImageRef+" INTEGER , "+colRelatedWordAudioRef+" INTEGER)" );

        db.execSQL("CREATE TABLE "+wordTable+"("+colWordID+" INTEGER PRIMARY KEY ,"+colWordName+ " TEXT ,"+colWordImageRef+" INTEGER ,"
                +colWordAudioRef+" INTEGER)" );
        //insertAlphabets();
        //insertWords();
    }

    private void insertWords() {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
            cv.put(colWordID,1);
            cv.put(colWordName,"cat");
            cv.put(colWordImageRef,-1);
            cv.put(colWordAudioRef,-1);
            db.insert(alphabetTable, colWordID, cv);

        cv=new ContentValues();
        cv.put(colWordID,2);
        cv.put(colWordName,"bat");
        cv.put(colWordImageRef,-1);
        cv.put(colWordAudioRef,-1);
        db.insert(alphabetTable, colWordID, cv);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+alphabetTable);
        onCreate(db);
    }
    public void insertAlphabets()
    {
        SQLiteDatabase db=this.getWritableDatabase();

        for(int i=1;i<=5;i++) {
            ContentValues cv=new ContentValues();
            String alph=Character.toString((char)(96+i));
            String word=new StringBuffer(alph).append("_word").toString();
            cv.put(colID,i);
            cv.put(colName,alph);
            cv.put(colImageRef,getResId(alph,R.drawable.class));
            cv.put(colAudioRef,getResId(alph,R.raw.class));
            cv.put(colRelatedWordImageRef,-1);
            cv.put(colRelatedWordAudioRef,getResId(word,R.raw.class));
            db.insert(alphabetTable, colID, cv);
        }
        db.close();
    }

    private static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
