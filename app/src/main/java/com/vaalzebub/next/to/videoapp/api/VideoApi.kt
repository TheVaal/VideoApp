package com.vaalzebub.next.to.videoapp.api

import com.vaalzebub.next.to.videoapp.data.dto.DataWrapper
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

const val key = "2oAFGEg70dCLU4M9KFq6RAKnm7m3hmh2lU5dBxNpNitFW56cR6sUsi37"


interface VideoApi {
    @Headers("Authorization: $key")
    @GET("/videos/popular")
    fun getVideos(@Query("page") page: Int, @Query("per_page") perPage: Int = 15): DataWrapper
}