package com.levkovskiy.ukadtest.utils

import android.icu.text.SimpleDateFormat
import android.net.ParseException

object DateUtils {
    fun formatDate(sourceDate: String): String? {
        val fromUser = SimpleDateFormat("yyyy-MM-dd")
        val myFormat = SimpleDateFormat("dd MMM")
        try {
            return myFormat.format(fromUser.parse(sourceDate))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }
}