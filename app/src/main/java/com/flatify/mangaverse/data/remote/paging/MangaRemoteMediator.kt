package com.flatify.mangaverse.data.remote.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.flatify.mangaverse.data.local.dao.MangaDao
import com.flatify.mangaverse.data.local.dao.RemoteKeysDao
import com.flatify.mangaverse.data.local.database.AppDatabase
import com.flatify.mangaverse.data.local.entity.MangaEntity
import com.flatify.mangaverse.data.local.entity.MangaRemoteKeys
import com.flatify.mangaverse.data.remote.api.MangaAPI
import com.flatify.mangaverse.data.remote.model.MangaData
import com.flatify.mangaverse.data.remote.model.toEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MangaRemoteMediator @Inject constructor(
    private val api: MangaAPI,
    private val remoteKeysDao: RemoteKeysDao,
    private val mangaDao: MangaDao
) : RemoteMediator<Int, MangaEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MangaEntity>
    ): MediatorResult {
        val loadKey = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getClosestPosition(state)
                val nextKey = remoteKey?.nextKey?.minus(1) ?: 1
                nextKey
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.APPEND -> {
                val remoteKey = getLastPosition(state)
                val nextKey = remoteKey?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
                nextKey
            }
        }

        val response = api.getManga(loadKey)
        val endOfPaginationReached = response.data.isEmpty()

        if(loadType == LoadType.REFRESH){
            mangaDao.clearAll()
            remoteKeysDao.clearRemoteKeys()
        }

        val prevKey = if (loadKey == 1) null else loadKey - 1
        val nextKey = if (endOfPaginationReached) null else loadKey + 1

        response.data.map {
            remoteKeysDao.insertKey(
                MangaRemoteKeys(
                    mangaId = it.id,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            )
        }
        mangaDao.insertAll(response.data.map { it.toEntity() })

        return MediatorResult.Success(endOfPaginationReached)
    }

    suspend fun getClosestPosition(state: PagingState<Int, MangaEntity>): MangaRemoteKeys? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let {
                remoteKeysDao.getRemoteKey(it.id)
            }
        }
    }

    suspend fun getLastPosition(state: PagingState<Int, MangaEntity>): MangaRemoteKeys? {
        return state.lastItemOrNull()?.let {
            remoteKeysDao.getRemoteKey(it.id)
        }
    }
}