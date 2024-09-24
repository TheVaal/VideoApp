package com.vaalzebub.next.to.videoapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaalzebub.next.to.videoapp.data.dto.VideoDto
import com.vaalzebub.next.to.videoapp.domain.model.VideoModel
import com.vaalzebub.next.to.videoapp.domain.usecase.VideoListUseCase
import com.vaalzebub.next.to.videoapp.domain.util.onError
import com.vaalzebub.next.to.videoapp.domain.util.onSuccess
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(private val useCase: VideoListUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(ListState())
    val uiState = _uiState
        .onStart { load() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ListState()
        )
    private var state: ListState
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

    private fun load() {
        viewModelScope.launch(Dispatchers.IO + job) {
            useCase.getVideos().collect {
                it.onSuccess { list ->
                    state = state.copy(list = list, isLoading = false)
                }.onError { error ->
                    state = state.copy(error = error.toString(), isLoading = false)
                }
            }
            delay(3000)
        }
    }
}

data class ListState(
    val isLoading: Boolean = true,
    val list: List<VideoModel> = listOf(),
    val error: String = ""
)