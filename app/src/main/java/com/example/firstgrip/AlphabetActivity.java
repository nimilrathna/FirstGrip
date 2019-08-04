package com.example.firstgrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class AlphabetActivity extends AppCompatActivity {

    public static int alphabet_id=1;
    private ImageButton button_content;
    MediaPlayer alphabet_sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        IntialActions();

    }

    private int changeImage(String imagename)
    {
        int imgres = getResources().getIdentifier(imagename, null, this.getPackageName());
        //Drawable res=getResources().getDrawable(imgres);
        if(imgres!=0) {
            button_content.setImageResource(imgres);
            return 1;
        }
        return 0;
    }

    private void IntialActions() {
        //Bundle bundle = getIntent().getExtras();
        //final String content_no=bundle.getString("CONTENT_NO");
        //if(content_no!= null) {
        button_content = (ImageButton) findViewById(R.id.imagebutton_content);
        StringBuffer imgname=new StringBuffer("@drawable/alph");
        String imagename=imgname.append(Integer.toString(alphabet_id)).toString();
        int changeReply=changeImage(imagename);

        StringBuffer audioname=new StringBuffer("@raw/alph");
        audioname.append(Integer.toString(alphabet_id));
        playAudio(audioname.toString());



        Button button_left=(Button) findViewById(R.id.button_moveleft);
        Button button_right=(Button) findViewById(R.id.button_moveright);

        button_left.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(alphabet_id==1)
                    alphabet_id=26;
                else
                    alphabet_id--;
                StringBuffer imgname=new StringBuffer("@drawable/alph");
                String imagename=imgname.append(Integer.toString(alphabet_id)).toString();
                int changeReply=changeImage(imagename);
                if(changeReply==0) {
                    if(alphabet_id==26)
                        alphabet_id=1;
                    else
                        alphabet_id++;
                }
                else
                {
                    StringBuffer audioname=new StringBuffer("@raw/alph");
                    audioname.append(Integer.toString(alphabet_id));
                    playAudio(audioname.toString());
                }
            }
        });
        button_right.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(alphabet_id==26)
                    alphabet_id=1;
                else
                    alphabet_id++;
                StringBuffer imgname=new StringBuffer("@drawable/alph");
                String imagename=imgname.append(Integer.toString(alphabet_id)).toString();
                int changeReply=changeImage(imagename);
                if(changeReply==0)
                {
                    if(alphabet_id==1)
                        alphabet_id=26;
                    else
                        alphabet_id--;
                }
                else
                {
                    StringBuffer audioname=new StringBuffer("@raw/alph");
                    audioname.append(Integer.toString(alphabet_id));
                    playAudio(audioname.toString());
                }
            }
        });
    }

    private void playAudio(String audioname) {

        int audiores = getResources().getIdentifier(audioname, null, this.getPackageName());
        alphabet_sound=MediaPlayer.create(this,audiores);
        alphabet_sound.start();
    }
}
