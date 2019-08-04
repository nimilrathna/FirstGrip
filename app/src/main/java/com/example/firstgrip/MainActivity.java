package com.example.firstgrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        intializeButtons();
    }

    private void intializeButtons() {
        Button button_alph=(Button)findViewById(R.id.button_alphabet);
        Button button_read=(Button)findViewById(R.id.button_read);
        Button button_write=(Button)findViewById(R.id.button_write);

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
