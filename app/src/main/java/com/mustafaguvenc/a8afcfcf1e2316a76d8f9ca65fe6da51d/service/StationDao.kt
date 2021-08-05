package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.service

import androidx.room.*
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel


@Dao
interface StationDao {

    @Insert
    suspend fun insertAll(vararg station : StationModel) : List<Long>

    @Query("SELECT * FROM stationmodel")
    suspend fun getAllStations():List<StationModel>

    @Query("SELECT * FROM stationmodel WHERE uuid = :stationId")
    suspend fun getStation(stationId :  Int):StationModel

    @Query("DELETE FROM stationmodel")
    suspend fun deleteAllStations()

    @Update
    fun update(stationData: StationModel)





}