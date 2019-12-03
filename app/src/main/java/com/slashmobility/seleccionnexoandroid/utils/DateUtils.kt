package com.slashmobility.seleccionnexoandroid.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val patternDate : String = "MM/dd/yyyy"

    fun getDateTime(timesamp: Long): String {
        return try {
            val sdf = SimpleDateFormat(patternDate)
            val netDate = Date(timesamp)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }
}