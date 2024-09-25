package com.vaalzebub.next.to.videoapp.data.mappers

import androidx.compose.ui.text.intl.Locale
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

fun VideoEntity.toModel(): VideoModel {
    val hours = duration / 3600
    val minutes = (duration % 3600) / 60
    val secs = duration % 60


    return VideoModel(
        id = id,
        picUrl = picUrl,
        url = url,
        author = author,
        authorUrl = authorUrl,
        duration = String.format(
            Locale.current.platformLocale,
            "%02d:%02d:%02d",
            hours,
            minutes,
            secs
        )
    )
}