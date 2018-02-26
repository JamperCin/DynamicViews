package com.jamper.viewscreator.LOGIC;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.jamper.searchspinner.SearchingSpinner;
import com.jamper.viewscreator.CALLBACK.CallBack;
import com.jamper.viewscreator.CALLBACK.OnCheckBoxSelected;
import com.jamper.viewscreator.CALLBACK.OnImageSelectedSaved;
import com.jamper.viewscreator.CALLBACK.OnItemSelected;
import com.jamper.viewscreator.R;
import com.jamper.viewscreator.UTILS.DatePickerClass;
import com.jamper.viewscreator.UTILS.ImageHandler;
import com.jamper.viewscreator.UTILS.Initialiser;
import com.jamper.viewscreator.UTILS.SignatureDialog;
import com.jamper.viewscreator.UTILS.Utils;

import java.util.ArrayList;
import java.util.List;

import floatswipe.com.jamper.floatingswipedactivity.Callbacks.OnMenuItemClickListener;
import floatswipe.com.jamper.floatingswipedactivity.UI.FloatingMenuDialog;
import mehdi.sakout.fancybuttons.FancyButton;

import static com.jamper.viewscreator.UTILS.Utils.LOG;

/**
 * Created by nforticsmobileteam on 20/02/2018.
 */

public class DynamicView {

    Activity mContext;
    LinearLayout.LayoutParams params;
    private String spinnerItem = "";
    private ArrayList<View> viewList;
    private ArrayList<View> innerViewsList;
    private Initialiser initialiser;


    public DynamicView(Activity context) {
        this.mContext = context;
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(16, 16, 16, 16);
        spinnerItem = "233";
        viewList = new ArrayList<>();
        innerViewsList = new ArrayList<>();
        initialiser =  new Initialiser(mContext);
    }


    public EditText EditText(String hint) {
        EditText editText = new EditText(mContext);
        editText.setLayoutParams(params);
        editText.setHint(hint);
        editText.setTag(hint);
        editText.setTextSize(14f);
        editText.setHintTextColor(mContext.getResources().getColor(R.color.transparent_black_hex_5));
        editText.setTextColor(mContext.getResources().getColor(R.color.black_eel));
        editText.setSingleLine(true);
        editText.setSingleLine();
        viewList.add(editText);
        return editText;
    }


    public EditText EditText(String hint, int inputType) {
        EditText editText = new EditText(mContext);
        editText.setLayoutParams(params);
        editText.setInputType(inputType);
        editText.setHint(hint);
        editText.setTag(hint);
        editText.setTextSize(14f);
        editText.setHintTextColor(mContext.getResources().getColor(R.color.transparent_black_hex_5));
        editText.setTextColor(mContext.getResources().getColor(R.color.black_eel));
        editText.setSingleLine(true);
        editText.setSingleLine();
        viewList.add(editText);
        return editText;
    }


    public EditText TextArea(String hint, int inputType) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
        params.setMargins(16, 16, 16, 16);

        EditText editText = new EditText(mContext);
        editText.setLayoutParams(params);
        editText.setInputType(inputType);
        editText.setHint(hint);
        editText.setTag(hint);
        editText.setTextSize(14f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            editText.setBackground(mContext.getResources().getDrawable(R.drawable.white_smoke_square_bg));
        }
        editText.setHintTextColor(mContext.getResources().getColor(R.color.transparent_black_hex_5));
        editText.setTextColor(mContext.getResources().getColor(R.color.black_eel));

        viewList.add(editText);
        return editText;
    }


    public EditText TextArea(String hint) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
        params.setMargins(16, 16, 16, 16);

        EditText editText = new EditText(mContext);
        editText.setLayoutParams(params);
        editText.setHint(hint);
        editText.setTag(hint);
        editText.setTextSize(14f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            editText.setBackground(mContext.getResources().getDrawable(R.drawable.white_smoke_square_bg));
        }
        editText.setHintTextColor(mContext.getResources().getColor(R.color.transparent_black_hex_5));
        editText.setTextColor(mContext.getResources().getColor(R.color.black_eel));

        viewList.add(editText);
        return editText;
    }


    public FancyButton Button(String text) {
        FancyButton button = new FancyButton(mContext);
        button.setText(text);
        button.setLayoutParams(params);
        button.setTextSize(16);
        button.setBackgroundColor(mContext.getResources().getColor(R.color.main_color));
        button.setFocusBackgroundColor(mContext.getResources().getColor(R.color.blue));
        button.setRadius(30);
        viewList.add(button);
        return button;
    }


    public View spinner(ArrayList<String> list) {
        try {
            if (list != null) {
                if (list.size() > 10)
                    return searchableSpinner(list);
                else return normalSpinner(list);
            }
        } catch (Exception e) {
            LOG("Error with spinner >> " + e.getMessage());
        }
        return normalSpinner(list);
    }

    private Spinner normalSpinner(List<String> list) {
        final Spinner spinner = new Spinner(mContext);
        spinner.setLayoutParams(params);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                spinner.setBackground(mContext.getDrawable(R.drawable.spinner_default_holo_light));
            }
        } catch (Exception e) {
        }


        if (list != null) {
            ArrayAdapter mAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_layout, list);
            mAdapter.setDropDownViewResource(R.layout.simple_dropdown_item);
            spinner.setAdapter(mAdapter);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner.setTag(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinner.setTag(adapterView.getSelectedItem().toString());
            }
        });

        viewList.add(spinner);
        return spinner;
    }

    private SearchingSpinner searchableSpinner(ArrayList<String> list) {
        final SearchingSpinner spinner = new SearchingSpinner(mContext);
        spinner.addEntries(list);
        spinner.setTitle("Search here");
        spinner.setAcceptLocalEntries(true);
        spinner.setLocalEntriesAddable(true);
        spinner.setItemOnClickDismissDialog(true);
        spinner.setOnItemSelectedListener(new com.jamper.searchspinner.OnItemSelected() {
            @Override
            public void onItemSelected(String item, int itemPosition) {
                spinner.setTag(item);
            }

            @Override
            public void onNothingSelected(String item) {
                spinner.setTag(item);
            }
        });
        spinner.setTag(spinner.getSelectedItem());
        viewList.add(spinner);
        return spinner;
    }

    public TextView textView(String text, boolean bolded) {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextSize(14);
        if (bolded)
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextColor(mContext.getResources().getColor(R.color.black));
        viewList.add(textView);
        return textView;
    }

    public TextView textView(String text, boolean bolded, boolean center) {
        TextView textView = new TextView(mContext);
        if (center)
            params.gravity = Gravity.CENTER_HORIZONTAL;

        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextSize(14);
        if (bolded)
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextColor(mContext.getResources().getColor(R.color.black));
        viewList.add(textView);
        return textView;
    }

    public View space() {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(params);
        textView.setText("");
        textView.setTextSize(14);
        textView.setTextColor(mContext.getResources().getColor(R.color.black));
        viewList.add(textView);
        return textView;
    }

    public RadioGroup radioGroup(ArrayList<String> list, final CallBack callBack) {
        final RadioGroup radioGroup = new RadioGroup(mContext);
        radioGroup.setLayoutParams(params);
        radioGroup.setTag("TAG");
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);

        if (list != null && list.size() != 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            params.setMargins(16, 16, 16, 16);


            for (String str : list) {
                final RadioButton radioButton = new RadioButton(mContext);
                radioButton.setText(String.format("%s", str));
                radioButton.setLayoutParams(params);
                radioButton.setPadding(16, 0, 50, 0);
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        radioGroup.setTag(radioButton.getText().toString());
                        if (callBack != null)
                            callBack.execute(radioButton.getText().toString());
                    }
                });
                radioGroup.addView(radioButton);
            }
        }

        viewList.add(radioGroup);
        return radioGroup;
    }

    public RadioGroup radioGroup(ArrayList<String> list, String tag) {
        final RadioGroup radioGroup = new RadioGroup(mContext);
        radioGroup.setLayoutParams(params);
        radioGroup.setTag("TAG#" + tag);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);

        if (list != null && list.size() != 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            //  params.setMargins(16, 16, 16, 16);

            for (String str : list) {
                final RadioButton radioButton = new RadioButton(mContext);
                radioButton.setText(String.format("%s     ", str));
                radioButton.setLayoutParams(params);
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        radioGroup.setTag(radioButton.getText().toString());
                    }
                });
                radioGroup.addView(radioButton);
            }
        }

        viewList.add(radioGroup);
        return radioGroup;
    }

    public LinearLayout datePicker(final FragmentManager fragmentManager, String tag, final CallBack callBack) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.setMargins(16, 16, 16, 16);

        final LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(params);
        linearLayout.setTag("TAG#" + tag);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        p.gravity = Gravity.CENTER_HORIZONTAL;
        //  p.setMargins(16, 16, 16, 16);

        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 6f);
        p1.gravity = Gravity.CENTER_HORIZONTAL;
        // p1.setMargins(16, 16, 16, 16);

        final EditText editText = new EditText(mContext);
        editText.setEnabled(false);
        editText.setHint("dd/MM/yyyy");
        editText.setTextSize(14f);
        editText.setTag(tag);
        editText.setHintTextColor(mContext.getResources().getColor(R.color.transparent_black_hex_5));
        editText.setTextColor(mContext.getResources().getColor(R.color.black_eel));
        editText.setSingleLine(true);
        editText.setSingleLine();
        editText.setLayoutParams(p);

        final ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(p1);
        imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.calender));
        imageView.setColorFilter(ContextCompat.getColor(mContext, R.color.black));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerClass dp = new DatePickerClass(fragmentManager).openCalendarDatePicker();
                dp.onDateSelected(new OnItemSelected() {
                    @Override
                    public void onItemSelected(String item) {
                        editText.setText(item);
                        linearLayout.setTag(item);
                        if (callBack != null)
                            callBack.execute(item);
                    }
                });
            }
        });

        linearLayout.addView(editText);
        linearLayout.addView(imageView);

        innerViewsList.add(editText);
        viewList.add(linearLayout);
        return linearLayout;
    }


    public LinearLayout PhoneNumberPicker(String hint) {

        TableRow.LayoutParams p = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1f);
        p.gravity = Gravity.CENTER_VERTICAL;

        TableRow.LayoutParams param = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT, 4f);
        param.gravity = Gravity.CENTER_HORIZONTAL;


        final LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(params);
        linearLayout.setTag("TAG#" + hint);

        EditText editText = new EditText(mContext);
        editText.setHint(hint);
        editText.setTag(hint);
        editText.setTextSize((float) 14);
        editText.setHintTextColor(mContext.getResources().getColor(R.color.transparent_black_hex_5));
        editText.setTextColor(mContext.getResources().getColor(R.color.black));
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setLayoutParams(p);

        SearchingSpinner spinner = new SearchingSpinner(mContext);
        spinner.addEntries(initialiser.getCountryCode());
        spinner.setTitle("Search here");
        spinner.setLayoutParams(param);
        spinner.setAcceptLocalEntries(true);
        spinner.setLocalEntriesAddable(true);
        spinner.setItemOnClickDismissDialog(true);

        setListener(editText, linearLayout);
        getSpinnerItem(spinner);

        linearLayout.addView(spinner);
        linearLayout.addView(editText);

        viewList.add(linearLayout);
        innerViewsList.add(editText);
        return linearLayout;
    }


    public ImageView ImageView(int resource, final FragmentManager fragmentManager, final String tag) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.setMargins(16, 16, 16, 16);

        final ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(params);
        imageView.getLayoutParams().width = 180;
        imageView.getLayoutParams().height = 180;
        imageView.setTag(tag);
        new Utils(mContext).setImageResource(resource, imageView);

        new ImageHandler(mContext); //Clean any residue, Refresh class
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new FloatingMenuDialog(mContext)
                            .setDialogTitle("Take Picture")
                            .setPositveButtonText("From Camera")
                            .setNeutralButtonText("From Gallery")
                            .setOnPositiveButtonOnClick(new OnMenuItemClickListener() {
                                @Override
                                public void onClick() {
                                    new ImageHandler(mContext)
                                            .setImageName(tag)
                                            .setImageView(imageView)
                                            .setRequest(0)
                                            .onImageSaved(new OnImageSelectedSaved() {
                                                @Override
                                                public void getData(String data) {
                                                }
                                            });
                                }
                            })
                            .setOnNeutralButtonOnClick(new OnMenuItemClickListener() {
                                @Override
                                public void onClick() {
                                    new ImageHandler(mContext)
                                            .setImageName(tag)
                                            .setImageView(imageView)
                                            .setRequest(1)
                                            .onImageSaved(new OnImageSelectedSaved() {
                                                @Override
                                                public void getData(String data) {
                                                }
                                            });
                                }
                            })
                            .show();
                } catch (Exception e) {
                    new ImageHandler(mContext)
                            .setImageName(tag)
                            .setImageView(imageView)
                            .setRequest(0)
                            .onImageSaved(new OnImageSelectedSaved() {
                                @Override
                                public void getData(String data) {
                                }
                            });
                }
            }
        });

        viewList.add(imageView);
        return imageView;
    }


    public ImageView Signature(final String tag) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.setMargins(16, 16, 16, 16);

        final ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(params);
        imageView.getLayoutParams().width = 180;
        imageView.getLayoutParams().height = 180;
        imageView.setTag("TAG#" + tag);
        new Utils(mContext).setImageResource(R.drawable.signature, imageView);

        new ImageHandler(mContext); //Clean any residue, Refresh class
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SignatureDialog(mContext).setSignatureName(tag).setImageView(imageView);
            }
        });

        viewList.add(imageView);
        return imageView;
    }


    /**========================================================================================================================**/
    /**============================= Below are the required methods that other functions depend on ============================**/

    /**Set the Spinner item to the String value**/
    private void getSpinnerItem(final SearchingSpinner spinner) {
        spinner.setOnItemSelectedListener(new com.jamper.searchspinner.OnItemSelected() {
            @Override
            public void onItemSelected(String item, int itemPosition) {
                spinnerItem = item;
            }

            @Override
            public void onNothingSelected(String item) {
                spinnerItem = item;
            }
        });
    }


    private void setListener(final EditText editText, final LinearLayout linearLayout) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String customPhone = getPhoneNumber(editText.getText().toString());
                    if (linearLayout != null)
                        linearLayout.setTag(customPhone);
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String customPhone = getPhoneNumber(editText.getText().toString());
                if (linearLayout != null)
                    linearLayout.setTag(customPhone);
            }
        });
    }

    /**
     * Return the Formatted Phone Number beginning with the country code
     **/
    private String getPhoneNumber(String phone) {
        if (phone != null)
            if (!TextUtils.isEmpty(phone)) {
                if (phone.startsWith("0") && phone.length() == 10) {
                    return spinnerItem + phone.substring(phone.length() - 9);
                } else if (!phone.startsWith("0") && phone.length() <= 9) {
                    return spinnerItem + phone;
                } else if (phone.startsWith(spinnerItem) && phone.length() == 12) {
                    return phone;
                }

            }
        return phone;
    }

    /**=========================================================================================================================**/


    /**^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^**/
    /**============================= Below are the extra public functions that needs to be called   ============================**/

    
    public LinearLayout checkBox(ArrayList<String> list, final OnCheckBoxSelected callBack) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        if (list == null)
            return linearLayout;

        if (list.size() > 0) {
            CheckBox checkBox;

            for (String str : list) {
                checkBox = new CheckBox(mContext);
                checkBox.setLayoutParams(params);
                checkBox.setText(str);
                checkBox.setTag(str);
                final CheckBox finalCheckBox = checkBox;
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (callBack != null)
                            callBack.execute(finalCheckBox.getText().toString(), b);

                    }
                });
                innerViewsList.add(checkBox);
                linearLayout.addView(checkBox);
            }
        }

        viewList.add(linearLayout);
        return linearLayout;
    }

    public ArrayList<View> getViewList() {
        return viewList;
    }

    public ArrayList<View> getInnerViewsList() {
        return innerViewsList;
    }
    /**=========================================================================================================================**/


}
