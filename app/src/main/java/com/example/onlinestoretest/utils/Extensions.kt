package com.example.onlinestoretest.utils

import android.content.Context
import android.util.TypedValue

fun Int.dpToPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()
}