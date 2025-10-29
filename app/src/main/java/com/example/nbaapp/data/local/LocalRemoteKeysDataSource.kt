package com.example.nbaapp.data.local

import com.example.nbaapp.data.local.database.entities.RemoteKeyEntity

interface LocalRemoteKeysDataSource {
    suspend fun get(label: String): RemoteKeyEntity
    suspend fun insert(remoteKey: RemoteKeyEntity)
    suspend fun delete(label: String)
}
