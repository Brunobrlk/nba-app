package com.example.nbaapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.nbaapp.data.local.database.AppDatabase
import com.example.nbaapp.core.helpers.Constants
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration(true).build()

    @Provides
    fun provideTeamsDao(db: AppDatabase) = db.getTeamsDao()

    @Provides
    fun providePlayersDao(db: AppDatabase) = db.getPlayersDao()

    @Provides
    fun provideRemoteKeysDao(db: AppDatabase) = db.getRemoteKeysDao()
}