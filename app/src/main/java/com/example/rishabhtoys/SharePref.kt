package com.example.rishabhtoys

import android.content.Context
import android.content.SharedPreferences

class SharePref(context: Context) {

    private var sharedPreferences:SharedPreferences =
        context.getSharedPreferences("toys_rishabh_pref",Context.MODE_PRIVATE)

    companion object {

        private var sharePrefInstance : SharePref ? = null

        fun getSharePrefInstance(context: Context) : SharePref?{
            if(sharePrefInstance == null) {
                sharePrefInstance = SharePref(context)
            }
            return sharePrefInstance
        }

    }

    fun setLockPattern(encodedKey : String) {
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("lockPattern",encodedKey)
        editor.apply()

    }

    fun getLockPattern() : String?{
        return sharedPreferences.getString("lockPattern",null)
    }

    fun clearSharePref(){
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

}