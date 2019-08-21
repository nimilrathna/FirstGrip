package com.example.firstgrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReadWordActivity extends AppCompatActivity {

    DatabaseManager dbManager;
    Cursor wordCursor;
    int sound_ID;
    String word="cat";
    List<MediaPlayer> alphabet_sounds=new ArrayList<MediaPlayer>();

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    ImageView image_letter1;
    ImageView image_letter2;
    ImageView image_letter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_read_word);
        Bundle bundle = getIntent().getExtras();
        sound_ID=bundle.getInt("SOUND");
        dbManager=new DatabaseManager(this);
        //SQLiteDatabase db=dbManager.getReadableDatabase();
        wordCursor=dbManager.getWords(sound_ID);
        if(wordCursor!=null)
        {
            wordCursor.moveToFirst();
            word=wordCursor.getString(1);
            initialActions();
        }

    }
    private int changeImage(ImageView img,String imgname)
    {
        int imgref = getResId(imgname,R.drawable.class);
        //Drawable res=getResources().getDrawable(imgref);
        if(imgref!=0) {
            img.setImageResource(imgref);
            return 1;
        }
        return 0;
    }
    private void initialActions() {
        ImageButton button_home=(ImageButton)findViewById(R.id.button_home);

        button_home.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent=new Intent(ReadWordActivity.this,MainActivity.class);
                //intent.putExtra("CONTENT_NO","1");
                startActivity(intent);
            }
        });
        ImageButton button_back=(ImageButton)findViewById(R.id.button_back);

        button_back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent=new Intent(ReadWordActivity.this,ReadActivity.class);
                //intent.putExtra("CONTENT_NO","1");
                startActivity(intent);
            }
        });
        image_letter1 = (ImageView) findViewById(R.id.image_letter1);
        image_letter2 = (ImageView) findViewById(R.id.image_letter2);
        image_letter3 = (ImageView) findViewById(R.id.image_letter3);

        setword();
/*
        image_letter1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(alphabet_sounds.get(0)!=null)
                    alphabet_sounds.get(0).start();
            }
        });
        image_letter2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(alphabet_sounds.get(1)!=null)
                    alphabet_sounds.get(1).start();
            }
        });
        image_letter3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(alphabet_sounds.get(2)!=null)
                    alphabet_sounds.get(2).start();
            }
        });
*/
        //int changeReply=changeImage();
        //playAudio();

        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        RelativeLayout layout_word=(RelativeLayout) findViewById(R.id.layout_word);
        layout_word.setOnTouchListener(gestureListener);
    }

    private void setword() {
        for(int i=0;i<word.length();i++)
        {
            StringBuffer letter=new StringBuffer();
            letter.append(word.charAt(i));
            String l=letter.toString();
            switch(i)
            {
                case 0:
                    changeImage(image_letter1,l);
                    break;
                case 1:
                    changeImage(image_letter2,l);
                    break;
                case 2:
                    changeImage(image_letter3,l);
            }

            int alph_sound_ref=getResId(l, R.raw.class);
            MediaPlayer alphabet_sound=null;
            if(alph_sound_ref!=-1) {
                alphabet_sound = MediaPlayer.create(this,alph_sound_ref);
            }
            alphabet_sounds.add(alphabet_sound);
        }
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    doOnRightTouch();
                    //Toast.makeText(getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
                }
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    doOnLeftTouch();
                    //Toast.makeText(getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    private void doOnRightTouch() {
        if(!wordCursor.moveToNext())
            wordCursor.moveToLast();
        word=wordCursor.getString(1);
        setword();
    }
    private void doOnLeftTouch() {
        if(!wordCursor.moveToPrevious())
            wordCursor.moveToFirst();
        word=wordCursor.getString(1);
        setword();
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
