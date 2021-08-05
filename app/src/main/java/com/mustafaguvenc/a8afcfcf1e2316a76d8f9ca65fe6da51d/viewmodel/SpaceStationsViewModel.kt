package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.service.StationApiService
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.service.StationDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SpaceStationsViewModel

    (application : Application): BaseViewModel(application) {

    val stations = MutableLiveData<List<StationModel>>()
    private val stationApiService = StationApiService()
    private val disposable = CompositeDisposable()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()
    val ugsValue = MutableLiveData<Int>()
    val eusValue =  MutableLiveData<Double>()
    val dsValue =  MutableLiveData<Int>()
    val damage =  MutableLiveData(100)
    val currentStation =  MutableLiveData("Dünya")
    val shipName =  MutableLiveData<String>()
    val currentDamageTime = MutableLiveData<Int>()
    lateinit var timer : CountDownTimer

    fun refreshData(){

           getDataFromSQLite()

    }
     private fun getDataFromSQLite(){
        launch {
            val stations = StationDatabase(getApplication()).stationDao().getAllStations()
            if(stations.size==0){
                getDataFromAPI()
            }else{
                showView(stations)
            }

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

         timer = object: CountDownTimer((dsValue.value!!*10).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {

                    if(currentDamageTime.value==0){
                        currentDamageTime.value= dsValue.value?.div(1000)?.minus(1)
                        damage.value = damage.value!! -10
                    }else{
                        currentDamageTime.value = currentDamageTime.value?.minus(1)
                    }

            }
            override fun onFinish() {
                    damage.value = 0
            }
        }
        timer.start()

    }

}