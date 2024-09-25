package com.vaalzebub.next.to.videoapp.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import com.vaalzebub.next.to.videoapp.domain.model.VideoModel
import com.vaalzebub.next.to.videoapp.presentation.destinations.DetailScreenDestination
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun ListScreen(navigator: NavController) {
    val viewModel = koinViewModel<ListViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    if (state.isLoading) {
        Box(Modifier.fillMaxSize()) { CircularProgressIndicator(Modifier.align(Alignment.Center)) }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.list) {

                ElevatedCard(modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillParentMaxHeight(0.4f),
                    onClick = {
                        navigator.navigate(DetailScreenDestination(it.id))
                    }
                ) {

                    CardContent(it)

                }
            }
        }
    }

}

@Composable
private fun CardContent(video: VideoModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
    ) {
        AsyncImage(
            model = video.picUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp, 24.dp)
                    .align(Alignment.BottomEnd)
                    .clip(
                        RoundedCornerShape(25)
                    )
                    .background(Color.Black.copy(alpha = 0.5f))
            ) {
                Text(
                    text = video.duration,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    modifier = Modifier

                        .align(Alignment.Center),
                    color = Color.White
                )
            }
        }
    }

    Text(
        text = "Posted by @${video.author}",
        textAlign = TextAlign.Left,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    )
}