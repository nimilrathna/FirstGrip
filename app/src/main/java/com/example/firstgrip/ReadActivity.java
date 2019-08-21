package com.example.firstgrip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {

//public static final String CONTENT_NO="com.example.firstgrip.CONTENT_NO";
    DatabaseManager dbManager;
    Cursor soundCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        getSupportActionBar().hide();
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        InitalizeDisplayContent();

    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }

    private void InitalizeDisplayContent() {
        ImageButton button_home=(ImageButton)findViewById(R.id.button_home);

        button_home.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent=new Intent(ReadActivity.this,MainActivity.class);
                //intent.putExtra("CONTENT_NO","1");
                startActivity(intent);
            }
        });
        final RecyclerView recyclerWords=(RecyclerView)findViewById(R.id.list_words);
        final LinearLayoutManager wordsLayoutManager=new LinearLayoutManager(this);
        recyclerWords.setLayoutManager(wordsLayoutManager);
        dbManager=new DatabaseManager(this);
        //SQLiteDatabase db=dbManager.getReadableDatabase();
        soundCursor=dbManager.getSounds();
        if(soundCursor.moveToFirst()) {
            final WordRecyclerAdapter wordRecyclerAdapter = new WordRecyclerAdapter(this, soundCursor);
            recyclerWords.setAdapter(wordRecyclerAdapter);
        }
    }


}
