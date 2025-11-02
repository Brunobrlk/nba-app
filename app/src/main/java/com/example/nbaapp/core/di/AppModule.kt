package com.example.nbaapp.core.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.nbaapp.core.workmanager.WorkScheduler
import com.example.nbaapp.data.local.LocalPlayersDataSource
import com.example.nbaapp.data.local.LocalRemoteKeysDataSource
import com.example.nbaapp.data.local.database.entities.PlayerEntity
import com.example.nbaapp.data.remote.RemotePlayersDataSource
import com.example.nbaapp.core.utils.Constants
import com.example.nbaapp.data.mediators.PlayersRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun providePlayersRemoteMediator(
        remotePlayers: RemotePlayersDataSource,
        localPlayers: LocalPlayersDataSource,
        localRemoteKeys: LocalRemoteKeysDataSource
    ): PlayersRemoteMediator = PlayersRemoteMediator(remotePlayers, localPlayers, localRemoteKeys)

    @OptIn(ExperimentalPagingApi::class)
    @Singleton
    @Provides
    fun providePlayersPager(
        localPlayers: LocalPlayersDataSource, playersRemoteMediator: PlayersRemoteMediator
    ): Pager<Int, PlayerEntity> = Pager(
        config = PagingConfig(pageSize = Constants.PLAYERS_PAGE_SIZE),
        remoteMediator = playersRemoteMediator,
        pagingSourceFactory = { localPlayers.playersPagingSource() }
    )

    @Singleton
    @Provides
    fun provideWorkManagerController(@ApplicationContext context: Context) = WorkScheduler(context)
}