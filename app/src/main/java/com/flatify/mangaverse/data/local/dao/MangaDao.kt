package com.flatify.mangaverse.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatify.mangaverse.data.local.entity.MangaEntity

@Dao
interface MangaDao {
    @Query("SELECT * FROM manga")
    fun getMangas(): List<MangaEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MangaEntity>)

    @Query("DELETE FROM manga")
    suspend fun clearAll()
}