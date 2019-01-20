package com.example.fingerprintCallbacks;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;
import com.example.android_fingerprint_scanner.R;
import com.example.fingerprint.FingerprintUtils;


@TargetApi(Build.VERSION_CODES.M)
abstract public class AbstractFingerprintCallback
        extends FingerprintManager.AuthenticationCallback
        implements View.OnClickListener {


    // fields
    private Activity context;
    private Dialog dialog_fingerprint;
    private CancellationSignal cancellationSignal;

    // constructor
    public AbstractFingerprintCallback(Activity context){
        // set private fields
        this.context = context;

        boolean isPermissionGranted = FingerprintUtils.isPermissionGranted(context);
        boolean isFingerprintAvailable = FingerprintUtils.isFingerprintAvailable(context);

        if(isPermissionGranted && isFingerprintAvailable){
            // -----------------------------------
            // just prepare finger print dialog
            // open dialog in full-screen mode
            dialog_fingerprint = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog_fingerprint.setContentView(R.layout.fingerprint_dialog);
            dialog_fingerprint.setCanceledOnTouchOutside(false);
            dialog_fingerprint.setCancelable(false); // Prevent back button from closing a dialog box

            // show the dialog
            dialog_fingerprint.show();
            // -----------------------------------
        }
    }

    // methods - getters
    public Activity getContext() {
        return context;
    }
    public Dialog getDialog_fingerprint() {
        return dialog_fingerprint;
    }
    public CancellationSignal getCancellationSignal() { return cancellationSignal; }


    // methods

    //Implement the startAuth method, which is responsible for starting the com.example.fingerprint authentication process//
    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject){
        // You should use the CancellationSignal method whenever your app can no longer process user input, for example when your app goes
        // into the background. If you don’t use this method, then other apps will be unable to access the touch sensor, including the lock screen!//
        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    //onAuthenticationHelp is called when a non-fatal error has occurred. This method provides additional information about the error,
    //so to provide the user with as much feedback as possible I’m incorporating this information into my toast//
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(context, helpString, Toast.LENGTH_LONG).show();
    }

    @Override
    //onAuthenticationFailed is called when the com.example.fingerprint does not match with any of the fingerprints registered
    // max further callbacks will be made (max 5 attempts)
    public void onAuthenticationFailed() {
        CharSequence msg = context.getText(R.string.FingerprintAuthenticationFailedText);
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    // (Cancel button)
    public void onClick(View v) {
        // just dismiss dialog
        if(dialog_fingerprint != null){
            dialog_fingerprint.dismiss();
        }

        this.onCancelButtonFingerprintDialog(v);
    }


    // abstract functions
    abstract public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result);
    abstract public void onAuthenticationError(int errMsgId, CharSequence errString);
    abstract public void onCancelButtonFingerprintDialog(View v);

}