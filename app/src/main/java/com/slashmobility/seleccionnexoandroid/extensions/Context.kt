package com.slashmobility.seleccionnexoandroid.extensions

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-03
 */

fun Context.isConnectedToNetwork(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return connectivityManager?.activeNetworkInfo?.isConnected ?: false
}