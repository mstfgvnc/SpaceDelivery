package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.util.CustomSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpaceShipCreateViewModel
    @Inject constructor
        (application: Application):BaseViewModel(application) {

    private var customPreferences = CustomSharedPreferences(getApplication())
    val ugsValue = MutableLiveData<Int>()
    val eusValue =  MutableLiveData<Int>()
    val dsValue =  MutableLiveData<Int>()
    val sub =  MutableLiveData<Int>()
    val shipName =  MutableLiveData<String>()


    fun refresh(){
            ugsValue.value=customPreferences.getUgs()
            eusValue.value=customPreferences.getEus()
            dsValue.value=customPreferences.getDs()
            shipName.value=customPreferences.getName()
            sub.value=ugsValue.value!!+eusValue.value!!+dsValue.value!!

    }

}