package com.example.nbaapp.core.di

import com.example.nbaapp.core.lifecycle.AppLifecycleObserver
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppLifecycleEntryPoint {
    fun appLifecycleObserver(): AppLifecycleObserver
}