package com.slashmobility.seleccionnexoandroid.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val patternDate : String = "dd/MM/yyyy"

    fun getDateTime(timestamp: Long): String {
        return try {
            val sdf = SimpleDateFormat(patternDate, Locale.getDefault())
            val netDate = Date(timestamp)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }
}