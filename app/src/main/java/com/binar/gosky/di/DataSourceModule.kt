package com.binar.gosky.di

import com.binar.gosky.data.network.datasource.TicketsRemoteDataSource
import com.binar.gosky.data.network.datasource.TicketsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideTicketsRemoteDataSource(ticketsRemoteDataSourceImpl: TicketsRemoteDataSourceImpl): TicketsRemoteDataSource
}