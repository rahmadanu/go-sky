package com.binar.gosky.di

import android.content.Context
import com.binar.gosky.data.local.database.AppDatabase
import com.binar.gosky.data.local.database.tickets.TicketsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context.applicationContext)
    }

    @Provides
    @Singleton
    fun provideTicketsDao(appDatabase: AppDatabase): TicketsDao {
        return appDatabase.ticketsDao()
    }
}