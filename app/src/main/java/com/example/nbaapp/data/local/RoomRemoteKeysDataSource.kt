package com.example.nbaapp.data.local

import com.example.nbaapp.data.local.database.daos.RemoteKeysDao
import com.example.nbaapp.data.local.database.entities.RemoteKeyEntity
import javax.inject.Inject

class RoomRemoteKeysDataSource @Inject constructor(
    private val remoteKeysDao: RemoteKeysDao
) : LocalRemoteKeysDataSource {
    override suspend fun get(label: String) = remoteKeysDao.get(label)
    override suspend fun insert(remoteKey: RemoteKeyEntity) = remoteKeysDao.insert(remoteKey)
    override suspend fun delete(label: String) = remoteKeysDao.delete(label)
}
