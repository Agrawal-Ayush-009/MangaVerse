package com.flatify.mangaverse.data.remote.repository

import androidx.paging.PagingData
import com.flatify.mangaverse.data.local.entity.MangaEntity
import com.flatify.mangaverse.data.remote.api.MangaAPI
import com.flatify.mangaverse.data.remote.model.MangaData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MangaRepository  {
    fun getManga(): Flow<PagingData<MangaData>>
    suspend fun cacheManga()
    suspend fun getCachedManga(): List<MangaData>
}