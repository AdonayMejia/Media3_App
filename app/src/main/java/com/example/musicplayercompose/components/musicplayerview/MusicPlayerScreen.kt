package com.example.musicplayercompose.components.musicplayerview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.domain.musicmodel.Images
import com.example.domain.musicmodel.MusicModel
import com.example.domain.musicmodel.Previews
import com.example.musicplayercompose.R
import com.example.musicplayercompose.components.musicplayerview.viewmodel.MusicPlayerViewModel

@Composable
fun MusicPlayerScreen(
    soundId: Int,
    viewModel: MusicPlayerViewModel = hiltViewModel()
) {
    val soundDetails by viewModel.soundDetail.collectAsState(null)

    soundDetails?.let { sounds ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            MusicplayerScreenContent(sounds = sounds)
        }
    }
}

@Composable
fun MusicplayerScreenContent(
    sounds:MusicModel
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
            val painter = rememberAsyncImagePainter(model = sounds.images.waveform)

            Image(
                painter = painter,
                contentDescription = stringResource(id = R.string.noImage),
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.imageWidth))
                    .height(dimensionResource(id = R.dimen.imageHeight))
            )
        }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_all)))
        Slider(
            value = 0.5f,
            onValueChange = {}
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(26.dp)
        ) {
            FloatingActionButton(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.error
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.prev),
                    tint = MaterialTheme.colorScheme.onError
                )
            }
            Spacer(modifier = Modifier.weight(15f))
            FloatingActionButton(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.error
            ) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = stringResource(R.string.play),
                    tint = MaterialTheme.colorScheme.onError
                )
            }
            Spacer(modifier = Modifier.weight(15f))
            FloatingActionButton(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.error
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.next),
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MusicPlayerPreview() {
    val sound = MusicModel(
        id = 1,
        name = "Piano Dan",
        duration = 2,
        images = Images(waveform = "https://i0.wp.com/codigoespagueti.com/wp-content/uploads/2023/04/kimetsu-no-yaiba-husbando-mitsuri-fanart.jpg"),
        preview = Previews(previewHq = "Link")
    )
    MusicplayerScreenContent(sounds = sound)
}