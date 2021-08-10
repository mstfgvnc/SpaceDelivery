package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.db.StationDatabase
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteStationViewModel
    @Inject constructor
    (private val repository: Repository, application : Application): BaseViewModel(application){

    val favoriteStations = MutableLiveData<List<StationModel>>()

    fun refreshData(){
        getDataFromSQLite()
    }

    private fun getDataFromSQLite(){
        launch {

            val stations = repository.getAllStations()
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


