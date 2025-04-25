package com.flatify.mangaverse.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.flatify.mangaverse.data.local.dao.MangaDao
import com.flatify.mangaverse.data.local.database.AppDatabase
import com.flatify.mangaverse.data.local.entity.MangaEntity
import com.flatify.mangaverse.data.remote.api.MangaAPI
import com.flatify.mangaverse.data.remote.model.MangaData
import com.flatify.mangaverse.data.remote.model.toDomain
import com.flatify.mangaverse.data.remote.model.toEntity
import com.flatify.mangaverse.data.remote.paging.MangaPagingSource
import com.flatify.mangaverse.data.remote.paging.MangaRemoteMediator
import com.flatify.mangaverse.data.remote.repository.MangaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MangaRepositoryImp @Inject constructor(
    private val api: MangaAPI,
    private val mangaDao: MangaDao,
    private val db: AppDatabase
) : MangaRepository {

    override fun getManga(): Flow<PagingData<MangaData>> = Pager(
        config = PagingConfig(
            pageSize = 25,
            enablePlaceholders = false,
            prefetchDistance = 1
        ),
        pagingSourceFactory = { MangaPagingSource(api) }
    ).flow

    override suspend fun cacheManga() {
        val mangaFlow = getManga()
        val mangaEntityList = mutableListOf<MangaEntity>()
        mangaFlow.map { pagingData ->
            pagingData.map { mangaData ->
                val mangaEntity = mangaData.toEntity()
                mangaEntityList.add(mangaEntity)
            }
        }
        db.mangaDao().insertAll(mangaEntityList)
    }

    override suspend fun getCachedManga(): List<MangaData> {
        return db.mangaDao().getMangas().map { it.toDomain() }
    }


}