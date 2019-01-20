package com.example.fingerprintCallbacks;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.android_fingerprint_scanner.R;


@TargetApi(Build.VERSION_CODES.M)
public class AuthenticateFingerprintCallback extends AbstractFingerprintCallback {

    public AuthenticateFingerprintCallback(Activity context) {
        // call parent constructor of abstract class
        super(context);

        // link (Cancel button) Listener to be this class
        if(super.getDialog_fingerprint() != null){
            Button dialogButtonCancel = super.getDialog_fingerprint().findViewById(R.id.dialogButtonCancel);
            dialogButtonCancel.setOnClickListener(this);
        }

    }

    @Override
    //onAuthenticationSucceeded is called when a com.example.fingerprint has been successfully matched
    // to one of the fingerprints stored on the user’s device
    // No further callbacks will be made
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {
        // just dismiss dialog
        if(super.getDialog_fingerprint() != null){
            super.getDialog_fingerprint().dismiss();
        }

        CharSequence msg = super.getContext().getResources().getText(R.string.FingerprintAuthorizedSuccessfullyText);
        Toast.makeText(super.getContext(), msg, Toast.LENGTH_LONG).show();

    }

    @Override
    //onAuthenticationError is called when a fatal error has occurred.
    // It provides the error code and error message as its parameters
    // No further callbacks will be made
    public void onAuthenticationError(int errMsgId, CharSequence errString) {

        // just dismiss fingerprint dialog
        if(super.getDialog_fingerprint() != null){
            super.getDialog_fingerprint().dismiss();
        }

        // show error msg from system
        Toast.makeText(super.getContext(), errString, Toast.LENGTH_LONG).show();

    }

    @Override
    // (Cancel button)
    public void onCancelButtonFingerprintDialog(View v) {
        // stop listen to com.example.fingerprint input
        super.getCancellationSignal().cancel();

        // Note: after [ stop listen to com.example.fingerprint input ]
        // onAuthenticationError: will be called after [ getCancellationSignal().cancel() ]
        // CharSequence errString => Fingerprint operation canceled.
        // so no need to rewrite handler action again

    }

}