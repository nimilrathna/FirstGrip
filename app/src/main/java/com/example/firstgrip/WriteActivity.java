package com.example.firstgrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WriteActivity extends AppCompatActivity {

    DatabaseManager dbManager;
    Cursor wordCursor;
    String word;

    ImageView image_letter1;
    ImageView image_letter2;
    ImageView image_letter3;
    List<ImageView> list_alphabets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
        dbManager=new DatabaseManager(this);
        //SQLiteDatabase db=dbManager.getReadableDatabase();
        wordCursor=dbManager.getAllWords();
        if(wordCursor!=null)
        {
            wordCursor.moveToFirst();
            word=wordCursor.getString(1);
            word="cat";
            initialActions();
        }


    }

    private void initialActions() {
        image_letter1=(ImageView)findViewById(R.id.image_letter1);
        image_letter2=(ImageView)findViewById(R.id.image_letter2);
        image_letter3=(ImageView)findViewById(R.id.image_letter3);

        image_letter1.setOnDragListener(new dropListener());
        image_letter2.setOnDragListener(new dropListener());
        image_letter3.setOnDragListener(new dropListener());

        List<String> alph_image_names=getRandomAlphabets();
        for(int i=1;i<=10;i++)
        {
            StringBuffer viewname=new StringBuffer("image_alph");
            viewname.append(i);
            int id = getResources().getIdentifier(viewname.toString(), "id", this.getPackageName());
            ImageView image_view=(ImageView)findViewById(id);
            int imgref = getResources().getIdentifier(alph_image_names.get(i-1),"drawable", this.getPackageName());
            image_view.setImageResource(imgref);
            image_view.setTag(alph_image_names.get(i-1));
            image_view.setOnLongClickListener(new myLongClickListener());
        }
    }
    private List<String> getRandomAlphabets()
    {
        List<Character> list_letters=new ArrayList<>();
        for(int i=0;i<word.length();i++)
        {
            Character c= (char)word.charAt(i);
            list_letters.add(c);
        }

        List<String> list_random_alph=new ArrayList<>();
        Random random=new Random();
        for(int i=0;i<8;i++)
        {
            int n=random.nextInt(25)+97;
            if(!list_letters.contains((char)n)) {
                String alph = Character.toString((char) n);
                list_random_alph.add(alph);
            }
        }
        for(char c: list_letters)
        {
            list_random_alph.add(Character.toString(c));
        }
        Collections.shuffle(list_random_alph);
        return list_random_alph;
    }
    private final class myLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                //ClipData.Item item = new ClipData.Item(v.getTag());
                ClipData.Item item=new ClipData.Item(v.getTag().toString());
                ClipData dragData = new ClipData(
                        v.getTag().toString(),
                        new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN },
                        item);
                v.startDrag(dragData, shadowBuilder, v, 0);

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
                    changeImage((ImageView)v,drag_image);
                    v.invalidate();
                    draggedView.setVisibility(View.INVISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
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
