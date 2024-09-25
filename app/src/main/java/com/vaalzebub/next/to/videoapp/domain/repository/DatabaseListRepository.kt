package com.vaalzebub.next.to.videoapp.domain.repository

import com.vaalzebub.next.to.videoapp.data.entity.core.VideoEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseListRepository {
    fun getVideos(): Flow<List<VideoEntity>>
    suspend fun upsertAll(lounges: List<VideoEntity>)
}