package com.example.nbaapp.core.lifecycle

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.startup.AppInitializer
import com.example.nbaapp.core.startup.HiltWorkManagerInitializer
import com.example.nbaapp.core.workmanager.WorkScheduler
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppLifecycleObserver @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val workScheduler: WorkScheduler
): DefaultLifecycleObserver {
    private var initialized = false

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        if(!initialized){
            AppInitializer.getInstance(context).initializeComponent(HiltWorkManagerInitializer::class.java)
            workScheduler.scheduleBackgroundJobs()
            initialized = true
        }
    }
}