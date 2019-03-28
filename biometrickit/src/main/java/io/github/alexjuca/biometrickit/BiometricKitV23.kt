/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package io.github.alexjuca.biometrickit

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import java.util.*
import javax.crypto.KeyGenerator
import android.security.keystore.KeyProperties
import android.security.keystore.KeyGenParameterSpec
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import android.security.keystore.KeyPermanentlyInvalidatedException
import androidx.core.os.CancellationSignal
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey


@TargetApi(Build.VERSION_CODES.M)
@SuppressLint("MissingPermission")
open class BiometricKitV23 {
    private val KEY = UUID.randomUUID().toString()
    private lateinit var keyStore: KeyStore
    private lateinit var keyGenerator: KeyGenerator
    private lateinit var cipher: Cipher
    private lateinit var cryptoObject: FingerprintManagerCompat.CryptoObject
    protected lateinit var context: Context
    protected lateinit var title: String
    protected lateinit var subTitle: String
    protected lateinit var description: String
    protected lateinit var negativeButtonText: String
    protected lateinit var instructions: String
    private lateinit var biometricKitDialogV23: BiometricKitDialogV23

    fun show(biometricKitCallback: BiometricKitCallback, showDialog: Boolean) {
        generateCryptoKey()
        if (initCipher()) {
            cryptoObject = FingerprintManagerCompat.CryptoObject(cipher)
            val fingerprintManagerCompat = FingerprintManagerCompat.from(context)
            fingerprintManagerCompat.authenticate(cryptoObject, 0, CancellationSignal(),
                object: FingerprintManagerCompat.AuthenticationCallback() {
                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        biometricKitDialogV23.setInstructions(context.getString(R.string.biometric_authentication_failed))
                    }

                    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
                        super.onAuthenticationError(errMsgId, errString)
                        setDialogState(errString.toString())
                        biometricKitCallback.onAuthenticationError(errMsgId, errString)
                    }

                    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
                        super.onAuthenticationHelp(helpMsgId, helpString)
                        setDialogState(helpString.toString())
                        biometricKitCallback.onAuthenticationHelp(helpMsgId, helpString)
                    }

                    override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
                        super.onAuthenticationSucceeded(result)
                        dismiss()
                        biometricKitCallback.onAuthenticationSuccessful()

                    }
                }, null)
            if (showDialog) {
                showBiometricKitPrompt(biometricKitCallback)
            }
        }
    }

    fun dismiss() {
        biometricKitDialogV23.apply {
            biometricKitDialogV23.dismiss()
        }
    }

    private fun showBiometricKitPrompt(biometricKitCallback: BiometricKitCallback) {
        biometricKitDialogV23 = BiometricKitDialogV23(context, biometricKitCallback)
        biometricKitDialogV23.setTitle(title)
        biometricKitDialogV23.setSubtitle(subTitle)
        biometricKitDialogV23.setDescription(description)
        biometricKitDialogV23.setNegativeButton(negativeButtonText)
        biometricKitDialogV23.setInstructions(instructions)
        biometricKitDialogV23.show()
    }

    private fun setDialogState(status: String) {
        biometricKitDialogV23.apply {
            biometricKitDialogV23.setInstructions(status)
        }
    }

    private fun generateCryptoKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyGenerator.init(
                KeyGenParameterSpec.Builder(KEY, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build()
            )
            keyGenerator.generateKey()

        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchProviderException) {
            e.printStackTrace()
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun initCipher():Boolean {
        try {
            cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7
            )
        }
        catch (e:NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get Cipher", e)
        }
        catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e)
        }

        try {
            keyStore.load(
                null)
            val key = keyStore.getKey(KEY, null) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE, key)
            return true


        }
        catch (e:KeyPermanentlyInvalidatedException) {
            return false

        }
        catch (e:KeyStoreException) {

            throw RuntimeException("Failed to init Cipher", e)
        }
        catch (e:CertificateException) {
            throw RuntimeException("Failed to init Cipher", e)
        }
        catch (e:UnrecoverableKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }
        catch (e:IOException) {
            throw RuntimeException("Failed to init Cipher", e)
        }
        catch (e:NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init Cipher", e)
        }
        catch (e:InvalidKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }
    }
}