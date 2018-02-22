package com.jamper.viewscreator.UTILS;

import android.app.Application;
import android.content.Context;

import com.jamper.viewscreator.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nforticsmobileteam on 22/02/2018.
 */

public class Initialiser{

    Context mContext;
    private static HashMap<String, String> imageData;
    private  ArrayList<String> countryCode;
    private  ArrayList<String> countryList;

    public Initialiser(Context context){
        this.mContext = context;
        addCountryList();
    }

    public Initialiser(){
        addCountryList();
    }


    public static HashMap<String, String> getImageData() {
        if (imageData == null)
            imageData = new HashMap<>();
        return imageData;
    }

    /**
     * Add Country Iso Codes to this List
     **/
    private void addCountryList() {
        String[] country = mContext.getResources().getStringArray(R.array.allianz_country);
        countryCode.clear();
        countryList.clear();
        for (int j = 0; j < country.length; j++) {
            String[] countryName = country[j].split(",");
            String value = countryName[0];
            try {
                value = "+" + countryName[0]; // + " " + countryName[2];
            } catch (Exception e) {
                e.printStackTrace();
            }
            countryCode.add(value);
            countryList.add(countryName[2]);
        }
    }

    public  ArrayList<String> getCountryCode() {
        return countryCode;
    }

    public  ArrayList<String> getCountryList() {
        return countryList;
    }
}
