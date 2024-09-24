package com.vaalzebub.next.to.videoapp.data.dto

import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user")
    val user: UserDto,
    @SerializedName("video_files")
    val videoFiles: List<VideoFileDto>,
    @SerializedName("video_pictures")
    val videoPictures: List<VideoPictureDto>,
    @SerializedName("width")
    val width: Int
)
