package com.example.nbaapp.core.workmanager

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/*
    Types of work: One-time, Periodic
    OneTime: Immediate(May be Expedited), Long Running, Deferrable
    Periodic: Long Running, Deferrable
*/

@Singleton
class WorkScheduler @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    private val manager by lazy { WorkManager.getInstance(context) }

    private fun scheduleOneTimeWork(appWork: AppWorks.OneTimeWork): UUID {
        val workRequest = when (appWork) {
            AppWorks.OneTimeWork.NOTIFICATIONS -> NotificationsWorker.buildOneTimeWork()
        }
        manager.enqueueUniqueWork(appWork.workName, ExistingWorkPolicy.REPLACE, workRequest)
        return workRequest.id
    }

    private fun schedulePeriodicWork(appWork: AppWorks.PeriodicWork): UUID {
        val workRequest = when (appWork) {
            AppWorks.PeriodicWork.NOTIFICATIONS -> NotificationsWorker.buildPeriodicWork()
        }
        manager.enqueueUniquePeriodicWork(appWork.workName, ExistingPeriodicWorkPolicy.REPLACE, workRequest)

        return workRequest.id
    }

    fun scheduleWork(appWork: AppWorks) = when (appWork) {
        is AppWorks.OneTimeWork -> scheduleOneTimeWork(appWork)
        is AppWorks.PeriodicWork -> schedulePeriodicWork(appWork)
    }
    fun scheduleBackgroundJobs() {
        scheduleWork(AppWorks.OneTimeWork.NOTIFICATIONS)
    }

    fun cancelWork(appWork: AppWorks) = manager.cancelUniqueWork(appWork.workName)
    fun cancelAllWork() = manager.cancelAllWork()
}