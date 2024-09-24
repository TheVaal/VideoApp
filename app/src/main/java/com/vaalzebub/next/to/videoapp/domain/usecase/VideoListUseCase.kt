package com.vaalzebub.next.to.videoapp.domain.usecase

import com.vaalzebub.next.to.videoapp.data.dto.VideoDto
import com.vaalzebub.next.to.videoapp.data.repository.ApiRepository
import com.vaalzebub.next.to.videoapp.data.repository.DatabaseRepository

class VideoListUseCase(
    private val databaseRepository: DatabaseRepository,
    private val apiRepository: ApiRepository
) {
    fun getVideos(): List<VideoDto> {
        return emptyList()
    }
}