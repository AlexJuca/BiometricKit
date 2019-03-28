/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package io.github.alexjuca.biometrickit

import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
class BiometricCallbackV28: BiometricPrompt.AuthenticationCallback {
    private var biometricKitCallback: BiometricKitCallback

    constructor(biometricKitCallback: BiometricKitCallback) {
        this.biometricKitCallback = biometricKitCallback
    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        this.biometricKitCallback.onAuthenticationSuccessful()
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        super.onAuthenticationError(errorCode, errString)
        this.biometricKitCallback.onAuthenticationError(errorCode, errString)
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        this.biometricKitCallback.onAuthenticationFailed()
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        super.onAuthenticationHelp(helpCode, helpString)
        this.biometricKitCallback.onAuthenticationHelp(helpCode, helpString)
    }
}