package com.jamper.viewscreator.UTILS;

import android.content.Context;

/**
 * Created by jamper on 2/23/2018.
 */

public class ExtraSpinner extends android.support.v7.widget.AppCompatSpinner {
    public ExtraSpinner(Context context) {
        super(context);
    }


    @Override
    public void setOnItemClickListener(OnItemClickListener l) {
       // super.setOnItemClickListener(l);
    }
}
