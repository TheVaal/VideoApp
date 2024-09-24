package com.vaalzebub.next.to.videoapp.api

import com.vaalzebub.next.to.videoapp.data.dto.DataWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface VideoApi {
    @GET("videos/popular")
    suspend fun getVideos(
        @Query("per_page") perPage: Int = 15,
        @Header("Authorization") key: String = "2oAFGEg70dCLU4M9KFq6RAKnm7m3hmh2lU5dBxNpNitFW56cR6sUsi37"
    ): Response<DataWrapper>
}