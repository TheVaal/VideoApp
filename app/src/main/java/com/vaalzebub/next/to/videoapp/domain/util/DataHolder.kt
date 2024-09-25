package com.vaalzebub.next.to.videoapp.domain.util

import com.vaalzebub.next.to.videoapp.domain.model.VideoModel

data class DataHolder(val video: VideoModel, val playOrder: List<Int>)
