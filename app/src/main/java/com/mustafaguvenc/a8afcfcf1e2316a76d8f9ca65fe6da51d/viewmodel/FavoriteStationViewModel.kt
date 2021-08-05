package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.service.StationDatabase
import kotlinx.coroutines.launch

class   FavoriteStationViewModel(application : Application): BaseViewModel(application){

    val favoriteStations = MutableLiveData<List<StationModel>>()

    fun refreshData(){
        getDataFromSQLite()
    }

    private fun getDataFromSQLite(){
        launch {

            val stations = StationDatabase(getApplication()).stationDao().getAllStations()
            val listFavorite = ArrayList<StationModel>()
            for(i in stations){
                if(i.favoriteBool){
                    listFavorite.add(i)
                }
            }
            favoriteStations.value=listFavorite
        }
    }

}


