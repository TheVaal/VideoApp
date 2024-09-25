package com.vaalzebub.next.to.videoapp.data.entity.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("videos")
data class VideoEntity(
    @PrimaryKey
    @ColumnInfo(name="id")
    val id: Int,
    @ColumnInfo(name="picUrl")
    val picUrl: String,
    @ColumnInfo(name="url")
    val url: String,
    @ColumnInfo(name="duration")
    val duration: Int,
    @ColumnInfo(name="author")
    val author: String,
    @ColumnInfo(name="authorUrl")
    val authorUrl: String
)
