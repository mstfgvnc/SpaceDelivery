package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.di

import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.db.StationDao
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.network.StationApi
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: StationApi,
                          localDataSource: StationDao
    ) =
        Repository(localDataSource,remoteDataSource )
}