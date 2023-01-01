package com.binar.gosky.di

import com.binar.gosky.data.local.datasource.TicketsLocalDataSource
import com.binar.gosky.data.local.datasource.TicketsLocalDataSourceImpl
import com.binar.gosky.data.local.datasource.UserLocalDataSource
import com.binar.gosky.data.local.datasource.UserLocalDataSourceImpl
import com.binar.gosky.data.network.datasource.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideTicketsRemoteDataSource(ticketsRemoteDataSourceImpl: TicketsRemoteDataSourceImpl): TicketsRemoteDataSource

    @Binds
    abstract fun provideTicketsLocalDataSource(ticketsLocalDataSourceImpl: TicketsLocalDataSourceImpl): TicketsLocalDataSource

    @Binds
    abstract fun provideTransactionsRemoteDataSource(transactionsRemoteDataSourceImpl: TransactionsRemoteDataSourceImpl): TransactionsRemoteDataSource

    @Binds
    abstract fun provideAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    abstract fun provideUserLocalDataSource(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds
    abstract fun provideUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    abstract fun provideNotificationRemoteDataSource(notificationRemoteDataSourceImpl: NotificationRemoteDataSourceImpl): NotificationRemoteDataSource

    @Binds
    abstract fun provideImageRemoteDataSource(imageRemoteDataSourceImpl: ImageRemoteDataSourceImpl): ImageRemoteDataSource
}