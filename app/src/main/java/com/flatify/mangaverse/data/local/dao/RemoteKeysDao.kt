package com.flatify.mangaverse.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatify.mangaverse.data.local.entity.MangaRemoteKeys

@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM remote_keys WHERE mangaId ==:id")
    suspend fun getRemoteKey(id: String): MangaRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: MangaRemoteKeys)

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}