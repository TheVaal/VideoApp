package com.vaalzebub.next.to.videoapp.data.entity.dao

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
    fun getVideoById(id: Int): Flow<VideoEntity>

    @Query("DELETE FROM videos")
    suspend fun clearAll()

    @Query("SELECT id FROM videos")
    fun getPlayOrder(): Flow<List<Int>>

}