package com.example.firstgrip;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class WriteActivityViewMOdel extends ViewModel {
    private final String CURSOR_POSITION="com.example.firstgrip.WriteActivity.CursorPosition";
    private final String SCORE="com.example.firstgrip.WriteActivity.Score";
    public int cursor_position;
    public int score;
    public void saveState(Bundle outState)
    {
        outState.putInt(CURSOR_POSITION,cursor_position);
        outState.putInt(SCORE,score);
    }
    public void restoreState(Bundle instate) {
        cursor_position=instate.getInt(CURSOR_POSITION);
        score=instate.getInt(SCORE);
    }
}
