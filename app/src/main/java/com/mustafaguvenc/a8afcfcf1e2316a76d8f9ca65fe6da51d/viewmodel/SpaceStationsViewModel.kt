package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel

import android.app.Application
import android.content.Intent
import android.os.CountDownTimer
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.service.StationApiService
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.service.StationDatabase
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.util.CustomSharedPreferences
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class SpaceStationsViewModel (application : Application): BaseViewModel(application) {
    val stations = MutableLiveData<List<StationModel>>()
    private val stationApiService = StationApiService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()

    val ugsValue = MutableLiveData<Int>()
    val eusValue =  MutableLiveData<Double>()
    val dsValue =  MutableLiveData<Int>()
    val damage =  MutableLiveData<Int>()
    val currentStation =  MutableLiveData<String>()
    val currentDamageTime = MutableLiveData<Int>()
    lateinit var timer : CountDownTimer

    val shipName = "Uzay Aracı : "+customPreferences.getName()

    fun refreshData(){
          damage.value=customPreferences.getDamageValue()
          ugsValue.value = customPreferences.getRemainUgs()
         eusValue.value = customPreferences.getRemainEus()!!.toDouble()
          dsValue.value = customPreferences.getRemainDs()
          currentDamageTime.value=customPreferences.getCurrentDamageTime()
          currentStation.value=customPreferences.geCurrentStationName()

           downTimer()
            if(customPreferences.getFromMain()!!){
                getDataFromAPI()
            }else{
                getDataFromSQLite()
            }


    }
     private fun getDataFromSQLite(){
        launch {
            val stations = StationDatabase(getApplication()).stationDao().getAllStations()
            showView(stations)
        }
    }
    private fun getDataFromAPI() {
        loading.value=true

        disposable.add(
            stationApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<StationModel>>() {
                    override fun onSuccess(t : List<StationModel>) {
                        storeInSQLite(t)
                    }

                    override fun onError(e : Throwable) {
                        loading.value=false
                        error.value=true
                        e.printStackTrace()

                    }


                })
        )
    }

    private fun storeInSQLite (list : List<StationModel>){
        launch {
            val dao = StationDatabase(getApplication()).stationDao()
            dao.deleteAllStations()
            val listLong =  dao.insertAll(*list.toTypedArray())
            var i = 0
            while(i<list.size){
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }
            showView(list)
        }

    }
    private fun showView(turbinList : List<StationModel>){

        stations.value= turbinList
        loading.value=false
        error.value=false


    }


    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }

    fun downTimer(){
        val milles = customPreferences.getMillisUntilFinished()

         timer = object: CountDownTimer(milles!!, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                    if(currentDamageTime.value==0){
                        currentDamageTime.value= dsValue.value?.div(1000)?.minus(1)
                        damage.value = damage.value!! -10
                        customPreferences.saveDamageValue(damage.value!!)
                    }else{
                        currentDamageTime.value = currentDamageTime.value?.minus(1)
                    }
                customPreferences.saveMillisUntilFinished(millisUntilFinished)
                customPreferences.saveCurrentDamageTime(currentDamageTime.value!!)

            }
            override fun onFinish() {
                    damage.value = 0
            }
        }
        timer.start()

    }

}