package com.example.firstgrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private DatabaseManager dbManager;


    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        dbManager=new DatabaseManager(this);
        SQLiteDatabase db=dbManager.getReadableDatabase();

        intializeButtons();
    }

    private void intializeButtons() {
        ImageButton button_alph=(ImageButton)findViewById(R.id.button_alphabet);
        ImageButton button_read=(ImageButton)findViewById(R.id.button_read);
        ImageButton button_write=(ImageButton)findViewById(R.id.button_write);

        button_alph.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent=new Intent(MainActivity.this,AlphabetActivity.class);
                //intent.putExtra("CONTENT_NO","1");
                startActivity(intent);
            }
        });
        button_read.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent=new Intent(MainActivity.this,ReadActivity.class);
                startActivity(intent);
            }
        });
        button_write.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent=new Intent(MainActivity.this,WriteActivity.class);
                startActivity(intent);
            }
        });
    }
}
