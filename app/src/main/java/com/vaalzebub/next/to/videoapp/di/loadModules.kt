package com.vaalzebub.next.to.videoapp.di

import androidx.room.Room
import com.vaalzebub.next.to.videoapp.api.VideoApi
import com.vaalzebub.next.to.videoapp.data.entity.VideoDatabase
import com.vaalzebub.next.to.videoapp.data.entity.dao.VideoDao
import com.vaalzebub.next.to.videoapp.data.repository.ApiRepository
import com.vaalzebub.next.to.videoapp.domain.usecase.VideoListUseCase
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

fun loadModule() = module {

    single<VideoApi> {
        Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient().newBuilder()
                    .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                    .readTimeout(10000L, TimeUnit.MILLISECONDS)
                    .writeTimeout(10000L, TimeUnit.MILLISECONDS).build()

            )
            .build().create(VideoApi::class.java)
    }
    single<VideoDatabase> {
        Room.databaseBuilder(
            androidContext(),
            VideoDatabase::class.java,
            "hookah_lounge_db"
        ).fallbackToDestructiveMigration().build()
    }
    factory<VideoDao> {
        get<VideoDatabase>().getDao()
    }
    single<ApiRepository> { ApiRepository(get()) }
    factory<VideoListUseCase> { VideoListUseCase(get(),get()) }

}