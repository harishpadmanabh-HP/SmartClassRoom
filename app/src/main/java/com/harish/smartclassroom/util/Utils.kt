package com.harish.smartclassroom.util

import android.app.Application
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat.getSystemService

class Utils {


 companion object {
     fun hasInternetConnection(context: Context): Boolean {
         val connectivityManager = context.applicationContext.getSystemService(
                 CONNECTIVITY_SERVICE
         ) as ConnectivityManager
         val activeNetwork = connectivityManager.activeNetwork ?: return false
         val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                 ?: return false
         return when {
             capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
             capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
             capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
             else -> false
         }
     }
 }



}