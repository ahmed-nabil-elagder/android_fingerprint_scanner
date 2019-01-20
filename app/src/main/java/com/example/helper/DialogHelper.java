package com.example.helper;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.android_fingerprint_scanner.R;

public class DialogHelper {

    // fields
    private Activity context;

    public DialogHelper(Activity context) {
        // set private fields
        this.context = context;
    }


    public void showAlert(String msg) {
        // -----------------------------------
        // show corresponding info msg into AlertDialog
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                context.getResources().getText(R.string.OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        // -----------------------------------
    }
}
