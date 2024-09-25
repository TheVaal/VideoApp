package com.vaalzebub.next.to.videoapp.domain.usecase

import com.vaalzebub.next.to.videoapp.data.mappers.toModel
import com.vaalzebub.next.to.videoapp.domain.repository.DatabaseDetailRepository
import com.vaalzebub.next.to.videoapp.domain.util.DataHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class VideoUseCase(private val repository: DatabaseDetailRepository) {
        fun getVideoInfo(id: Int): Flow<DataHolder> {
            return combine(repository.getVideo(id), repository.getPlayOrder()){video, order ->
                DataHolder(video.toModel(), order)
            }
        }
}