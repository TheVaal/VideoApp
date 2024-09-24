package com.vaalzebub.next.to.videoapp.data.mappers

import com.vaalzebub.next.to.videoapp.data.dto.VideoDto
import com.vaalzebub.next.to.videoapp.data.entity.core.VideoEntity
import com.vaalzebub.next.to.videoapp.domain.model.VideoModel

fun VideoDto.toEntity(): VideoEntity =
    VideoEntity(
        id = id,
        picUrl = image,
        url = videoFiles.maxByOrNull { it.width }?.link ?: "",
        author = user.name,
        authorUrl = user.url,
        duration = duration
    )

fun VideoEntity.toModel(): VideoModel =
    VideoModel(
        id = id,
        picUrl = picUrl,
        url = url,
        author = author,
        authorUrl = authorUrl,
        duration = duration
    )