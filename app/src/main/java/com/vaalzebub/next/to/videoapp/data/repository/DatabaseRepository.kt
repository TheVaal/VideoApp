package com.vaalzebub.next.to.videoapp.data.repository

import com.vaalzebub.next.to.videoapp.data.entity.core.VideoEntity
import com.vaalzebub.next.to.videoapp.data.entity.dao.VideoDao
import com.vaalzebub.next.to.videoapp.domain.repository.DatabaseDetailRepository
import com.vaalzebub.next.to.videoapp.domain.repository.DatabaseListRepository
import kotlinx.coroutines.flow.Flow

class DatabaseRepositoryImpl(private val dao: VideoDao) : DatabaseDetailRepository,
    DatabaseListRepository {

    override fun getVideos(): Flow<List<VideoEntity>> {
        return dao.getVideos()
    }

    override suspend fun upsertAll(lounges: List<VideoEntity>) {
        dao.upsertAllVideos(lounges)
    }

    override fun getVideo(id: Int): Flow<VideoEntity> {
        return dao.getVideoById(id = id)
    }

    override fun getPlayOrder(): Flow<List<Int>> {
        return dao.getPlayOrder()
    }
}