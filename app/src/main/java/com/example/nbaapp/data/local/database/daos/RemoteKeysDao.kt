package com.example.nbaapp.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nbaapp.data.local.database.entities.RemoteKeyEntity

@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM remote_keys WHERE label = :label")
    suspend fun get(label: String): RemoteKeyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKey: RemoteKeyEntity)

    @Query("DELETE FROM remote_keys WHERE label = :label")
    suspend fun delete(label: String)
}