package com.vaalzebub.next.to.videoapp.presentation.detail

import android.view.View
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.vaalzebub.next.to.videoapp.R
import org.koin.androidx.compose.koinViewModel

@OptIn(UnstableApi::class)
@RootNavGraph
@Destination
@Composable
fun DetailScreen(currentId: Int) {
    val viewModel: VideoViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                AndroidView(
                    factory = { context ->
                        PlayerView(context).also {
                            it.player = viewModel.player
                            it.setShowNextButton(false)
                            it.setShowPreviousButton(false)
                            it.setControllerVisibilityListener(PlayerView.ControllerVisibilityListener { visibility ->
                                viewModel.onEvent(VideoEvent.ToggleButtons(visibility == View.VISIBLE))
                            })
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)

                )

                // Замінив дефолтні кнопки на кастомні.
                // Для того щоб не вивантажувати всі відео разом
                // та не формувати плейліст на екрані з деталюю
                // а брати тільки айдішники наступного та попереднього відео
                // та перетворювати на MediaItem тільки відео яке буде переглядатися


                if (state.isButtonsVisible) {
                    ChangeVideoButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        enabled = state.nextVidId != null,
                        resId = R.drawable.skip_next
                    ) {
                        viewModel.onEvent(VideoEvent.NextVid)
                    }
                    ChangeVideoButton(
                        modifier = Modifier.align(Alignment.CenterStart),
                        enabled = state.prevVidId != null,
                        resId = R.drawable.skip_previous
                    ) {
                        viewModel.onEvent(VideoEvent.PrevVid)
                    }
                }

            }
        }
    }
}

@Composable
fun ChangeVideoButton(modifier: Modifier, enabled: Boolean, resId: Int, action: () -> Unit) {
    IconButton(
        onClick = { action() },
        enabled = enabled,
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier
                .size(64.dp),
            imageVector = ImageVector.vectorResource(id = resId),
            contentDescription = "to previous Video",
            tint = if (enabled) {
                Color.White
            } else Color.DarkGray
        )
    }
}