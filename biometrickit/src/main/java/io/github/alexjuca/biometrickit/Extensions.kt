/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package io.github.alexjuca.biometrickit

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

fun isBiometricPromptAvailable() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

fun isSupported() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

@SuppressLint("MissingPermission")
fun Context.biometricHasBeenConfigured() = FingerprintManagerCompat.from(this).hasEnrolledFingerprints()

@SuppressLint("MissingPermission")
fun Context.isSupportedHardware() = FingerprintManagerCompat.from(this).isHardwareDetected

fun Context.biometricHasPermissionBeenGranted() = ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC) ==
        PackageManager.PERMISSION_GRANTED