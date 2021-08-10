package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.di


import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.network.StationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private  val BASE_URL = "https://gist.githubusercontent.com/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }



    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): StationApi {
        return retrofit.create(StationApi::class.java)
    }


    @Singleton
    @Provides
    fun provideStationDataSource(stationApi: StationApi) = stationApi.getStation()
}