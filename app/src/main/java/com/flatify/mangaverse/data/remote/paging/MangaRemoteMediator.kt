package com.flatify.mangaverse.data.remote.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
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
    private val db: AppDatabase
) : RemoteMediator<Int, MangaEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MangaEntity>
    ): MediatorResult {
        Log.d("PagingStateDebug", "LoadType: $loadType")
        Log.d("PagingStateDebug", "State pages size: ${state.pages.size}")
        state.pages.forEachIndexed { index, page ->
            Log.d("PagingStateDebug", "Page[$index] item count: ${page.data.size}")
        }
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }


        return try {
            val response = api.getManga(page)
            val mangaList = response.data.map { it.toEntity() }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearRemoteKeys()
                    db.mangaDao().clearAll()
                }

                val keys = mangaList.map {
                    MangaRemoteKeys(
                        mangaId = it.id,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (response.data.isEmpty()) null else page + 1
                    )
                }

                db.remoteKeysDao().insertAll(keys)
                db.mangaDao().insertAll(mangaList)
            }

            MediatorResult.Success(endOfPaginationReached = response.data.isEmpty())

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MangaEntity>): MangaRemoteKeys? {
        val lastPage = state.pages.lastOrNull { it.data.isNotEmpty() } ?: return null
        val lastItem = lastPage.data.lastOrNull() ?: return null
        return db.remoteKeysDao().getRemoteKey(lastItem.id)
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MangaEntity>): MangaRemoteKeys? {
        val firstPage = state.pages.firstOrNull { it.data.isNotEmpty() } ?: return null
        val firstItem = firstPage.data.firstOrNull() ?: return null
        return db.remoteKeysDao().getRemoteKey(firstItem.id)
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MangaEntity>): MangaRemoteKeys? {
        val anchor = state.anchorPosition ?: return null
        val closestItem = state.closestItemToPosition(anchor) ?: return null
        return db.remoteKeysDao().getRemoteKey(closestItem.id)
    }
}