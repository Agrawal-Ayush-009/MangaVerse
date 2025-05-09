package com.flatify.mangaverse.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga")
data class MangaEntity(
    @PrimaryKey (autoGenerate = false)
    val id: String,
    val title: String,
    val subTitle: String,
    val summary: String,
    val thumb: String,
    val type: String,
    val status: String,
    val nsfw: Boolean,
    val totalChapter: Int,
    val createAt: Long,
    val updateAt: Long
)