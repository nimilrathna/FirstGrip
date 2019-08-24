package com.example.firstgrip;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class ReadWordActivityViewModel extends ViewModel {
    private final String CURSOR_POSITION="com.example.firstgrip.ReadWordActivity.CursorPosition";
    public int cursor_position;
    public void saveState(Bundle outState)
    {
        outState.putInt(CURSOR_POSITION,cursor_position);
    }
    public void restoreState(Bundle instate)
    {
        cursor_position=instate.getInt(CURSOR_POSITION);
    }
}
