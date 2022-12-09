package com.binar.gosky.di

import com.binar.gosky.data.repository.AuthRepository
import com.binar.gosky.data.repository.AuthRepositoryImpl
import com.binar.gosky.data.repository.TicketsRepository
import com.binar.gosky.data.repository.TicketsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideTicketsRepository(ticketsRepositoryImpl: TicketsRepositoryImpl): TicketsRepository

    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}