package com.example.fingerprint;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;

import com.example.fingerprintCallbacks.AbstractFingerprintCallback;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintMasterLogic /*extends AppCompatActivity*/ {

    // Declare a string variable for authentication
    private static final String KEY_NAME = UUID.randomUUID().toString();
    private Cipher cipher;
    private KeyStore keyStore;

    private AbstractFingerprintCallback handler;
    private Activity appCompatActivity;



    public FingerprintMasterLogic(Activity appCompatActivity, AbstractFingerprintCallback handler) {
        // do not load com.example.fingerprint popup in case FingerprintConfigs not validated
        this.appCompatActivity = appCompatActivity;
        this.handler = handler;
    }

    public void startAuth() {
        // Now we can authenticate using com.example.fingerprint
        generateKey();

        if (initCipher()) {
            // If the cipher is initialized successfully, then create a CryptoObject
            // instance//
            FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);

            // Here, I’m referencing the AuthenticateFingerprintCallback class that we’ll create in the
            // next section. This class will be responsible
            // for starting the authentication process (via the startAuth method) and
            // processing the authentication process events//

            handler.startAuth(FingerprintUtils.getFingerprintManager(appCompatActivity), cryptoObject);
        }
    }

    // Create the generateKey method that we’ll use to gain access to the Android
    // keystore and generate the encryption key//

    private void generateKey() {
        try {
            // Obtain a reference to the Keystore using the standard Android keystore
            // container identifier (“AndroidKeystore”)//
            keyStore = KeyStore.getInstance("AndroidKeyStore");

            // Generate the key//
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            // Initialize an empty KeyStore//
            keyStore.load(null);

            // Initialize the KeyGenerator//
            keyGenerator.init(new
                    // Specify the operation(s) this key can be used for//
                    KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    // Configure this key so that the user has to confirm their identity with a
                    // com.example.fingerprint each time they want to use it//
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            // Generate the key//
            keyGenerator.generateKey();

        } catch (KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException
                | InvalidAlgorithmParameterException | CertificateException | IOException exc) {
            exc.printStackTrace();
        }
    }

    // Create a new method that we’ll use to initialize our cipher//
    private boolean initCipher() {
        try {
            // Obtain a cipher instance and configure it with the properties required for
            // com.example.fingerprint authentication//
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/" +
                    KeyProperties.BLOCK_MODE_CBC + "/" +
                    KeyProperties.ENCRYPTION_PADDING_PKCS7);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // Return true if the cipher has been initialized successfully//
            return true;
        }
        catch (KeyPermanentlyInvalidatedException e) {

            // Return false if cipher initialization failed//
            return false;
        }
        catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

}