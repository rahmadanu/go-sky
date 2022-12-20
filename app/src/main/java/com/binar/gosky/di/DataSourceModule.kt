package com.binar.gosky.di

import com.binar.gosky.data.local.datasource.UserLocalDataSource
import com.binar.gosky.data.local.datasource.UserLocalDataSourceImpl
import com.binar.gosky.data.network.datasource.*
import com.binar.gosky.data.repository.NotificationRepository
import com.binar.gosky.data.repository.NotificationRepositoryImpl
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
    abstract fun provideTransactionsRemoteDataSource(transactionsRemoteDataSourceImpl: TransactionsRemoteDataSourceImpl): TransactionsRemoteDataSource

    @Binds
    abstract fun provideAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    abstract fun provideUserLocalDataSource(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds
    abstract fun provideUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    abstract fun provideNotificationRemoteDataSource(notificationRemoteDataSourceImpl: NotificationRemoteDataSourceImpl): NotificationRemoteDataSource
}