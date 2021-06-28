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
   // private val disposable = CompositeDisposable()

    fun refreshData(){

        getDataFromSQLite()
        /*
        val sonuc = customPreferences.getFavoritePosition()
        var yaz =""
        for(i in sonuc){
            yaz= yaz  +i. toString()
        }
        Toast.makeText(getApplication(),yaz,Toast.LENGTH_LONG).show()
        val station1 = StationModel("opl",0.2,0.9,1000,200,800)
        val station2 = StationModel("sdsa",5.2,0.9,1500,300,500)
        val station3 = StationModel("opal",0.2,3.9,2000,200,400)

        val stationList = arrayListOf<StationModel>(station1,station2,station3)

        favoriteStations.value=stationList

         */

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
            // Toast.makeText(getApplication(),"Turbines From SQLite", Toast.LENGTH_LONG).show()
        }
    }

}


