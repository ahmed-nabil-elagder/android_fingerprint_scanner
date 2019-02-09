# android_fingerprint_scanner

Its an Android application which show all Fingerprint flags status, and scan Fingerprint (Authentication) if device support that and at least one fingerprint registered in device  
  
  
Minimum supported Android API version (minSdkVersion = 21)  
<img src="https://img.shields.io/badge/API-21%2B-blue.svg" >  

Fot your information  
Android Fingerprint Authentication is only supported from Android 6.0 / API-23  
<img src="https://img.shields.io/badge/API-23%2B-blue.svg" >  


## To be able use fingerprint scanner, Four conditions should be passed    
Condition |  Description
|:--------|:-----------|
isSdkVersionSupported     | Check if the Android version in this device is greater than Marshmallow, since Fingerprint Authentication is only supported from Android 6.0 / API-23
isHardwareSupported       | Check if this Android device has Fingerprint sensors or not
isPermissionGranted       | Check if the permission has been added to the application, This permission will be granted as soon as the user installs the application on their device
isFingerprintAvailable    | Check if there are any registered Fingerprints on this device or not

# Screenshots  

## Happy scenario  
If *All Fingerprint checks* **are OK**, so **We can authenticate** by Fingerprint.  

All Fingerprint checks are OK |  We can authenticate by Fingerprint |    DONE
:----------------------------:|:-----------------------------------:|:----------------:
<img src="https://github.com/ahmednabil88/images-host/blob/master/Screenshot_20190121-113309_Fingerprint%20Scanner.jpg" width=300>  |  <img src="https://github.com/ahmednabil88/images-host/blob/master/Screenshot_20190121-113319_Fingerprint%20Scanner.jpg" width=300> |  <img src="https://github.com/ahmednabil88/images-host/blob/master/Screenshot_20190121-113337_Fingerprint%20Scanner.jpg" width=300>


## Others scenarios  
If *All Fingerprint checks* **are not OK**, so **We can not authenticate** by Fingerprint.  

Example 1 |  Example 2 | Error Message
:----------------------------:|:--------------:|:----------------:
<img src="https://github.com/ahmednabil88/images-host/blob/master/Screenshot_2019-01-21-11-37-02.png" width=300>  |  <img src="https://github.com/ahmednabil88/images-host/blob/master/Screenshot_2019-01-21-11-36-57.png" width=300> | <img src="https://github.com/ahmednabil88/images-host/blob/master/Screenshot_2019-01-24-00-37-59.png" width=300>
