package com.vaalzebub.next.to.videoapp.data.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vaalzebub.next.to.videoapp.data.entity.core.VideoEntity
import com.vaalzebub.next.to.videoapp.data.entity.dao.VideoDao

@Database(
    entities = [
        VideoEntity::class,
    ],
    version = 1
)
abstract class VideoDatabase : RoomDatabase() {
    abstract fun getDao() : VideoDao
}