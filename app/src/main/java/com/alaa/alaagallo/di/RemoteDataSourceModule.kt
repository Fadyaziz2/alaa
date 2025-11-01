package com.alaa.alaagallo.di

import com.alaa.data.source.IRemoteDataSource
import com.alaa.data.source.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(remoteDataSource: RemoteDataSource): IRemoteDataSource
}