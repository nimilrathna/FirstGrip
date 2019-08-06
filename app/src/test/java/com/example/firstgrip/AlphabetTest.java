package com.example.firstgrip;

import org.junit.Test;

import static org.junit.Assert.*;

public class AlphabetTest {

    @Test
    public void setUri() {
        Alphabet alphabet= new Alphabet(2);
        String imaageres="@drawable/alph2";
        String audiores="@raw/alph2";
        String result1=alphabet.getImageUri();
        String result2=alphabet.getAudioUri();

        System.out.println("Image Res: "+ result1);
        System.out.println("Audio Res: "+ result2);

        assertEquals(result1,imaageres);
        assertEquals(result2,audiores);
        //assertNotEquals(result1,imaageres);
    }
}