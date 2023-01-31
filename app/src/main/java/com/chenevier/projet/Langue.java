package com.chenevier.projet;

import androidx.annotation.NonNull;

public class Langue {
    String language;
    String name;
    public  Langue(String l,String n){
        this.language = l;
        this.name = n;
    }

    @NonNull
    public String toString(){

        String countryCode = this.language;
        int firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6;
        int secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6;
        String flag =  new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));

        return  flag.toString() + " " + this.name;
    }

}
