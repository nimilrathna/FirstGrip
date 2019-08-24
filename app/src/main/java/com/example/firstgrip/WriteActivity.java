package com.example.firstgrip;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class WriteActivity extends AppCompatActivity {

    WriteActivityViewMOdel mViewModel;
    DatabaseManager dbManager;
    Cursor wordCursor;
    String word;
    int score;
    MediaPlayer word_audio=null;
    MediaPlayer alphabet_audio=null;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    ImageView image_letter1;
    ImageView image_letter2;
    ImageView image_letter3;

    TextView text_score;
    boolean drop_success_letter1=false;
    boolean drop_success_letter2=false;
    boolean drop_success_letter3=false;

    List<String> list_letters;
    List<ImageView> list_alphabets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
        ViewModelProvider viewModelProvider=new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        mViewModel=viewModelProvider.get(WriteActivityViewMOdel.class);

        if(savedInstanceState!=null)
            mViewModel.restoreState(savedInstanceState);

        dbManager=new DatabaseManager(this);
        //SQLiteDatabase db=dbManager.getReadableDatabase();
        wordCursor=dbManager.getAllWords();
        if(wordCursor!=null)
        {
            if(mViewModel.cursor_position>0) {
                wordCursor.moveToPosition(mViewModel.cursor_position);
                score=mViewModel.score;
            }
            else
                wordCursor.moveToFirst();
            word=wordCursor.getString(1);
            //word="cat";
            initialActions();
        }


    }

    @Override
    protected void onPause() {
        if(word_audio!=null)
            word_audio.stop();
        if(alphabet_audio!=null)
            alphabet_audio.stop();
        super.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mViewModel.cursor_position=wordCursor.getPosition();
        mViewModel.score=score;
        if(outState!=null)
            mViewModel.saveState(outState);
    }

    private void initialActions() {

        ImageButton button_home=(ImageButton)findViewById(R.id.button_home);
        button_home.setOnClickListener(new MyOnClickListener());

        ImageButton button_audio=(ImageButton)findViewById(R.id.button_audio);
        button_home.setOnClickListener(new MyOnClickListener());

        text_score=(TextView)findViewById(R.id.text_score);
        text_score.setText(Integer.toString(score));
        image_letter1=(ImageView)findViewById(R.id.image_letter1);
        image_letter2=(ImageView)findViewById(R.id.image_letter2);
        image_letter3=(ImageView)findViewById(R.id.image_letter3);



        image_letter1.setOnDragListener(new dropListener());
        image_letter2.setOnDragListener(new dropListener());
        image_letter3.setOnDragListener(new dropListener());



        gestureDetector = new GestureDetector(this, new WriteActivity.MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        ConstraintLayout layout_word=(ConstraintLayout) findViewById(R.id.layout_write);
        layout_word.setOnTouchListener(gestureListener);

        nextWord();


    }
    class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v)
        {
            switch(v.getId()){
                case R.id.button_home:
                    Intent intent=new Intent(WriteActivity.this,MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_audio:
                    if(word_audio!=null)
                        word_audio.start();
                    break;
            }
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
        //word=wordCursor.getString(1);
        nextWord();
    }


    private void doOnLeftTouch() {
        if(!wordCursor.moveToPrevious())
            wordCursor.moveToFirst();
        //word=wordCursor.getString(1);
        nextWord();
    }

    private void nextWord() {
        if(word_audio!=null)
            word_audio.stop();
        word=wordCursor.getString(1);
        int audio_ref=wordCursor.getInt(3);
        if(audio_ref!=-1) {
            word_audio = MediaPlayer.create(this,audio_ref);
            word_audio.start();
        }

        int imgblankref = getResources().getIdentifier("blank_white","drawable", this.getPackageName());
        image_letter1.setImageResource(imgblankref);
        image_letter2.setImageResource(imgblankref);
        image_letter3.setImageResource(imgblankref);

        list_letters=new ArrayList<>();
        for(int i=0;i<word.length();i++)
        {
            Character c= (char)word.charAt(i);
            list_letters.add(Character.toString(c));
        }
        image_letter1.setTag(list_letters.get(0));
        image_letter2.setTag(list_letters.get(1));
        image_letter3.setTag(list_letters.get(2));

        List<String> alph_image_names=getRandomAlphabets();
        for(int i=1;i<=10;i++)
        {
            StringBuffer viewname=new StringBuffer("image_alph");
            viewname.append(i);
            int id = getResources().getIdentifier(viewname.toString(), "id", this.getPackageName());
            ImageView image_view=(ImageView)findViewById(id);
            int imgref = getResources().getIdentifier(alph_image_names.get(i-1),"drawable", this.getPackageName());
            image_view.setImageResource(imgref);
            image_view.setVisibility(View.VISIBLE);
            image_view.setTag(alph_image_names.get(i-1));


            image_view.setOnTouchListener(new myOnTouchListener());

        }

    }

    private List<String> getRandomAlphabets() {
        List<String> list_random_alph=new ArrayList<>();
        Random random=new Random();
        for(int i=0;;i++)
        {
            int n=random.nextInt(25)+97;
            if(!list_letters.contains(Character.toString((char)n))) {
                String alph = Character.toString((char) n);
                list_random_alph.add(alph);
            }
            if(list_random_alph.size()>=7)
                break;
        }
        for(String s: list_letters)
        {
            list_random_alph.add(s);
        }
        Collections.shuffle(list_random_alph);
        return list_random_alph;
    }
    private final class myOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v,MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_DOWN) {

                if(word_audio!=null)
                    word_audio.stop();
                if(alphabet_audio!=null)
                    alphabet_audio.stop();
                int alphabet_audioref = getResources().getIdentifier(v.getTag().toString(),"raw", WriteActivity.this.getPackageName());
                if(alphabet_audioref!=-1) {
                    alphabet_audio = MediaPlayer.create(WriteActivity.this,alphabet_audioref);
                    alphabet_audio.start();
                }

                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                //ClipData.Item item = new ClipData.Item(v.getTag());
                ClipData.Item item = new ClipData.Item(v.getTag().toString());
                ClipData dragData = new ClipData(
                        v.getTag().toString(),
                        new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                        item);
                v.startDrag(dragData, shadowBuilder, v, 0);

            }
            return true;
        }

    }


    private class dropListener implements View.OnDragListener {

        View draggedView;
        ImageView dropped;

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                    draggedView = (View) event.getLocalState();
                    dropped = (ImageView) draggedView;
                    //changeImage((ImageView)v,drag_image);
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    ClipData.Item item=event.getClipData().getItemAt(0);
                    String drag_image=item.getText().toString();
                    ImageView dropView=(ImageView)v;
                    String dropViewTag=dropView.getTag().toString();

                    if( dropViewTag.equals(list_letters.get(0))&& drag_image.equals(list_letters.get(0))) {
                        changeImage(dropView, drag_image);
                        v.invalidate();
                        drop_success_letter1=true;
                        draggedView.setVisibility(View.INVISIBLE);
                    }
                    else if(dropViewTag.equals(list_letters.get(1)) && drag_image.equals(list_letters.get(1))) {
                        changeImage(dropView, drag_image);
                        v.invalidate();
                        draggedView.setVisibility(View.INVISIBLE);
                        drop_success_letter2=true;
                    }
                    else if(dropViewTag.equals(list_letters.get(2)) && drag_image.equals(list_letters.get(2))) {
                        changeImage(dropView, drag_image);
                        v.invalidate();
                        draggedView.setVisibility(View.INVISIBLE);
                        drop_success_letter3=true;
                    }
                    if(drop_success_letter1==true && drop_success_letter2==true &&drop_success_letter3==true)
                    {
                        try {
                            sleep(1000);
                        }catch(Exception e){
                            //did nothing
                        }

                        score=Integer.parseInt(text_score.getText().toString());
                        score++;
                        text_score.setText(Integer.toString(score));
                        if(score>9)
                        {
                            showThumbsUpToast();
                            score=0;
                        }
                        doOnRightTouch();
                        text_score.setText(Integer.toString(score));
                        drop_success_letter1=false;
                        drop_success_letter2=false;
                        drop_success_letter3=false;
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                    break;
                default:
                    break;
            }
            return true;
        }

        public void showThumbsUpToast() {

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.activity_layout_thumbsup,
                    (ViewGroup)findViewById(R.id.layout_constarint));

            final Toast toast = new Toast(WriteActivity.this);
            toast.setGravity(Gravity.FILL, 0, 0);
            toast.setView(view);


            // Set the toast and duration
            int toastDurationInMilliSeconds = 7000;
            // Set the countdown to display the toast
            CountDownTimer toastCountDown;
            toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
                public void onTick(long millisUntilFinished) {
                    toast.show();
                }
                public void onFinish() {
                    toast.cancel();
                }
            };

            // Show the toast and starts the countdown
            toast.show();
            toastCountDown.start();

            //Plays Music
            MediaPlayer mediaplayer = MediaPlayer.create(WriteActivity.this, R.raw.a);//You Can Put Your File Name Instead Of abc
            mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.reset();
                    mediaPlayer.release();
                }
            });
            mediaplayer.start();
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
        private  int getResId(String resName, Class<?> c) {

            try {
                Field idField = c.getDeclaredField(resName);
                return idField.getInt(idField);
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
    }

}
