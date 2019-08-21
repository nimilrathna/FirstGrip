package com.example.firstgrip;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordRecyclerAdapter extends RecyclerView.Adapter<WordRecyclerAdapter.ViewHolder>
{
    private final Context mContext;
    Cursor msoundCursor;
    private final LayoutInflater layoutInflater;

    public WordRecyclerAdapter(Context mConText, Cursor soundCursor) {
        this.mContext = mConText;
        //this.mWords=wlist;
        //this.maudios=alist;
        this.msoundCursor=soundCursor;
        layoutInflater = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =layoutInflater.inflate(R.layout.itemwords,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(msoundCursor.moveToPosition(position)) {
            holder.text_word.setText(msoundCursor.getString(1));
            holder.current_sound_id = msoundCursor.getInt(0);
            holder.current_audio_ref = msoundCursor.getInt(2);
        }
    }

    @Override
    public int getItemCount() {
        return msoundCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView text_word;
        private final ImageView image_audio;
        int current_sound_id;
        int current_audio_ref;
        MediaPlayer word_sound;

        public ViewHolder(View itemView){
            super(itemView);
            text_word= (TextView)itemView.findViewById(R.id.text_word);
            image_audio = (ImageView) itemView.findViewById(R.id.image_audio);

            text_word.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    if(word_sound!=null)
                        word_sound.stop();
                    Intent intent=new Intent(mContext,ReadWordActivity.class);
                    intent.putExtra("SOUND",current_sound_id);
                    mContext.startActivity(intent);
                }
            });

            image_audio.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    if(current_audio_ref!=-1) {
                        word_sound = MediaPlayer.create(mContext, current_audio_ref);
                        word_sound.start();
                    }
                }
            });
        }
    }
}
