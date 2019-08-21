package com.example.firstgrip;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    static final String dbName="FirstGrip_DB";
    static final String alphabetTable="Alphabets";
    static final String colID="AlphabetID";
    static final String colName="Name";
    static final String colImageRef="ImageRef";
    static final String colAudioRef="AudioRef";
    static final String colRelatedWordImageRef="WordImageRef";
    static final String colRelatedWordAudioRef="WordAudioRef";

    static final String soundsTable="Sounds";
    static final String colSoundID="SoundID";
    static final String colSoundName="SoundName";
    static final String colsoundAudioRef="SoundAudioRef";

    static final String wordTable="Words";
    static final String colWordID="WordID";
    static final String colWordName="WordName";
    static final String colWordImageRef="ImageRef";
    static final String colWordAudioRef="WordAudioRef";
    static final String colsound="SoundID";


    public DatabaseManager(Context context) {


        super(context, dbName, null,5);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // db.execSQL("DROP TABLE IF EXISTS "+alphabetTable);
        try {
            db.execSQL("CREATE TABLE " + alphabetTable + "(" + colID + " INTEGER PRIMARY KEY ," + colName + " TEXT ," + colImageRef + " INTEGER ,"
                    + colAudioRef + " INTEGER ," + colRelatedWordImageRef + " INTEGER , " + colRelatedWordAudioRef + " INTEGER)");

            db.execSQL("CREATE TABLE " + soundsTable + "(" + colSoundID + " INTEGER PRIMARY KEY ," + colSoundName + " TEXT ," + colsoundAudioRef + " INTEGER)");

            db.execSQL("CREATE TABLE " + wordTable + "(" + colWordID + " INTEGER PRIMARY KEY " + ","
                    + colWordName + " TEXT ,"
                    + colWordImageRef + " INTEGER ,"
                    + colWordAudioRef + " INTEGER, "
                    + colsound + " INTEGER, "
                    + "FOREIGN KEY (" + colsound + ") REFERENCES " + soundsTable + " (" + colSoundID + "))");
       /* db.execSQL("INSERT INTO "+alphabetTable+
                "("+colID+","
                +colName+","
                +colImageRef+","
                +colAudioRef+","
                +colRelatedWordImageRef+","
                +colRelatedWordAudioRef+") VALUES (1,'a',-1,-1,-1,-1)");*/

            insertAlphabets(db);
            insertSounds(db);
            insertWords(db);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        //insertWords(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+alphabetTable);
        db.execSQL("DROP TABLE IF EXISTS "+soundsTable);
        db.execSQL("DROP TABLE IF EXISTS "+wordTable);
        onCreate(db);
    }
    private void insertSounds(SQLiteDatabase db) {
        ContentValues cv=new ContentValues();

        //List<String> sounds=new ArrayList<String>();

        cv.put(colSoundID,1);
        cv.put(colSoundName,"at");
        cv.put(colsoundAudioRef,-1);
        db.insert(soundsTable, colSoundID, cv);

        cv.put(colSoundID,2);
        cv.put(colSoundName,"ad");
        cv.put(colsoundAudioRef,-1);
        db.insert(soundsTable, colSoundID, cv);

        cv.put(colSoundID,3);
        cv.put(colSoundName,"aw");
        cv.put(colsoundAudioRef,-1);
        db.insert(soundsTable, colSoundID, cv);

        cv.put(colSoundID,4);
        cv.put(colSoundName,"ay");
        cv.put(colsoundAudioRef,-1);
        db.insert(soundsTable, colSoundID, cv);

        cv.put(colSoundID,5);
        cv.put(colSoundName,"ab");
        cv.put(colsoundAudioRef,-1);
        db.insert(soundsTable, colSoundID, cv);

        cv.put(colSoundID,6);
        cv.put(colSoundName,"am");
        cv.put(colsoundAudioRef,-1);
        db.insert(soundsTable, colSoundID, cv);

        cv.put(colSoundID,7);
        cv.put(colSoundName,"ag");
        cv.put(colsoundAudioRef,-1);
        db.insert(soundsTable, colSoundID, cv);

        cv.put(colSoundID,8);
        cv.put(colSoundName,"ed");
        cv.put(colsoundAudioRef,-1);
        db.insert(soundsTable, colSoundID, cv);
    }

    private void insertWords(SQLiteDatabase db) {
        //SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
            cv.put(colWordID,1);
            cv.put(colWordName,"cat");
            cv.put(colWordImageRef,-1);
            cv.put(colWordAudioRef,-1);
            cv.put(colsound,1);
            db.insert(wordTable, colWordID, cv);

        cv=new ContentValues();
        cv.put(colWordID,2);
        cv.put(colWordName,"bat");
        cv.put(colWordImageRef,-1);
        cv.put(colWordAudioRef,-1);
        cv.put(colsound,1);
        db.insert(wordTable, colWordID, cv);

        cv=new ContentValues();
        cv.put(colWordID,3);
        cv.put(colWordName,"mat");
        cv.put(colWordImageRef,-1);
        cv.put(colWordAudioRef,-1);
        cv.put(colsound,1);
        db.insert(wordTable, colWordID, cv);

        cv.put(colWordID,4);
        cv.put(colWordName,"bad");
        cv.put(colWordImageRef,-1);
        cv.put(colWordAudioRef,-1);
        cv.put(colsound,2);
        db.insert(wordTable, colWordID, cv);

        cv=new ContentValues();
        cv.put(colWordID,5);
        cv.put(colWordName,"dad");
        cv.put(colWordImageRef,-1);
        cv.put(colWordAudioRef,-1);
        cv.put(colsound,2);
        db.insert(wordTable, colWordID, cv);

        cv=new ContentValues();
        cv.put(colWordID,6);
        cv.put(colWordName,"sad");
        cv.put(colWordImageRef,-1);
        cv.put(colWordAudioRef,-1);
        cv.put(colsound,2);
        db.insert(wordTable, colWordID, cv);
    }


    public void insertAlphabets(SQLiteDatabase db)
    {
        //db=this.getWritableDatabase();
try {
    for (int i = 1; i <= 26; i++) {
        String alph = Character.toString((char) (96 + i));
        String word = new StringBuffer(alph).append("_word").toString();
        String sqlquery = "INSERT INTO " + alphabetTable +
                "(" + colID + ","
                + colName + ","
                + colImageRef + ","
                + colAudioRef + ","
                + colRelatedWordImageRef + ","
                + colRelatedWordAudioRef + ") VALUES ("
                + i + ",'"
                + alph + "',"
                + getResId(alph, R.drawable.class) + ","
                + getResId(alph, R.raw.class) + ","
                + getResId(word, R.drawable.class) + ","
                + getResId(word, R.raw.class) + ")";
        db.execSQL(sqlquery);
    }
}
catch (SQLException e)
{
    System.out.println(e.getMessage());
}
    }
   public Cursor getAllAlphabets()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("SELECT "+ colID + " as _id,"
                        + colName + ","
                        + colImageRef + ","
                        + colAudioRef + ","
                        + colRelatedWordImageRef + ","
                        + colRelatedWordAudioRef +
                " from " +alphabetTable,new String[] {});
              /*  "SELECT "+col+" as _id,
                "+colDeptName+" from "+deptTable,new String [] {});*/

        return cur;
    }
    public Cursor getSounds()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("SELECT "+ colSoundID + " as _id,"
                + colSoundName + ","
                + colsoundAudioRef +
                " from " +soundsTable,new String[] {});
              /*  "SELECT "+col+" as _id,
                "+colDeptName+" from "+deptTable,new String [] {});*/

        return cur;
    }
    public Cursor getWords(int sound_ID)
    {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT " + colWordID + " as _id,"
                    + colWordName + ","
                    + colWordImageRef + ","
                    + colWordAudioRef +
                    " from " + wordTable + " WHERE " + colSoundID + "=" +sound_ID, new String[]{});
              /*  "SELECT "+col+" as _id,
                "+colDeptName+" from "+deptTable,new String [] {});*/

            return cur;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Cursor getAllWords()
    {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT " + colWordID + " as _id,"
                    + colWordName + ","
                    + colWordImageRef + ","
                    + colWordAudioRef +
                    " from " + wordTable, new String[]{});
              /*  "SELECT "+col+" as _id,
                "+colDeptName+" from "+deptTable,new String [] {});*/

            return cur;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
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
