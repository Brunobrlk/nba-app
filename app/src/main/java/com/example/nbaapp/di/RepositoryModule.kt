package com.example.nbaapp.di

import com.example.nbaapp.data.local.LocalPlayersDataSource
import com.example.nbaapp.data.local.LocalRemoteKeysDataSource
import com.example.nbaapp.data.local.LocalTeamsDataSource
import com.example.nbaapp.data.local.RoomPlayersDataSource
import com.example.nbaapp.data.local.RoomRemoteKeysDataSource
import com.example.nbaapp.data.local.RoomTeamsDataSource
import com.example.nbaapp.data.remote.RemoteGamesDataSource
import com.example.nbaapp.data.remote.RemotePlayersDataSource
import com.example.nbaapp.data.remote.RemoteTeamsDataSource
import com.example.nbaapp.data.remote.RetrofitGamesDataSource
import com.example.nbaapp.data.remote.RetrofitPlayersDataSource
import com.example.nbaapp.data.remote.RetrofitTeamsDataSource
import com.example.nbaapp.domain.repository.Games
import com.example.nbaapp.domain.repository.GamesRepository
import com.example.nbaapp.domain.repository.Players
import com.example.nbaapp.domain.repository.PlayersRepository
import com.example.nbaapp.domain.repository.Teams
import com.example.nbaapp.domain.repository.TeamsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    // --- Teams
    @Binds abstract fun bindTeamsRepository(impl: TeamsRepository): Teams
    @Binds abstract fun bindRemoteTeamsDataSource(impl: RetrofitTeamsDataSource): RemoteTeamsDataSource
    @Binds abstract fun bindLocalTeamsDataSource(impl: RoomTeamsDataSource): LocalTeamsDataSource

    // --- Players
    @Binds abstract fun bindPlayersRepository(impl: PlayersRepository): Players
    @Binds abstract fun bindRemotePlayersDataSource(impl: RetrofitPlayersDataSource): RemotePlayersDataSource
    @Binds abstract fun bindLocalPlayersDataSource(impl: RoomPlayersDataSource): LocalPlayersDataSource

    // --- RemoteKeys
    @Binds abstract fun bindRemoteKeysDataSource(impl: RoomRemoteKeysDataSource): LocalRemoteKeysDataSource

    // --- Games
    @Binds abstract fun bindGamesRepository(impl: GamesRepository): Games
    @Binds abstract fun bindGamesRemoteDataSource(impl: RetrofitGamesDataSource): RemoteGamesDataSource
}