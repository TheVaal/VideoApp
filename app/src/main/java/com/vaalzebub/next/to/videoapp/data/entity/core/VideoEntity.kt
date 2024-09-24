package com.vaalzebub.next.to.videoapp.data.entity.core

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("videos")
data class VideoEntity(
    @PrimaryKey
    val id: Int,
    val picUrl: String,
    val url: String,
    val duration: Int,
    val author: String,
    val authorUrl: String
)
