/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package io.github.alexjuca.biometrickitdemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.alexjuca.biometrickit.BiometricKit
import io.github.alexjuca.biometrickit.BiometricKitCallback


class MainActivity : AppCompatActivity() {

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

        }

        override fun onAuthenticationSuccessful() {

        }

        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {

        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prompt: BiometricKit = BiometricKit.BiometricBuilder(this as Context)
            .setTitle("Confirm Payment")
            .setSubtitle("corextechnologies@gmail.com")
            .setDescription("Paying for Kamba Gas Service")
            .setNegativeButtonText("Cancel").setInstructions("Touch the fingerprint sensor")
            .build()

        prompt.authenticate(biometricKitCallback, true)
    }
}
