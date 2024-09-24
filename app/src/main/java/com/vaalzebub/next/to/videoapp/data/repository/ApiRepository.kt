package com.vaalzebub.next.to.videoapp.data.repository

import com.vaalzebub.next.to.videoapp.api.VideoApi
import com.vaalzebub.next.to.videoapp.data.entity.core.VideoEntity
import com.vaalzebub.next.to.videoapp.data.mappers.toEntity
import com.vaalzebub.next.to.videoapp.domain.util.ApiResponse

class ApiRepository(private val api: VideoApi) {

    suspend fun getVideos(): ApiResponse<List<VideoEntity>> {
        return try {
            val response = api.getVideos()
            if (response.isSuccessful) {
                val responseData = response.body()
                if (responseData != null) {
                    ApiResponse.Success(responseData.videos.map { it.toEntity() })
                } else {
                    ApiResponse.Error("Unexpected error")
                }
            } else {
                ApiResponse.Error("Unable to get response from remote server")
            }
        } catch (e: Exception) {
            ApiResponse.Error("Unexpected error")
        }

    }
}