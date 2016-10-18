package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by luisresende on 18/10/16.
 */

public class FeedbackManager {

    static public void createToast(Context context, String message){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

    static public ProgressDialog createProgressDialog (Context context, String message){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.show();
        return dialog;
    }
}
