package com.flatify.mangaverse.data.remote.model

import com.flatify.mangaverse.data.local.entity.MangaEntity

data class GetMangaResponse(
    val code: Int,
    val `data`: List<MangaData>
)

data class MangaData(
    val create_at: Long,
    val id: String,
    val nsfw: Boolean,
    val status: String,
    val sub_title: String,
    val summary: String,
    val thumb: String,
    val title: String,
    val total_chapter: Int,
    val type: String,
    val update_at: Long
)

fun MangaData.toEntity() : MangaEntity = MangaEntity(id, title, sub_title, summary, thumb, type, status, nsfw,total_chapter, create_at, update_at)
fun MangaEntity.toDomain(): MangaData = MangaData(createAt,id, nsfw, status, subTitle, summary, thumb, title, totalChapter, type, updateAt)