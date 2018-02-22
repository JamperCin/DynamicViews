package com.jamper.viewscreator.UTILS;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jamper.searchspinner.SearchingSpinner;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import mehdi.sakout.fancybuttons.FancyButton;


/**
 * Created by jamper on 1/16/2018.
 */

public class Utils {

    Context mContext;


    public Utils(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Function to set the image drawable from Resource to an imageView
     *
     * @param resourceId the id of the resource from drawables or mipMap
     * @param imageView  the ImageView or CircleImageView to which the drawable will be set to
     **/
    public void setImageResource(int resourceId, ImageView imageView) {
        try {
            imageView.setImageBitmap(CompressImage.decodeSampledBitmapFromResource(
                    mContext.getResources(), resourceId, 300, 300));
        } catch (OutOfMemoryError er) {
            LOG("Error Parsing image >> " + er.getMessage());
            try {
                Glide.with(mContext)
                        .load("")
                        .placeholder(resourceId)
                        .into(imageView);
            } catch (OutOfMemoryError e) {
                LOG("Error Parsing image Alternative >> " + e.getMessage());
                try {
                    imageView.setImageDrawable(mContext.getResources().getDrawable(resourceId));
                } catch (Exception ef) {
                    LOG("Error Parsing image Alternative One >> " + ef.getMessage());
                }
            }


        }

    }


    public static void LOG(String mess) {
        Log.d("Http", mess);
    }

    /**
     * convert the POST Request to
     **/
   /* public static String convertClassToJson(Transaction transaction) {
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            //  .serializeNulls().create();
            return gson.toJson(transaction);
        } catch (Exception e) {
            return "";
        }
    }
*/
    /**
     * Convert the JSON response to map the Transaction Class
     **/
   /* public static Transaction getJSON(String jsonString) {
        try {
            JsonParser parser = new JsonParser();
            JsonElement mJson = parser.parse(jsonString);
            Gson gson = new Gson();
            return gson.fromJson(mJson, Transaction.class);
        } catch (Exception e) {
            LOG("Couldn't Map Json To Class >> " + e.getMessage());
            return null;
        }
    }*/


    public void cacheStringData(final String data, final String FILENAME) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream fos = mContext.openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    fos.write(data.getBytes());
                    fos.close();
                    LOG("Writing >>" + data);
                } catch (IOException e) {
                    LOG("Error Caching Data >> " + e.getMessage());
                }
            }
        });

    }

    public String getCachedString(String FILENAME) {
        StringBuffer fileContent = null;
        try {
            FileInputStream fis = mContext.openFileInput(FILENAME);
            if (fis != null) {
                fileContent = new StringBuffer("");

                byte[] buffer = new byte[1024];
                int n;
                while ((n = fis.read(buffer)) != -1) {
                    fileContent.append(new String(buffer, 0, n));
                    LOG("Reading" + fileContent);
                }
            }

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileContent == null)
            return null;

        return String.valueOf(fileContent);
    }



    public  boolean Validator(ArrayList<View> viewArrayList) {
        boolean valid = true;
        try {
            for (View v : viewArrayList) {
                if (v instanceof EditText) {
                    EditText edt = (EditText) v;
                    if (TextUtils.isEmpty(edt.getText().toString())) {
                        edt.setError(edt.getHint().toString() + " required");
                        edt.requestFocus();
                        edt.setFocusable(true);
                        valid = false;
                        break;
                    }
                }

                if (v instanceof Spinner) {
                    Spinner spinner = (Spinner) v;
                    if (spinner.getSelectedItem() == null || TextUtils.isEmpty(spinner.getSelectedItem().toString())) {
                        valid = false;
                        Toast.makeText(mContext, spinner.getTag().toString() + " required", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }


                if (v instanceof RadioGroup) {
                    RadioGroup radioGroup = (RadioGroup) v;
                    String tag = radioGroup.getTag().toString();
                    if (splitText(0, tag).equalsIgnoreCase("TAG")) {
                        valid = false;
                        Toast.makeText(mContext, splitText(1, tag) + " required", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

                if (v instanceof LinearLayout) {
                    LinearLayout linearLayout = (LinearLayout) v;
                    String tag = linearLayout.getTag().toString();
                    if (splitText(0, tag).equalsIgnoreCase("TAG")) {
                        valid = false;
                        Toast.makeText(mContext, splitText(1, tag) + " required", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

                if (v instanceof ImageView) {
                    ImageView linearLayout = (ImageView) v;
                    String tag = linearLayout.getTag().toString();
                    if (splitText(0, tag).equalsIgnoreCase("TAG")) {
                        valid = false;
                        Toast.makeText(mContext, splitText(1, tag) + " required", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            LOG("Error validating >>> " + e.getMessage());
        }

        return valid;
    }

    /**
     * this function accepts a string in the form of date eg)) 22/04/2017 and splits them into an array and uses the index to
     * retrieve the particular value. Eg index 0 retrieves value = 22, index 1 for value = 04, and index 2 for value = 2017
     **/
    public static String splitText(int index, String date) {
        try {
            if (!date.contains("#"))
                return date;

            if (!TextUtils.isEmpty(date)) {
                String[] dateArray = date.split("#");
                return dateArray[index];
            }
        } catch (Exception e) {
            return date;
        }
        return "";
    }


    public static String splitName(int index, String date) {
        try {
            if (!date.contains(" "))
                return date;

            if (!TextUtils.isEmpty(date)) {
                String[] dateArray = date.split(" ");
                return dateArray[index];
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }


    /**
     * convert the POST Request to
     **/
   /* public static String convertClassToJson(Transaction transaction) {
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            //  .serializeNulls().create();
            return gson.toJson(transaction);
        } catch (Exception e) {
            return "";
        }
    }*/

    /**
     * convert the POST Request to
     **/
    public static String convertArrayToJson(ArrayList<String> list) {
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            //  .serializeNulls().create();
            if (gson != null)
                return gson.toJson(list);
            return "";
        } catch (Exception e) {
            return "";
        }
    }





    /**
     * Convert the JSON response to map the Transaction Class
     **/
   /* public static Transaction getJSON(String jsonString) {
        try {
            JsonParser parser = new JsonParser();
            JsonElement mJson = parser.parse(jsonString);
            Gson gson = new Gson();
            return gson.fromJson(mJson, Transaction.class);
        } catch (Exception e) {
            LOG("Couldn't Map Json To Class >> " + e.getMessage());
            return null;
        }
    }*/


    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = android.util.Base64.decode(encodedString, android.util.Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static String getBase64Bytes(Bitmap bitmap) {
        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap = Bitmap.createScaledBitmap(bitmap, 350, 350, true);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, bao);
            byte[] ba = bao.toByteArray();
            return Base64.encodeBytes(ba);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }




//    public void changeFragment(int pos) {
//        CustomViewPager viewPager = (CustomViewPager) mContext.findViewById(R.id.viewPager);
//        try {
//            viewPager.setCurrentItem(pos);
//        } catch (Exception e) {
//            mContext.finish();
//        }
//    }


    /**
     * Return String TEXT from the Given View
     *
     * @param v VIEW the type of View to get TEXT from
     **/
    public static String getStringData(View v) {
        if (v instanceof EditText)
            return ((EditText) v).getText().toString();
        if (v instanceof TextView)
            return ((TextView) v).getText().toString();
        if (v instanceof FancyButton)
            return ((FancyButton) v).getText().toString();

        return "";
    }


    public static String getStringValue(View v) {
        try {
            if (getViewValue(v) == null || TextUtils.isEmpty(getViewValue(v)))
                return null;
            else if (getViewValue(v).contains("TAG#"))
                return null;
            else return getViewValue(v).trim();
        } catch (Exception e) {
            return getViewValue(v);
        }
    }


    private static String getViewValue(View v) {
        if (v instanceof SearchingSpinner)
            return ((SearchingSpinner) v).getSelectedItem();
        if (v instanceof EditText)
            return ((EditText) v).getText().toString();
        if (v instanceof Spinner)
            return ((Spinner) v).getSelectedItem().toString();
        if (v instanceof RadioGroup)
            return v.getTag().toString();
        if (v instanceof LinearLayout)
            return v.getTag().toString();
        if (v instanceof ImageView)
            return getImageData(v);
        return null;
    }

    private static String getImageData(View v) {
        try {
            return Initialiser.getImageData().get(v.getTag().toString());
        } catch (Exception e) {
            LOG("Error getting image >> " + e.getMessage());
            return null;
        }
    }


    public static int splitDate(int index, String date) {
        try {
            if (date != null && !date.contains("/"))
                return Integer.parseInt(date);

            if (date != null && !TextUtils.isEmpty(date)) {
                String[] dateArray = date.split("/");
                return Integer.parseInt(dateArray[index]);
            }
        } catch (Exception e) {
            return Integer.parseInt(date);
        }
        return Integer.parseInt("0");
    }


    public static String getCustomisedDate() {
        DateFormat df = new SimpleDateFormat("d/MM/yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public static String formatDate(String datetoFromat) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateRaw = "";
        if (datetoFromat != null && !TextUtils.isEmpty(datetoFromat)) {
            try {
                Date date = format.parse(datetoFromat);
                dateRaw = df.format(date);
            } catch (ParseException e) {
                LOG("Date Error:>> " + e.getMessage());
                return dateRaw;

            }
        }
        return dateRaw;
    }





    public static void fillInnerData(ArrayList<View> v, String hint, String data) {
        try {
            for (int i = 0; i < v.size(); i++) {
                if (v.get(i) instanceof EditText) {
                    String TAG = ((EditText) v.get(i)).getTag().toString();
                    LOG("Here is Tag>> " + TAG);
                    if (TAG.equalsIgnoreCase(hint)) {
                        ((EditText) v.get(i)).setText(data);
                        break;
                    }
                }
            }
        } catch (Exception e) {

        }
    }


}
