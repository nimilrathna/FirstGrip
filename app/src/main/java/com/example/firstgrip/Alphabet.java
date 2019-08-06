package com.example.firstgrip;

public class Alphabet {
    private int alphabet_ID;
    private String imageUri,audioUri;

    Alphabet()
    {

    }
    Alphabet(int alphabet_ID)
    {
        setUri(alphabet_ID);
    }
    public void setAlphabet_ID(int alphabet_ID) {
        this.alphabet_ID = alphabet_ID;
        setUri();
    }
    public int getAlphabet_ID()
    {
        return alphabet_ID;
    }

    public String getImageUri() {
        return imageUri;
    }
    public String getAudioUri(){
            return audioUri;
    }

    private void setUri()
    {
        StringBuffer temp =new StringBuffer("@drawable/alph");
        imageUri=temp.append(Integer.toString(alphabet_ID)).toString();
        temp =new StringBuffer("@raw/alph");
        audioUri=temp.append(Integer.toString(alphabet_ID)).toString();
    }
    public void setUri(int alphID)
    {
        alphabet_ID=alphID;
        setUri();
    }
}
