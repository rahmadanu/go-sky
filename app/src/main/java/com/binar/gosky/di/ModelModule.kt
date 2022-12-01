package com.binar.gosky.di

import com.binar.gosky.data.network.model.tickets.SearchTickets
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Singleton

@Module
@InstallIn(FragmentComponent::class)
class ModelModule {

    @Provides
    fun provideSearchTicket(): SearchTickets {
        return SearchTickets()
    }
}