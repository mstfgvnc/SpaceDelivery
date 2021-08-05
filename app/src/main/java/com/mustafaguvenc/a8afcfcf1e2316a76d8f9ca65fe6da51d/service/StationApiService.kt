package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.service

import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class StationApiService {
 //   private  val BASE_URL = "https://run.mocky.io/"
    private  val BASE_URL = "https://gist.githubusercontent.com/"

    private  val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(StationApi::class.java)


    fun getData() : Single<List<StationModel>> {
        return api.getStation()
    }

}