package com.yandex.academy.mobdev.service

import android.content.Context
import android.graphics.Typeface
import android.support.annotation.StringRes
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.Toast

object Toast {

    const val LENGTH_SHORT = Toast.LENGTH_SHORT

    const val LENGTH_LONG = Toast.LENGTH_LONG

    fun makeText(context: Context, @StringRes resId: Int, duration: Int): Toast {
        return makeText(context, context.getString(resId), duration)
    }

    fun makeText(context: Context, string: String, duration: Int): Toast {
        val text = SpannableString(string.toUpperCase())
        text.setSpan(StyleSpan(Typeface.BOLD), 0, text.length, Spannable.SPAN_MARK_POINT)
        return Toast.makeText(context, text, duration)
    }
}