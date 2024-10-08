package com.vaalzebub.next.to.videoapp.di

import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import com.vaalzebub.next.to.videoapp.api.VideoApi
import com.vaalzebub.next.to.videoapp.data.entity.VideoDatabase
import com.vaalzebub.next.to.videoapp.data.entity.dao.VideoDao
import com.vaalzebub.next.to.videoapp.data.repository.ApiRepository
import com.vaalzebub.next.to.videoapp.data.repository.DatabaseRepositoryImpl
import com.vaalzebub.next.to.videoapp.domain.repository.DatabaseDetailRepository
import com.vaalzebub.next.to.videoapp.domain.repository.DatabaseListRepository
import com.vaalzebub.next.to.videoapp.domain.usecase.VideoListUseCase
import com.vaalzebub.next.to.videoapp.domain.usecase.VideoUseCase
import com.vaalzebub.next.to.videoapp.presentation.detail.VideoViewModel
import com.vaalzebub.next.to.videoapp.presentation.list.ListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun loadModule() = module {

    single<VideoApi> {
        Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .writeTimeout(10000L, TimeUnit.MILLISECONDS).build())
            .build().create(VideoApi::class.java)
    }
    single<VideoDatabase> {
        Room.databaseBuilder(
            androidContext(),
            VideoDatabase::class.java,
            "videos_db"
        ).fallbackToDestructiveMigration().build()
    }
    factory<VideoDao> {
        get<VideoDatabase>().getDao()
    }
    single<ApiRepository> { ApiRepository(get()) }
    factory<DatabaseDetailRepository> { DatabaseRepositoryImpl(get()) }
    factory<DatabaseListRepository> { DatabaseRepositoryImpl(get()) }
    factory<VideoListUseCase> { VideoListUseCase(get(), get()) }
    factory<VideoUseCase> { VideoUseCase(get()) }
    factory<Player> { ExoPlayer.Builder(androidApplication())
        .build() }
    viewModel {
        ListViewModel(get())
    }
    viewModel {
        VideoViewModel(get(),get(), get())
    }
}