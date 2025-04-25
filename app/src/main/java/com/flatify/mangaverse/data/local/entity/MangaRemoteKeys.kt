package com.flatify.mangaverse.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class MangaRemoteKeys(
    @PrimaryKey val mangaId: String,
    val prevKey: Int?,
    val nextKey: Int?
)