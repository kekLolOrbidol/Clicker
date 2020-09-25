package com.arkadiusz.surma.cookieclicker.api

import android.content.Context
import android.content.SharedPreferences

class CustomSharedPreferences(val context: Context) {

    var pref : SharedPreferences? = null

    fun getSp(name : String){
        pref = context.getSharedPreferences("PREF", Context.MODE_PRIVATE)
    }

    fun getStr(name : String) : String?{
        return pref?.getString(name, null)
    }

    fun putStr(name : String, value : String){
        pref?.edit()?.putString(name, value)?.apply()
    }
}