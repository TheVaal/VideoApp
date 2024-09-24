package com.vaalzebub.next.to.videoapp.domain.model

data class VideoModel(
    val id: Int,
    val picUrl: String,
    val url: String,
    val duration: Int,
    val author: String,
    val authorUrl: String
)
