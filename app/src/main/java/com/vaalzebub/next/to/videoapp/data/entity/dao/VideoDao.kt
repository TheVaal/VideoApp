package com.vaalzebub.next.to.videoapp.data.entity.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.vaalzebub.next.to.videoapp.data.entity.core.VideoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {

    @Upsert
    suspend fun upsertAllVideos(loungeHardness: List<VideoEntity>)

    @Query("SELECT * FROM videos")
    fun getVideos(): Flow<List<VideoEntity>>

    @Query("SELECT * FROM videos WHERE id = :id")
    fun getVideoById(id: Long): Flow<VideoEntity>

    @Query("DELETE FROM videos")
    suspend fun clearAll()

    @Query("SELECT * FROM videos")
    fun pagingSource(): PagingSource<Int, VideoEntity>
}