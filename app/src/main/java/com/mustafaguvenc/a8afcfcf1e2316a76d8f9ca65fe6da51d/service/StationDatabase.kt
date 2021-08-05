package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel


@Database(entities = arrayOf(StationModel::class),version = 1,exportSchema = false)
abstract class StationDatabase : RoomDatabase(){

    abstract fun stationDao (): StationDao

    companion object{
        @Volatile private var instance : StationDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance?: makeDatabese(context).also {
                instance=it
            }
        }

        private fun makeDatabese(context : Context) = Room.databaseBuilder(context.applicationContext
            ,StationDatabase::class.java,"stationdatabase").build()

    }
}