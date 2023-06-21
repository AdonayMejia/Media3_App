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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.domain.musicmodel.Images
import com.example.domain.musicmodel.MusicModel
import com.example.domain.musicmodel.Previews
import com.example.musicplayercompose.R

@Composable
fun SoundListItem(
    sounds: MusicModel,
    onSoundSelected: (Int) -> Unit
) {
    val painter = rememberAsyncImagePainter(model = sounds.images.waveform)
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable { onSoundSelected(sounds.id) }
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

@Preview
@Composable
fun ItemPreview() {
    val model = MusicModel(
        id = 1,
        name = "Piano Dan",
        images = Images(waveform = "https://i0.wp.com/codigoespagueti.com/wp-content/uploads/2023/04/kimetsu-no-yaiba-husbando-mitsuri-fanart.jpg"),
        preview = Previews(previewHq = "Link")
    )

    SoundListItem(sounds = model, onSoundSelected = {})
}
