package com.example.android_fingerprint_scanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fingerprint.FingerprintMasterLogic;
import com.example.fingerprint.FingerprintUtils;
import com.example.fingerprintCallbacks.AuthenticateFingerprintCallback;
import com.example.helper.DialogHelper;

public class MainActivity extends Activity {

    private ImageView isSdkVersionSupportedImageView;
    private ImageView isHardwareSupportedImageView;
    private ImageView isPermissionGrantedImageView;
    private ImageView isFingerprintAvailableImageView;

    private boolean isSdkVersionSupported;
    private boolean isHardwareSupported;
    private boolean isPermissionGranted;
    private boolean isFingerprintAvailable;
    private boolean isAllFlagsTrue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isSdkVersionSupportedImageView = findViewById(R.id.isSdkVersionSupportedImageView);
        isHardwareSupportedImageView = findViewById(R.id.isHardwareSupportedImageView);
        isPermissionGrantedImageView = findViewById(R.id.isPermissionGrantedImageView);
        isFingerprintAvailableImageView = findViewById(R.id.isFingerprintAvailableImageView);

        // evaluate Fingerprint Logic
        handleFingerprintLogic();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // reEvaluate Fingerprint Logic
        handleFingerprintLogic();
    }


    private void handleFingerprintLogic() {
        setFingerprintFlags();
        setImageViewsResources();
    }

    private void setFingerprintFlags() {
        // first check if SdkVersionSupported fingerprint [supported since (M = 23)]
        isSdkVersionSupported = FingerprintUtils.isSdkVersionSupported();


        if(isSdkVersionSupported) {
            // Now SdkVersionSupported, so we can use FingerprintUtils methods under @TargetApi(Build.VERSION_CODES.M)
            isHardwareSupported = FingerprintUtils.isHardwareSupported(this);
            isPermissionGranted = FingerprintUtils.isPermissionGranted(this);
            isFingerprintAvailable = FingerprintUtils.isFingerprintAvailable(this);
        } else {
            // isSdkVersionSupported = false, So all others flags will be false
            isHardwareSupported = false;
            isPermissionGranted = false;
            isFingerprintAvailable = false;
        }

        isAllFlagsTrue = isSdkVersionSupported && isHardwareSupported && isPermissionGranted && isFingerprintAvailable;
    }

    private void setImageViewsResources() {
        if(isSdkVersionSupported){
            isSdkVersionSupportedImageView.setImageResource(R.mipmap.icon_ok_flag);
        } else {
            isSdkVersionSupportedImageView.setImageResource(android.R.drawable.ic_delete);
        }

        if(isHardwareSupported){
            isHardwareSupportedImageView.setImageResource(R.mipmap.icon_ok_flag);
        } else {
            isHardwareSupportedImageView.setImageResource(android.R.drawable.ic_delete);
        }

        if(isPermissionGranted){
            isPermissionGrantedImageView.setImageResource(R.mipmap.icon_ok_flag);
        } else {
            isPermissionGrantedImageView.setImageResource(android.R.drawable.ic_delete);
        }

        if(isFingerprintAvailable){
            isFingerprintAvailableImageView.setImageResource(R.mipmap.icon_ok_flag);
        } else {
            isFingerprintAvailableImageView.setImageResource(android.R.drawable.ic_delete);
        }
    }



    public void authenticateByFingerprintButtonOnClick(View v){

        // open fingerprint dialog ONLY if all flags are true
        if (isAllFlagsTrue) {
            // call core code of [ authenticate By Fingerprint ]
            /*
                NOTE: isolate related code of authentication (should be from @TargetApi(Build.VERSION_CODES.M))
                in another method for compatibility and to fix
                Execution failed for task ':app:transformClassesWithInstantRunForDebug'
                Caused by: java.lang.ClassNotFoundException: android.hardware.fingerprint.FingerprintManager$AuthenticationCallback
             */
            authenticateByFingerprint();

        } else {

            // -----------------------------------
            // show msg into AlertDialog
            String msg = this.getResources().getText(R.string.FingerprintSorryText).toString();
            DialogHelper dh = new DialogHelper(this);
            dh.showAlert(msg);
            // -----------------------------------
        }

    }

    private void authenticateByFingerprint(){
        // *******************************************
        // authenticate by Fingerprint
        AuthenticateFingerprintCallback handler = new AuthenticateFingerprintCallback(this);
        FingerprintMasterLogic fingerprintMasterLogic = new FingerprintMasterLogic(this,handler);
        fingerprintMasterLogic.startAuth();
        // *******************************************
    }


    public void viewInformationMsg(View v){

        // *******************************
        // prepare corresponding info msg
        String msg = "";
        int id = v.getId();
        switch (id){
            case R.id.isSdkVersionSupportedTextView:
                msg = this.getText(R.string.isSdkVersionSupportedInfo).toString();
                break;
            case R.id.isHardwareSupportedTextView:
                msg = this.getText(R.string.isHardwareSupportedInfo).toString();
                break;
            case R.id.isPermissionGrantedTextView:
                msg = this.getText(R.string.isPermissionGrantedInfo).toString();
                break;
            case R.id.isFingerprintAvailableTextView:
                msg = this.getText(R.string.isFingerprintAvailableInfo).toString();
                break;
            default:
                break;
        }
        // *******************************

        // -----------------------------------
        // show msg into AlertDialog
        DialogHelper dh = new DialogHelper(this);
        dh.showAlert(msg);
        // -----------------------------------
    }



}
