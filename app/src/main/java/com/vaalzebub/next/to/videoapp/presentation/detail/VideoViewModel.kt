package com.vaalzebub.next.to.videoapp.presentation.detail

import android.util.Log
import androidx.annotation.OptIn
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.vaalzebub.next.to.videoapp.domain.model.VideoModel
import com.vaalzebub.next.to.videoapp.domain.usecase.VideoUseCase
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoViewModel(
    val player: Player,
    private val savedStateHandle: SavedStateHandle,
    private val useCase: VideoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VideoUiState())
    val uiState = _uiState.onStart {
        savedStateHandle.get<Int>("currentId")?.let {
            player.prepare()
            load(it)
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            VideoUiState()
        )
    private var state: VideoUiState
        get() = _uiState.value
        set(newState) {
            _uiState.update { newState }
        }
    private var _job = Job()
    private val job: CompletableJob
        get() {
            _job.cancelChildren()
            _job.cancel()
            _job = Job()
            return _job
        }


    fun onEvent(e: VideoEvent) {
        when (e) {
            VideoEvent.NextVid -> {
                state.nextVidId?.let {
                    load(it)
                    savedStateHandle["currentId"] = it
                }

            }

            VideoEvent.PrevVid -> {
                state.prevVidId?.let {
                    load(it)
                    savedStateHandle["currentId"] = it
                }

            }

            is VideoEvent.ToggleButtons -> {
                state = state.copy(isButtonsVisible = e.visibility)
            }
        }
    }

    private fun load(id: Int) {
        if (state.isLoading) return

        state = state.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO + job) {
            useCase.getVideoInfo(id).collect {
                state = state.copy(
                    isLoading = false,
                    videoInfo = it.video,
                    playOrder = it.playOrder,
                    mediaItem = MediaItem.fromUri(it.video.url),
                    prevVidId = it.playOrder.findPrev(it.video.id),
                    nextVidId = it.playOrder.findNext(it.video.id)
                )
                withContext(Dispatchers.Main) {
                    player.setMediaItem(state.mediaItem)
                    player.seekToNext()
                }
            }
        }
    }

    private fun List<Int>.findPrev(id: Int) = this.getOrElse(this.indexOf(id) - 1) { null }
    private fun List<Int>.findNext(id: Int) = this.getOrElse(this.indexOf(id) + 1) { null }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}

@OptIn(UnstableApi::class)
data class VideoUiState(
    val isLoading: Boolean = false,
    val mediaItem: MediaItem = MediaItem.EMPTY,
    val videoInfo: VideoModel = VideoModel(),
    val playOrder: List<Int> = listOf(),
    val prevVidId: Int? = null,
    val nextVidId: Int? = null,
    val isButtonsVisible: Boolean = true
)

sealed class VideoEvent {
    data object NextVid : VideoEvent()
    data object PrevVid : VideoEvent()
    data class ToggleButtons(val visibility: Boolean) : VideoEvent()
}