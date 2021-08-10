package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.di

import android.content.Context
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.db.StationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = StationDatabase.invoke(appContext)

    @Singleton
    @Provides
    fun provideStationDao(db: StationDatabase) = db.stationDao()



}