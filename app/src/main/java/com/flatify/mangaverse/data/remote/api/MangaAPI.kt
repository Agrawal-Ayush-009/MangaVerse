package com.flatify.mangaverse.data.remote.api

import com.flatify.mangaverse.data.remote.model.GetMangaResponse
import com.flatify.mangaverse.utils.Constants
import com.flatify.mangaverse.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
interface MangaAPI {
    @GET("/manga/fetch")
    suspend fun getManga(
        @Query("page") page: Int,
        @Header("x-rapidapi-key") apiKey: String = BuildConfig.API_KEY
    ): GetMangaResponse
}