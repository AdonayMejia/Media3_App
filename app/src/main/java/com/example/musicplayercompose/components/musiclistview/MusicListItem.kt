package com.example.musicplayercompose.components.musiclistview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.domain.musicmodel.MusicModel
import com.example.musicplayercompose.R

@Composable
fun SoundListItem(
    sounds: MusicModel,
    navHostController: NavHostController
) {
    val painter = rememberAsyncImagePainter(model = sounds.images.waveform)
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable { navHostController.navigate("MusicPlayerScreen/${sounds.id}") }
    ) {
        Image(
            painter = painter,
            contentDescription = "Previews Images",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        )
        Text(
            text = sounds.name,
            maxLines = 1
        )
    }
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_all)))
}
