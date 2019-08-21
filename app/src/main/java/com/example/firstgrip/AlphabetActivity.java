package com.example.firstgrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class AlphabetActivity extends AppCompatActivity {

    private DatabaseManager dbManager;
    private Cursor alphCursor;
    private int curr_alphabet_id=1;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    private ImageView image_content;
    MediaPlayer alphabet_sound;
    MediaPlayer word_sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide(); //hide the title bar
        dbManager=new DatabaseManager(this);
        //SQLiteDatabase db=dbManager.getReadableDatabase();
        alphCursor=dbManager.getAllAlphabets();
        if(alphCursor.moveToFirst()) {
            curr_alphabet_id = 1;
            IntialActions();
        }
        else
        {
            Toast toast=Toast.makeText(getApplicationContext(),"No Alphabets Loaded",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();
        }


    }


    private void IntialActions() {
        //Bundle bundle = getIntent().getExtras();
        //final String content_no=bundle.getString("CONTENT_NO");
        //if(content_no!= null) {
        ImageButton button_home=(ImageButton)findViewById(R.id.button_home);

        button_home.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent=new Intent(AlphabetActivity.this,MainActivity.class);
                //intent.putExtra("CONTENT_NO","1");
                startActivity(intent);
            }
        });
        image_content = (ImageView) findViewById(R.id.image_content);
        int changeReply=changeImage();
        playAudio();

        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        RelativeLayout layout_alphabet_content=(RelativeLayout) findViewById(R.id.layout_alphabet_content);
        layout_alphabet_content.setOnTouchListener(gestureListener);
                /*new RelativeLayout.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Log.i("IMAGE", "motion event: " + event.toString());
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_UP:
                                doOnLeftTouch();
                                        break;
                            case MotionEvent.ACTION_DOWN:
                                doOnRightTouch();
                                break;
                        }
                        return false;
                    }
                }
        );*/

        /*layout_alphabet_content.setOnClickListener(new RelativeLayout.OnClickListener(){
            @Override
            public void onClick(View v)
            {

                doOnRightTouch();
            }
        });*/
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

    private void playAudio() {
        int alphAudioref = alphCursor.getInt(3);
        if(alphAudioref!=-1) {
            alphabet_sound = MediaPlayer.create(this, alphAudioref);
            alphabet_sound.start();
        }
        int wordAudioref=alphCursor.getInt(5);
        if(wordAudioref!=-1) {
            word_sound = MediaPlayer.create(this, wordAudioref);
            word_sound.start();
        }

    }
    private void stopAudio()
    {
        alphabet_sound.stop();
        word_sound.stop();
    }

    @Override protected void onPause()
    {
        stopAudio();
        super.onPause();
    }
    private int changeImage()
    {
        //int imgres = getResources().getIdentifier(imagename, null, this.getPackageName());
        int imgref=alphCursor.getInt(4);
        //Drawable res=getResources().getDrawable(imgres);
        if(imgref!=0) {
            image_content.setImageResource(imgref);
            return 1;
        }
        return 0;
    }
    private void doOnLeftTouch()
    {
        int alphabet_id=alphCursor.getInt(0);
        if(alphabet_id==1) {
            alphabet_id = 26;
            //alphCursor.moveToLast();
        }
        else {
            alphabet_id--;
            //alphCursor.moveToPrevious();
        }

        boolean cursorreply=changeCursorPosition(alphabet_id);
        if(cursorreply) {
            int changeReply = changeImage();
            if (changeReply == 0) {
                changeCursorPosition(curr_alphabet_id);
            } else {
                curr_alphabet_id=alphabet_id;
                stopAudio();
                playAudio();
            }
        }
        else
        {
            changeCursorPosition(curr_alphabet_id);
        }
    }
    private void doOnRightTouch()
    {
        int alphabet_id=alphCursor.getInt(0);
        if(alphabet_id==26) {
            alphabet_id = 1;
            //alphCursor.moveToFirst();
        }
        else {
            alphabet_id++;
            //alphCursor.moveToNext();
        }
        boolean cursorreply=changeCursorPosition(alphabet_id);
        if(cursorreply) {
            int changeReply = changeImage();
            if (changeReply == 0) {
                changeCursorPosition(curr_alphabet_id);
            } else {
                curr_alphabet_id=alphabet_id;
                stopAudio();
                playAudio();
            }
        }
        else
        {
            changeCursorPosition(curr_alphabet_id);
        }
    }
    private boolean changeCursorPosition(int alphabet_id)
    {
        try
        {
                return alphCursor.moveToPosition(alphabet_id-1);
        }
        catch(Exception e)
        {
            Toast toast=Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();
            return false;
        }
    }

}
