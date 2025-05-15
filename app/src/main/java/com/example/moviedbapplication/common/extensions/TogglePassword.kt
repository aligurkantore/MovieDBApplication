package com.example.moviedbapplication.common.extensions

import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.moviedbapplication.R

fun EditText.togglePasswordVisibility() {
    val isVisible = transformationMethod != null
    val drawableId = if (isVisible) R.drawable.ic_eye_open else R.drawable.ic_eye_close
    val drawable = ContextCompat.getDrawable(context, drawableId)
    transformationMethod =
        if (isVisible) null else PasswordTransformationMethod.getInstance()
    setSelection(text?.length ?: 0)
    drawable?.let { setCompoundDrawablesWithIntrinsicBounds(null, null, it, null) }
}