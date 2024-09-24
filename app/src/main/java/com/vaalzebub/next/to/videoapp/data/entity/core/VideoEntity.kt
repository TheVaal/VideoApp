package com.vaalzebub.next.to.videoapp.data.entity.core

import androidx.room.Entity

@Entity("videos")
data class VideoEntity(
    val id: String,
    val picUrl: String,
    val url: String,
    val name: String,
    val author: String,
    val authorUrl: String
)
