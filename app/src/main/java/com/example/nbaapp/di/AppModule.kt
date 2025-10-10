package com.example.nbaapp.di

import android.content.Context
import androidx.room.Room
import com.example.nbaapp.data.local.database.AppDatabase
import com.example.nbaapp.data.remote.AuthInterceptor
import com.example.nbaapp.data.remote.services.GamesService
import com.example.nbaapp.data.remote.services.PlayersService
import com.example.nbaapp.data.remote.services.TeamsService
import com.example.nbaapp.domain.helpers.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    // =========================== Room Injections ===========================
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "nba_database").build()
    }

    @Singleton
    @Provides
    fun provideTeamsDao(db: AppDatabase) = db.getTeamDao()

    @Singleton
    @Provides
    fun providePlayersDao(db: AppDatabase) = db.getPlayerDao()

    // =========================== Retrofit Injections ===========================
    @Singleton
    @Provides
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.NBA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesTeamsService(retrofit: Retrofit): TeamsService {
        return retrofit.create(TeamsService::class.java)
    }

    @Singleton
    @Provides
    fun providesPlayersService(retrofit: Retrofit): PlayersService {
        return retrofit.create(PlayersService::class.java)
    }

    @Singleton
    @Provides
    fun providesGamesService(retrofit: Retrofit): GamesService {
        return retrofit.create(GamesService::class.java)
    }

}