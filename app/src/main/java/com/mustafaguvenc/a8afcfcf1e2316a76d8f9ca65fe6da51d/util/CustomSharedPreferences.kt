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
        private val COORDINATE_X = "coordinate_x"
        private val COORDINATE_Y = "coordinate_y"
        private val FAVORITE_KEY = "favorite_key"
        private val MILLES_UNTIL_FINESHED = "millis_until_finished"
        private val DAMAGE_VALUE = "damage_value"
        private val CURRENT_DAMAGE_TIME = "current_damage_time"
        private val CURRENT_STATION_NAME = "current_station_name"
        private val VISITED_POSITION= "visited_position"
        private val FROM_MAIN= "from_main"





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

    fun saveFavoritePosition(value: HashSet<Int>) {
        sharedPreferences?.edit(commit = true){

            val favoriteStrinSet= hashSetOf<String>()
            for(i in value){
                favoriteStrinSet.add(i.toString())
            }
            putStringSet(FAVORITE_KEY,favoriteStrinSet)
        }
    }

    fun saveVisitedPosition(value: HashSet<Int>) {
        sharedPreferences?.edit(commit = true){

            val visitStrinSet= hashSetOf<String>()
            for(i in value){
                visitStrinSet.add(i.toString())
            }
            putStringSet(VISITED_POSITION,visitStrinSet)
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
    fun saveCurrentCoordinateX(value: Double) {
        sharedPreferences?.edit(commit = true){
            putFloat(COORDINATE_X,value.toFloat())
        }
    }
    fun saveCurrentCoordinateY(value: Double) {
        sharedPreferences?.edit(commit = true){
            putFloat(COORDINATE_Y,value.toFloat())
        }
    }

    fun saveMillisUntilFinished(value: Long) {
        sharedPreferences?.edit(commit = true){
            putLong(MILLES_UNTIL_FINESHED,value)
        }
    }
    fun saveDamageValue(value: Int) {
        sharedPreferences?.edit(commit = true){
            putInt(DAMAGE_VALUE,value)
        }
    }
    fun saveCurrentDamageTime(value: Int) {
        sharedPreferences?.edit(commit = true){
            putInt(CURRENT_DAMAGE_TIME,value)
        }
    }

    fun saveCurrentStationName(value: String) {
        sharedPreferences?.edit(commit = true){
            putString(CURRENT_STATION_NAME,value)
        }
    }

    fun saveFromMain(value: Boolean) {
        sharedPreferences?.edit(commit = true){
            putBoolean(FROM_MAIN,value)
        }
    }
    fun getFromMain() = sharedPreferences?.getBoolean(FROM_MAIN,true)


    fun getCurrentDamageTime() = sharedPreferences?.getInt(CURRENT_DAMAGE_TIME,0)
    fun geCurrentStationName() = sharedPreferences?.getString(CURRENT_STATION_NAME,"")
    fun getUgs() = sharedPreferences?.getInt(SPACESHIP_UGS,0)
    fun getEus() = sharedPreferences?.getInt(SPACESHIP_EUS,0)
    fun getDs() = sharedPreferences?.getInt(SPACESHIP_DS,0)
    fun getDamageValue() = sharedPreferences?.getInt(DAMAGE_VALUE,0)
    fun getCurrentCoordinateX() = sharedPreferences?.getFloat(COORDINATE_X,0f)
    fun getCurrentCoordinateY() = sharedPreferences?.getFloat(COORDINATE_Y,0f)
    fun getName() = sharedPreferences?.getString(SPACESHIP_NAME,"")
    fun getMillisUntilFinished() = sharedPreferences?.getLong(MILLES_UNTIL_FINESHED,0)


    fun getFavoritePosition():HashSet<Int> {
        val favoriteIntSet= hashSetOf<Int>()

        val fvStringSet = sharedPreferences?.getStringSet(FAVORITE_KEY,null)

        if (fvStringSet != null) {
            for(i in fvStringSet){
                favoriteIntSet.add(i.toInt())
            }
        }
        return favoriteIntSet

    }

    fun getVisitedPosition():HashSet<Int> {
        val visitIntSet= hashSetOf<Int>()

        val visitStringSet = sharedPreferences?.getStringSet(VISITED_POSITION,null)

        if (visitStringSet != null) {
            for(i in visitStringSet){
                visitIntSet.add(i.toInt())
            }
        }
        return visitIntSet

    }




}