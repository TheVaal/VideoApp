package com.vaalzebub.next.to.videoapp.domain.repository

import com.vaalzebub.next.to.videoapp.data.entity.core.VideoEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseDetailRepository {
    fun getVideo(id: Int): Flow<VideoEntity>
    fun getPlayOrder(): Flow<List<Int>>
}