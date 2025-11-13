package com.example.nbaapp.core.di

import com.example.nbaapp.data.remote.AuthInterceptor
import com.example.nbaapp.data.remote.services.GamesService
import com.example.nbaapp.data.remote.services.PlayersService
import com.example.nbaapp.data.remote.services.TeamsService
import com.example.nbaapp.core.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(authInterceptor).addInterceptor(loggingInterceptor).build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(Constants.NBA_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).client(okHttpClient).build()

    @Provides
    fun provideTeamsService(retrofit: Retrofit): TeamsService =
        retrofit.create(TeamsService::class.java)

    @Provides
    fun providePlayersService(retrofit: Retrofit): PlayersService =
        retrofit.create(PlayersService::class.java)

    @Provides
    fun provideGamesService(retrofit: Retrofit): GamesService =
        retrofit.create(GamesService::class.java)
}