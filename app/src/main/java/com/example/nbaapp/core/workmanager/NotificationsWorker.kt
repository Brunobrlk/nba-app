package com.example.nbaapp.core.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.nbaapp.core.utils.DebugUtils
import com.example.nbaapp.core.utils.onFailure
import com.example.nbaapp.core.utils.onSuccess
import com.example.nbaapp.domain.repository.GamesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class NotificationsWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val gamesRepository: GamesRepository
) : CoroutineWorker(appContext, params) {
    init {
        DebugUtils.reportDebug("NotificationsWorker constructed via Hilt")
    }

    override suspend fun doWork(): Result {
        DebugUtils.reportDebug("Hello World from NotificationsWorker")
        gamesRepository.getAll(2).onSuccess {
            DebugUtils.reportDebug("Games: $it")
        }.onFailure {
            return Result.failure()
        }
        return Result.success()
    }

    companion object {
        const val WORK_TAG = "NotificationsWorker"
        const val PERIODIC_NAME = "PeriodicNotificationsWorker"
        const val ONE_TIME_NAME = "OneTimeNotificationsWorker"

        fun buildPeriodicWork(): PeriodicWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(false)
                .build()

            return PeriodicWorkRequestBuilder<NotificationsWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .addTag(WORK_TAG)
                .build()
        }

        fun buildOneTimeWork(): OneTimeWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiresCharging(false)
                .build()

            return OneTimeWorkRequestBuilder<NotificationsWorker>()
                .setConstraints(constraints)
                .addTag(WORK_TAG)
                .build()
        }
    }
}