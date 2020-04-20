
<p align="center">
    <a href="#" target="_blank">
        <img src="logo.png" width="64" alt="BiometricKit Fingerprint Authentication Library" />
    </a>
</p>



[![](https://jitpack.io/v/AlexJuca/BiometricKit.svg)](https://jitpack.io/#AlexJuca/BiometricKit)


BiometricKit is a simple to use and android fingerprint authentication Library written in pure kotlin for Android M (API 23) and above that follows the material design guidelines. <a href="https://material.io/design/platform-guidance/android-fingerprint.html#standard-fingerprint" target="_blank">Guidelines</a>


<img src="https://raw.githubusercontent.com/AlexJuca/BiometricKit/master/demo.png" width="360" height="640">

Download
--------

Download the latest version via Gradle:

**Step 1:** Include jitpack to your projects build.gradle file
```
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
    }
}
```

**Step 2:** Add the Kiyo dependency to your build.gradle file

```
dependencies
{
	implementation 'com.github.AlexJuca:BiometricKit:v0.0.1'
}
```


Usage
----------

**Step 1:** Define the permissions needed. 
```xml
<uses-permission android:name="android.permission.USE_BIOMETRIC"
        android:required="false" />
    <uses-permission android:name="android.permission.USE_FINGERPRINT"
        android:required="false" />
```

**Step 2:** Set up a BiometricKitCallback to handle callbacks. 
```java

private val biometricKitCallback: BiometricKitCallback = object: BiometricKitCallback {
        override fun onSdkVersionNotSupported() {

        }

        override fun onBiometricAuthenticationNotSupported() {

        }

        override fun onBiometricAuthenticationNotAvailable() {

        }

        override fun onBiometricAuthenticationPermissionNotGranted() {

        }

        override fun onBiometricAuthenticationInternalError(error: String) {

        }

        override fun onAuthenticationFailed() {

        }

        override fun onAuthenticationCancelled() {
            prompt.authenticate(this, false)
        }

        override fun onAuthenticationSuccessful() {
            Toast.makeText(this@MainActivity, "Bought successfully", Toast.LENGTH_SHORT).show()
        }

        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {

        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {

        }

    }
```

**Step 3:** 

```java
   val prompt = BiometricKit.BiometricBuilder(this as Context)
            .setTitle("Confirm Payment")
            .setSubtitle("corextechnologies@gmail.com")
            .setDescription("Paying for Kamba Gas Service")
            .setNegativeButtonText("Cancel").setInstructions("Touch the fingerprint sensor")
            .build()

        prompt.authenticate(biometricKitCallback, true)
```

Applications using Biometrickit
-------
If you are using AppIntro in your app and would like to be listed here, please let us know by commenting in [this issue](https://github.com/AlexJuca/BiometricKit/issues/2)!

* [Usekamba](https://play.google.com/store/apps/details?id=com.usekamba.kamba.kamba)

## Version history
``` 0.0.1: Initial Version - 28/03/2019 ``` <br/>

License
--------

    Copyright Alexandre Antonio Juca <corextechnologies@gmail.com>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

