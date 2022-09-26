package com.scg.core.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.toDateTimeDisplay(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter = SimpleDateFormat("MMM dd, HH:mm")
    return formatter.format(parser.parse(this))
}