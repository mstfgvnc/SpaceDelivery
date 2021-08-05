package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferences {

    companion object {

        private val SPACESHIP_UGS = "spaceship_ugs"
        private val SPACESHIP_EUS = "spaceship_eus"
        private val SPACESHIP_DS = "spaceship_ds"
        private val SPACESHIP_NAME = "spaceship_name"

        private var sharedPreferences: SharedPreferences? = null

        @Volatile private var instance: CustomSharedPreferences? = null
        private val lock = Any()

        operator fun invoke(context: Context) : CustomSharedPreferences = instance ?: synchronized(lock) {
            instance ?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPreferences(context: Context) : CustomSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }

    }

    fun saveUgs(value: Int) {
        sharedPreferences?.edit(commit = true){
            putInt(SPACESHIP_UGS,value)
        }
    }

    fun saveEus(value: Int) {
        sharedPreferences?.edit(commit = true){
            putInt(SPACESHIP_EUS,value)
        }
    }

    fun saveDs(value: Int) {
        sharedPreferences?.edit(commit = true){
            putInt(SPACESHIP_DS,value)
        }
    }

    fun saveName(value: String) {
        sharedPreferences?.edit(commit = true){
            putString(SPACESHIP_NAME,value)
        }
    }


    fun getUgs() = sharedPreferences?.getInt(SPACESHIP_UGS,1)
    fun getEus() = sharedPreferences?.getInt(SPACESHIP_EUS,1)
    fun getDs() = sharedPreferences?.getInt(SPACESHIP_DS,1)
    fun getName() = sharedPreferences?.getString(SPACESHIP_NAME,"")


}