package com.jamper.viewscreator.UTILS;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.jamper.viewscreator.CALLBACK.CallBack;
import com.jamper.viewscreator.R;

import static com.jamper.viewscreator.UTILS.Utils.LOG;
import static com.jamper.viewscreator.UTILS.Utils.StringToBitMap;
import static com.jamper.viewscreator.UTILS.Utils.getBase64Bytes;


/**
 * Created by jamper on 6/1/2017.
 */

public class SignatureDialog {
    private Activity mContext;
    private SignaturePad signaturePad;
    private boolean isSignatureSigned;
    private String signature;
    private String signatureName;
    private ImageView imageView;
    private CallBack callBack;


    public SignatureDialog(@NonNull Activity context) {
        this.mContext = context;
        this.isSignatureSigned = false;
        signature = "";
        signatureName = "";
        show();
    }

    public SignatureDialog setSignatureName(String signatureName) {
        this.signatureName = signatureName;
        return this;
    }

    public SignatureDialog setImageView(ImageView imageView) {
        this.imageView = imageView;
        return this;
    }


    public void returnValue(CallBack callBack) {
        this.callBack = callBack;
        if (callBack != null)
            callBack.execute(signature);
    }


    private void setMargins(LinearLayout.LayoutParams params, int value, int left, int right) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (value * scale + 0.5f);
        params.setMargins(left, 0, right, dpAsPixels);
    }


    private View getLayout() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                550);
        setMargins(params, 8, 0, 0);

        LinearLayout lin = new LinearLayout(mContext);
        lin.setOrientation(LinearLayout.VERTICAL);
        lin.setLayoutParams(params);

        signaturePad = new SignaturePad(mContext, null);
        signaturePad.setLayoutParams(params);
        signaturePad.setBackgroundColor(mContext.getResources().getColor(R.color.white_smoke));

        lin.addView(signaturePad);

        return lin;
    }


    public SignatureDialog show() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Sign your Signature");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Attach", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onAttachOnCLick(dialog);
            }
        });

        alertDialog.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onClearOnCLick(dialog);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onCancelOnCLick(dialog);
            }
        });

        alertDialog.setView(getLayout());
        onSignedListeners();

        alertDialog.show();
        return this;
    }


    /**
     * OnSignedListener for Signature Pad
     **/
    private void onSignedListeners() {
        onSignatureSigned();
        setAlreadySignedSignature();
    }

    /**
     * Set already signed Signature Bitmap to Signature Pad
     **/
    private void setAlreadySignedSignature() {
        if (Initialiser.getImageData().get(this.signatureName) != null) {
            String data = Initialiser.getImageData().get(this.signatureName);
            Bitmap bp = StringToBitMap(data);
            if (bp != null)
                signaturePad.setSignatureBitmap(bp);
        }

    }

    /**
     * OnSign Listener for Signature
     **/
    private void onSignatureSigned() {
        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                signaturePad.setPenColor(R.color.asparagus);
            }

            @Override
            public void onSigned() {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bp = signaturePad.getSignatureBitmap();
                        signature = getBase64Bytes(bp);
                        isSignatureSigned = true;
                        if (TextUtils.isEmpty(signatureName))
                            signatureName = "signature";

                        //Save image into the database
                        Initialiser.getImageData().put(signatureName, signature);
                        LOG("Signature Signed >> " + signature);
                        try {
                            mContext.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (imageView != null) {
                                        imageView.setImageBitmap(StringToBitMap(getBase64Bytes(bp)));
                                        imageView.setTag(signatureName);
                                    }
                                }
                            });

                        } catch (Exception e) {
                            LOG("Error >>> " + e.getMessage());
                        }

                    }
                });
            }

            @Override
            public void onClear() {

            }
        });
    }

    /**
     * Clear Signature OnClickListener
     **/
    private void onClearOnCLick(DialogInterface dialog) {
        if (isSignatureSigned) {
            dialog.dismiss();
            signaturePad.clear();
            isSignatureSigned = false;
            if (TextUtils.isEmpty(signatureName))
                signatureName = "signature";
            Initialiser.getImageData().put(signatureName, "");

            if (imageView != null) {
                new Utils(mContext).setImageResource(R.drawable.signature, imageView);
            }

            if (callBack != null)
                callBack.execute("");
        }
        show();
    }

    /**
     * Attach Signature OnClickListener
     **/
    private void onAttachOnCLick(DialogInterface dialog) {
        if (isSignatureSigned) {
            dialog.dismiss();
          //  Toast.makeText(mContext, signatureName + " Saved", Toast.LENGTH_SHORT).show();
        } else {
            show();
            messageBox("Please Sign your signature");
        }
    }

    /**
     * Cancel signing Signature OnClickListener
     **/
    private void onCancelOnCLick(DialogInterface dialog) {
        isSignatureSigned = false;
        if (TextUtils.isEmpty(signatureName))
            signatureName = "signature";
        new Utils(mContext).cacheStringData("", signatureName);
        //Clear image from hashmap
        Initialiser.getImageData().put(signatureName, "");
        if (callBack != null)
            callBack.execute("");
        dialog.dismiss();
    }

    private void messageBox(String message) {
        new AlertDialog.Builder(mContext)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }


}
