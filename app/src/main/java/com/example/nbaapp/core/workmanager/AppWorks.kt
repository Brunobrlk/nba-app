package com.example.nbaapp.core.workmanager

sealed interface AppWorks {
    val workName: String

    enum class OneTimeWork(override val workName: String) : AppWorks {
        NOTIFICATIONS(NotificationsWorker.ONE_TIME_NAME)
    }

    enum class PeriodicWork(override val workName: String) : AppWorks {
        NOTIFICATIONS(NotificationsWorker.PERIODIC_NAME)
    }
}
