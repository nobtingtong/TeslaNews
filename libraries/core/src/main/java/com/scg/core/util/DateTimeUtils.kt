package com.scg.core.util

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {

    fun now(): String {
        val sdf = SimpleDateFormat("yyyy-mm-dd")
        return sdf.format(Date())
    }
}