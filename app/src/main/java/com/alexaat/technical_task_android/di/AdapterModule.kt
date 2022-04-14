package com.alexaat.technical_task_android.di

import com.alexaat.technical_task_android.adapters.UsersAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {
    @Singleton
    @Provides
    fun provideAdapter(): UsersAdapter {
        return UsersAdapter()
    }
}