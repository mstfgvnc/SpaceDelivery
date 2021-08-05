package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.service

import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel
import io.reactivex.Single
import retrofit2.http.GET

interface StationApi {

  //  @GET("v3/e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    @GET("mstfgvnc/0f068722042aa5166fe4d3ed45f8211e/raw/d5cab4b4c1272da1bd9146f13ed000cbbdef8302/spacestations")
    fun getStation(): Single<List<StationModel>>
    //https://run.mocky.io/v3/e7211664-cbb6-4357-9c9d-f12bf8bab2e2
    // https://gist.githubusercontent.com/mstfgvnc/0f068722042aa5166fe4d3ed45f8211e/raw/d5cab4b4c1272da1bd9146f13ed000cbbdef8302/spacestations
}