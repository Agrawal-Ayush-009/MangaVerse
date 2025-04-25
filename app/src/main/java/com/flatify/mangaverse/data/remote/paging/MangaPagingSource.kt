package com.flatify.mangaverse.data.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.flatify.mangaverse.data.remote.api.MangaAPI
import com.flatify.mangaverse.data.remote.model.MangaData
import javax.inject.Inject

class MangaPagingSource @Inject constructor(private val api: MangaAPI): PagingSource<Int, MangaData>(){
    override fun getRefreshKey(state: PagingState<Int, MangaData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MangaData> {
        return try{
            val page = params.key ?: 1
            val response = api.getManga(page)
            val mangaList = response.data
            LoadResult.Page(
                data = mangaList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (mangaList.isEmpty()) null else page + 1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}