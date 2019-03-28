/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package io.github.alexjuca.biometrickit

import android.content.Context
import android.R.attr.subtitle
import android.annotation.TargetApi
import android.os.Build
import android.content.DialogInterface
import android.annotation.SuppressLint
import android.hardware.biometrics.BiometricPrompt


open class BiometricKit : BiometricKitV23 {

    constructor(biometricBuilder: BiometricBuilder): super() {
        this.context = biometricBuilder.context
        this.title = biometricBuilder.title.toString()
        this.subTitle = biometricBuilder.subtitle.toString()
        this.description = biometricBuilder.description.toString()
        this.negativeButtonText = biometricBuilder.negativeButtonText.toString()
        this.instructions = biometricBuilder.instruction.toString()
    }


    class BiometricBuilder(val context: Context) {
        var title: String? = null
        var subtitle: String? = null
        var description: String? = null
        var negativeButtonText: String? = null
        var instruction: String? = null

        fun setTitle(title: String?): BiometricBuilder {
            this.title = title
            return this
        }

        fun setSubtitle(subtitle: String?): BiometricBuilder {
            this.subtitle = subtitle
            return this
        }

        fun setDescription(description: String?): BiometricBuilder {
            this.description = description
            return this
        }


        fun setNegativeButtonText(negativeButtonText: String): BiometricBuilder {
            this.negativeButtonText = negativeButtonText
            return this
        }

        fun setInstructions(instructions: String): BiometricBuilder {
            this.instruction = instructions
            return this
        }

        fun build(): BiometricKit {
            return BiometricKit(this)
        }
    }

    fun authenticate(biometricCallback: BiometricKitCallback, showPrompt: Boolean) {
        if (title == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog title cannot be null")
        }


        if (subtitle == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog subtitle cannot be null")
        }


        if (description == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog description cannot be null")
        }

        if (negativeButtonText == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog negative button text cannot be null")
        }


        if (isSupported()) {
            biometricCallback.onSdkVersionNotSupported()
        }

        if (!context.biometricHasPermissionBeenGranted()) {
            biometricCallback.onBiometricAuthenticationPermissionNotGranted()
        }

        if (!context.isSupportedHardware()) {
            biometricCallback.onBiometricAuthenticationNotSupported()
        }

        if (!context.biometricHasBeenConfigured()) {
            biometricCallback.onBiometricAuthenticationNotAvailable()
        }

        if (showPrompt) {
            showPrompt(biometricCallback, showPrompt)
        }
    }

    private fun showPrompt(biometricCallback: BiometricKitCallback, showPrompt: Boolean) {
        when (isBiometricPromptAvailable()) {
            true -> { showPrompt(biometricCallback) }
            false -> { show(biometricCallback, showPrompt) }
        }
    }

    @TargetApi(Build.VERSION_CODES.P)
    @SuppressLint("MissingPermission")
    private fun showPrompt(biometricKitCallback: BiometricKitCallback) {
        BiometricPrompt.Builder(context)
            .setTitle(title)
            .setSubtitle(subTitle)
            .setDescription(description)
            .setNegativeButton(negativeButtonText, context.mainExecutor,
                DialogInterface.OnClickListener { _, i -> biometricKitCallback.onAuthenticationCancelled() })
            .build()
            .authenticate(android.os.CancellationSignal(), context.mainExecutor,
                BiometricCallbackV28(biometricKitCallback)
            )
    }

}