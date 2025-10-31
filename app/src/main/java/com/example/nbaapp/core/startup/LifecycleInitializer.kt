package com.example.nbaapp.core.startup

import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.startup.Initializer
import com.example.nbaapp.core.di.AppLifecycleEntryPoint
import dagger.hilt.android.EntryPointAccessors

class LifecycleInitializer: Initializer<Unit> {
    override fun create(context: Context) {
        val appContext = context.applicationContext
        val appLifecycleObserver = EntryPointAccessors.fromApplication(
            appContext,
            AppLifecycleEntryPoint::class.java
        ).appLifecycleObserver()
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> = emptyList()
}