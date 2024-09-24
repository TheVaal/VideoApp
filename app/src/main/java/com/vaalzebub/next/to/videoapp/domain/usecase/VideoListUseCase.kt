package com.vaalzebub.next.to.videoapp.domain.usecase

import com.vaalzebub.next.to.videoapp.data.entity.core.VideoEntity
import com.vaalzebub.next.to.videoapp.data.mappers.toModel
import com.vaalzebub.next.to.videoapp.data.repository.ApiRepository
import com.vaalzebub.next.to.videoapp.data.repository.DatabaseRepository
import com.vaalzebub.next.to.videoapp.domain.model.VideoModel
import com.vaalzebub.next.to.videoapp.domain.util.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class VideoListUseCase(
    private val databaseRepository: DatabaseRepository,
    private val apiRepository: ApiRepository
) {
    suspend fun getVideos(): Flow<ApiResponse<List<VideoModel>>> {
        return databaseRepository.getVideos().map { list:List<VideoEntity> ->
            ApiResponse.Success(
                list.map { video: VideoEntity ->
                    video.toModel()
                }
            )
        }.onStart {
            val response: ApiResponse<List<VideoEntity>> =
                apiRepository.getVideos()
            if (response is ApiResponse.Success) {
                databaseRepository.upsertAll(response.data)
            }
        }

    }
}