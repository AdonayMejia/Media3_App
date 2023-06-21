package com.example.musicplayercompose.components.musicplayerview

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.musicplayercompose.R
import com.example.data.media.UiEvents
import com.example.musicplayercompose.components.musicplayerview.utils.PlayerStatus
import com.example.musicplayercompose.components.musicplayerview.viewmodel.MusicPlayerViewModel

@Composable
fun MusicPlayerScreen(
    soundId: Int,
    viewModel: MusicPlayerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = soundId) {
        uiState.preparePlayer(soundId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (uiState.mediaPlayerStatus) {
            PlayerStatus.Initial -> CircularProgressIndicator(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.size_30dp))
                    .align(Alignment.Center)
            )

            is PlayerStatus.Ready -> {
                MusicplayerScreenContent(
                    image = uiState.soundImage,
                    name = uiState.soundName,
                    mediaEvents = uiState.mediaEvents,
                    isPlaying = uiState.isPlaying,
                    progressFloat = uiState.progressFloat,
                    progressString = uiState.progressString
                )
            }
        }

    }


}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MusicplayerScreenContent(
    image: String = "",
    name:String = "",
    mediaEvents:(UiEvents) -> Unit,
    isPlaying:Boolean,
    progressFloat:Float,
    progressString:String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (LocalInspectionMode.current){
            val painter = painterResource(id = R.drawable.noimage)
            Image(
                painter = painter,
                contentDescription = stringResource(id = R.string.noImage),
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.imageWidth))
                    .height(dimensionResource(id = R.dimen.imageHeight))
            )
        } else {
            val painter = rememberAsyncImagePainter(model = image)

            Image(
                painter = painter,
                contentDescription = stringResource(id = R.string.noImage),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.imageHeight))
            )
        }
        Text(
            text = name,
            fontSize = 20.sp,
            maxLines = 1,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_16dp))
        ) {
            Text(
                text = progressString,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Slider(
            value = progressFloat,
            onValueChange = {}
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            FloatingActionButton(
                onClick = { mediaEvents(UiEvents.Backward) },
                containerColor = MaterialTheme.colorScheme.error
            ) {
                Icon(
                    imageVector = Icons.Rounded.SkipPrevious,
                    contentDescription = stringResource(R.string.prev),
                    tint = MaterialTheme.colorScheme.onError
                )
            }
            Spacer(modifier = Modifier.weight(5f))
            FloatingActionButton(
                onClick = { mediaEvents(UiEvents.Play) },
                containerColor = MaterialTheme.colorScheme.error
            ) {
                if (isPlaying){
                    Icon(
                        imageVector = Icons.Rounded.Pause,
                        contentDescription = stringResource(R.string.play),
                        tint = MaterialTheme.colorScheme.onError
                    )
                } else{
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = stringResource(R.string.play),
                        tint = MaterialTheme.colorScheme.onError
                    )
                }

            }
            Spacer(modifier = Modifier.weight(5f))
            FloatingActionButton(
                onClick = { mediaEvents(UiEvents.Forward) },
                containerColor = MaterialTheme.colorScheme.error
            ) {
                Icon(
                    imageVector = Icons.Rounded.SkipNext,
                    contentDescription = stringResource(R.string.next),
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun MusicPlayerPreview() {
//    val sound = MusicModel(
//        id = 1,
//        name = "Piano Dan",
//        images = Images(waveform = "https://i0.wp.com/codigoespagueti.com/wp-content/uploads/2023/04/kimetsu-no-yaiba-husbando-mitsuri-fanart.jpg"),
//        preview = Previews(previewHq = "Link")
//    )
//    MusicplayerScreenContent(sounds = sound)
//}