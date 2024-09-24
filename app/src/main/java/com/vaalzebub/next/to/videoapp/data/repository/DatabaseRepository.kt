package com.vaalzebub.next.to.videoapp.data.repository

import com.vaalzebub.next.to.videoapp.data.entity.core.VideoEntity
import com.vaalzebub.next.to.videoapp.data.entity.dao.VideoDao
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val dao: VideoDao) {

    fun getVideos(): Flow<List<VideoEntity>> {
        return dao.getVideos()
    }

    suspend fun upsertAll(lounges: List<VideoEntity>) {
        dao.upsertAllVideos(lounges)
    }
    fun getVideo(id: Int): Flow<VideoEntity> {
        return dao.getVideoById(id = id)
    }
}