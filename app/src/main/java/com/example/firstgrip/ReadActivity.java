package com.example.firstgrip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {

//public static final String CONTENT_NO="com.example.firstgrip.CONTENT_NO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        InitalizeDisplayContent();

    }

    private void InitalizeDisplayContent() {
        final RecyclerView recyclerWords=(RecyclerView)findViewById(R.id.list_words);
        final LinearLayoutManager wordsLayoutManager=new LinearLayoutManager(this);
        recyclerWords.setLayoutManager(wordsLayoutManager);
        //DatabaseManager dbManager=new DatabaseManager(this);
        //SQLiteDatabase db=dbManager.getReadableDatabase();
        //dbManager.close();
        List<String> wordList=new ArrayList<String>();
        wordList.add("At");
        wordList.add("As");
        wordList.add("An");
        wordList.add("Cat");
        wordList.add("Mat");
        wordList.add("Rat");
        wordList.add("Pen");
        wordList.add("Pan");
        wordList.add("Pin");
        wordList.add("Eat");
        final WordRecyclerAdapter wordRecyclerAdapter=new WordRecyclerAdapter(this,wordList);
        recyclerWords.setAdapter(wordRecyclerAdapter);
    }


}
