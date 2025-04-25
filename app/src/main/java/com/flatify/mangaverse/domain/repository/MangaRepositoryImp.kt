package com.flatify.mangaverse.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.RemoteMediator
import androidx.paging.map
import com.flatify.mangaverse.data.local.dao.MangaDao
import com.flatify.mangaverse.data.local.dao.RemoteKeysDao
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
    private val remoteKeysDao: RemoteKeysDao,
) : MangaRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getManga(): Flow<PagingData<MangaEntity>> = Pager(
        config = PagingConfig(
            pageSize = 25,
            enablePlaceholders = false,
            prefetchDistance = 1
        ),
        remoteMediator = MangaRemoteMediator(
            mangaDao = mangaDao,
            remoteKeysDao = remoteKeysDao,
            api = api
        ),
        pagingSourceFactory = {mangaDao.getMangas()}
    ).flow

}