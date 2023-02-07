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

        // Change bad contry code
        switch (countryCode){
            case "EN":        countryCode = "GB";        break;
            case "JA":        countryCode = "JP";        break;
            case "DA":        countryCode = "DK";        break;
            case "CS":        countryCode = "CZ";        break;
            case "KO":        countryCode = "KP";        break;
            case "EL":        countryCode = "GR";        break;
            case "NB":        countryCode = "NO";        break;
            case "ZH":        countryCode = "CN";        break;
            case "UK":        countryCode = "UA";        break;
        }

        int firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6;
        int secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6;
        String flag =  new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));

        return  flag+ " " + this.name;
    }


}
