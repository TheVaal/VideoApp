package com.vaalzebub.next.to.videoapp.data.repository

import com.vaalzebub.next.to.videoapp.api.VideoApi
import com.vaalzebub.next.to.videoapp.data.entity.core.VideoEntity

class ApiRepository(private val api: VideoApi) {

    fun getVideos():List<VideoEntity>{
        return listOf()
    }
}