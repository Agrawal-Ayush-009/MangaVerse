package com.flatify.mangaverse.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatify.mangaverse.data.local.dao.MangaDao
import com.flatify.mangaverse.data.local.dao.RemoteKeysDao
import com.flatify.mangaverse.data.local.dao.UserDao
import com.flatify.mangaverse.data.local.entity.MangaEntity
import com.flatify.mangaverse.data.local.entity.MangaRemoteKeys
import com.flatify.mangaverse.data.local.entity.UserEntity

@Database(entities = [UserEntity::class, MangaEntity::class, MangaRemoteKeys::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun mangaDao(): MangaDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}