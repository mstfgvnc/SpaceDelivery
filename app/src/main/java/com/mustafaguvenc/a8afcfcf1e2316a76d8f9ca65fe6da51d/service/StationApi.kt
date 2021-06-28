package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.service

import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel
import io.reactivex.Single
import retrofit2.http.GET

interface StationApi {

    @GET("v3/e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    fun getStation(): Single<List<StationModel>>
//https://run.mocky.io/v3/e7211664-cbb6-4357-9c9d-f12bf8bab2e2
}