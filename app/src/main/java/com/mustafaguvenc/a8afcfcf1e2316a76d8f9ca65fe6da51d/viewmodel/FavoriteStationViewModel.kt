package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.service.StationDatabase
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.util.CustomSharedPreferences
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class FavoriteStationViewModel(application : Application): BaseViewModel(application){

    val favoriteStations = MutableLiveData<List<StationModel>>()
    private var customPreferences = CustomSharedPreferences(getApplication())

    fun refreshData(){

        getDataFromSQLite()

    }

    private fun getDataFromSQLite(){
        launch {
            val positions = customPreferences.getFavoritePosition()

            val stations = StationDatabase(getApplication()).stationDao().getAllStations()
            val listFavorite = ArrayList<StationModel>()

            for(i in positions){
                listFavorite.add(stations[i])

            }
            favoriteStations.value=listFavorite
        }
    }

}


