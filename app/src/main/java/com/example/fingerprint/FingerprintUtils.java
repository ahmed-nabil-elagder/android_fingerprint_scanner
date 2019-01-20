package com.example.fingerprint;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;


public class FingerprintUtils {



    /*
     * Condition : Check if the android version in device is greater than
     * Marshmallow, since fingerprint authentication is only supported
     * from Android 6.0.
     * Note: If your project's min Sdk version is 23 or higher,
     * then you won't need to perform this check.
     * */
    public static boolean isSdkVersionSupported() {
        // If you’ve set your app’s minSdkVersion to anything lower than 23, then you’ll
        // need to verify that the device is running Marshmallow
        // or higher before executing any fingerprint-related code
        boolean isSdkVersionSupported = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
        return isSdkVersionSupported;
    }

    /*
     * Condition : Check if the device has fingerprint sensors.
     * Note: If you marked fingerprint as something that
     * your app requires (android:required="true"), then you don't need
     * to perform this check.
     *
     * */
    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isHardwareSupported(Context context) {
        // TODO - return false - if (!fingerprintManager.isHardwareDetected()) {
        // S5
        // https://stackoverflow.com/questions/42575967/fingerprintmanagercompat-ishardwaredetected-return-false-in-targetapi-25/43618556
        // Check whether the device has a fingerprint sensor//
        // TODO - return false for Samsung shitty devices: like S5
        // https://stackoverflow.com/q/37935959/4024146
        // If a fingerprint sensor isn’t available, then inform the user that they’ll be
        // unable to use your app’s fingerprint functionality//
        //Toast.makeText(context, "Your device doesn't support fingerprint authentication", Toast.LENGTH_LONG).show();
        //return false;

        boolean isHardwareSupported = getFingerprintManager(context).isHardwareDetected();
        return isHardwareSupported;
    }
    /*
     * Condition : Fingerprint authentication can be matched with a
     * registered fingerprint of the user. So we need to perform this check
     * in order to enable fingerprint authentication
     *
     * */
    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isFingerprintAvailable(Context context) {
        // TODO - return false - if (!fingerprintManager.isHardwareDetected()) {
        // TODO - return false for Samsung shitty devices: like S5
        boolean isFingerprintAvailable = getFingerprintManager(context).hasEnrolledFingerprints();
        return isFingerprintAvailable;
    }


    /*
     * Condition : Check if the permission has been added to
     * the app. This permission will be granted as soon as the user
     * installs the app on their device.
     *
     * */
    public static boolean isPermissionGranted(Context context) {
        boolean isPermissionGranted = ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) ==
                PackageManager.PERMISSION_GRANTED;
        return isPermissionGranted;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static FingerprintManager getFingerprintManager(Context context){
        // TODO 2 ways init ( FingerprintManager or FingerprintManagerCompat)
        //FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        return fingerprintManager;
    }


}
