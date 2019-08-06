package com.example.firstgrip;

import android.content.Context;
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
    private final List<String> mWords;
    private final LayoutInflater layoutInflater;

    public WordRecyclerAdapter(Context mConText,List<String> list) {
        this.mContext = mConText;
        this.mWords=list;
        layoutInflater = LayoutInflater.from(mContext);

    }
    public void initiateWordList()
    {
        this.mWords.add("At");
        this.mWords.add("As");
        this.mWords.add("An");
        this.mWords.add("Cat");
        this.mWords.add("Mat");
        this.mWords.add("Rat");
        this.mWords.add("Pen");
        this.mWords.add("Pan");
        this.mWords.add("Pin");
        this.mWords.add("Eat");
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =layoutInflater.inflate(R.layout.itemwords,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text_word.setText(mWords.get(position));
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView text_word;
        private final ImageView image_audio;


        public ViewHolder(View itemView){
            super(itemView);
            text_word= (TextView)itemView.findViewById(R.id.text_word);
            image_audio = (ImageView) itemView.findViewById(R.id.image_audio);
        }
    }
}
