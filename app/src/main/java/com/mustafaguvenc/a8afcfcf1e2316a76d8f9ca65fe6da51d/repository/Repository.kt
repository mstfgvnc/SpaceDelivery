package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.repository

import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.db.StationDao
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.network.StationApi
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton



class Repository @Inject constructor(private val appDao : StationDao,
                                         private val appApi : StationApi) {



    suspend fun getStations(): Single<List<StationModel>> {
        return appApi.getStation()
    }


    suspend fun insertAll(vararg station : StationModel):List<Long>{
        return appDao.insertAll(*station)
    }

    suspend fun getAllStations():List<StationModel>{
        return appDao.getAllStations()
    }
    suspend fun getStation(stationId :  Int):StationModel{
        return appDao.getStation(stationId)
    }

    suspend fun deleteAllStations(){
        return appDao.deleteAllStations()
    }
    suspend fun update(stationData: StationModel){
        return appDao.update(stationData)
    }


}