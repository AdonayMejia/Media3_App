package com.example.musicplayercompose.components.musiclistview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.musicmodel.Images
import com.example.domain.musicmodel.MusicModel
import com.example.domain.musicmodel.Previews
import com.example.musicplayercompose.components.musiclistview.utils.isLandscape
import kotlinx.coroutines.flow.flowOf

@Composable
fun ItemDisplay(
    sounds: LazyPagingItems<MusicModel>,
    navController: NavHostController
) {
    if (isLandscape()) {
        LazyVerticalGrid(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalArrangement = Arrangement.SpaceEvenly,
            columns = GridCells.Fixed(4),
        ){
            items(sounds.itemCount) {sound ->
                val soundList = sounds[sound] ?: return@items
                SoundListItem(
                    sounds = soundList,
                    navHostController = navController
                )
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            items(sounds.itemCount) { sound ->
                val songList = sounds[sound] ?: return@items
                SoundListItem(
                    sounds = songList,
                    navHostController = navController
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewItemDisplay() {
    val controller = rememberNavController()

    val sounds = flowOf(
        PagingData.from(
            listOf(
                MusicModel(id = 1,
                name = "Piano Dan",
                images = Images(waveform = "https://i0.wp.com/codigoespagueti.com/wp-content/uploads/2023/04/kimetsu-no-yaiba-husbando-mitsuri-fanart.jpg"),
                preview = Previews(previewHq = "Link")
                ),
                MusicModel(id = 1,
                    name = "Piano Dan",
                    images = Images(waveform = "https://i0.wp.com/codigoespagueti.com/wp-content/uploads/2023/04/kimetsu-no-yaiba-husbando-mitsuri-fanart.jpg"),
                    preview = Previews(previewHq = "Link")
                ),
                MusicModel(id = 1,
                    name = "Piano Dan",
                    images = Images(waveform = "https://i0.wp.com/codigoespagueti.com/wp-content/uploads/2023/04/kimetsu-no-yaiba-husbando-mitsuri-fanart.jpg"),
                    preview = Previews(previewHq = "Link")
                ),
                MusicModel(id = 1,
                    name = "Piano Dan",
                    images = Images(waveform = "https://i0.wp.com/codigoespagueti.com/wp-content/uploads/2023/04/kimetsu-no-yaiba-husbando-mitsuri-fanart.jpg"),
                    preview = Previews(previewHq = "Link")
                ),
                MusicModel(id = 1,
                    name = "Piano Dan",
                    images = Images(waveform = "https://i0.wp.com/codigoespagueti.com/wp-content/uploads/2023/04/kimetsu-no-yaiba-husbando-mitsuri-fanart.jpg"),
                    preview = Previews(previewHq = "Link")
                )
                )
            )
        ).collectAsLazyPagingItems()

    ItemDisplay(sounds = sounds , navController = controller)
}
