package com.daisy.journalapp.core.presentation.utils

import android.content.Context
import android.widget.Toast
import com.daisy.journalapp.core.presentation.UiText

fun Context.showToast(
    message: UiText,
    duration: Int = Toast.LENGTH_LONG,
) {
    Toast.makeText(this, message.asString(this), duration).show()
}