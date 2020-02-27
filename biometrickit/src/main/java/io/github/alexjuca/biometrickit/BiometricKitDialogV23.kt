/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package io.github.alexjuca.biometrickit

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.interpolator.view.animation.FastOutSlowInInterpolator


class BiometricKitDialogV23 : BottomSheetDialog, View.OnClickListener {
    private lateinit var mContext: Context
    private lateinit var title: TextView
    private lateinit var subTitle: TextView
    private lateinit var description: TextView
    private lateinit var instructions: TextView
    private lateinit var icon: ImageView
    private lateinit var cancel: TextView
    private lateinit var callback: BiometricKitCallback

    constructor(context: Context) : super(context) {
        mContext = context.applicationContext
        setUpBiometricDialog()
    }

    constructor(context: Context, biometricKitCallback: BiometricKitCallback) : super(context) {
        mContext = context.applicationContext
        callback = biometricKitCallback
        setUpBiometricDialog()
    }

    constructor(@NonNull context: Context, theme: Int) : super(context, theme)

    constructor(@NonNull context: Context, cancelable: Boolean, onCancelListener: DialogInterface.OnCancelListener) : super(context, cancelable, onCancelListener)

    private fun setUpBiometricDialog() {
        val view = layoutInflater.inflate(R.layout.biometric_auth_bottom_sheet, null)
        setContentView(view)
        title = view.findViewById(R.id.title)
        subTitle = view.findViewById(R.id.subtitle)
        description = view.findViewById(R.id.description)
        icon = view.findViewById(R.id.biometric_image)
        cancel = view.findViewById(R.id.cancel_and_dismiss)
        instructions = view.findViewById(R.id.finger_print_instructions)
        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            icon,
            PropertyValuesHolder.ofFloat("scaleX", 1.05f),
            PropertyValuesHolder.ofFloat("scaleY", 1.05f)
        )
        scaleDown.duration = 1100
        scaleDown.repeatCount = ObjectAnimator.INFINITE
        scaleDown.repeatMode = ObjectAnimator.REVERSE
        scaleDown.interpolator = FastOutSlowInInterpolator()
        scaleDown.start()
        cancel.setOnClickListener {
            onClick(it)
        }
    }

    fun setTitle(title: String) {
        this.title.text = title
    }

    fun setDescription(description: String) {
        this.description.text = description
    }

    fun setInstructions(instructions: String) {
        this.instructions.text = instructions
    }

    fun setSubtitle(subtitle: String) {
        this.subTitle.text = subtitle
    }

    fun setNegativeButton(negativeText: String) {
        this.cancel.text = negativeText
    }

    override fun onClick(v: View?) {
        dismiss()
        callback.onAuthenticationCancelled()
    }

}