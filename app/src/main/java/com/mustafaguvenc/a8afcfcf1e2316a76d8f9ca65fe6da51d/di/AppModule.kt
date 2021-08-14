package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.di

import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.db.StationDao
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.network.StationApi
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.repository.Repository
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view.FavoriteStations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideList() : ArrayList<StationModel> = ArrayList<StationModel>()


}