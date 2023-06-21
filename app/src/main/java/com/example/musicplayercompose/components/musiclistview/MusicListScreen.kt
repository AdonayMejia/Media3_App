package com.example.musicplayercompose.components.musiclistview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.musicmodel.Images
import com.example.domain.musicmodel.MusicModel
import com.example.domain.musicmodel.Previews
import com.example.musicplayercompose.R
import com.example.musicplayercompose.components.musiclistview.viewmodel.MusicListViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun MusicListScreen(
    viewModel: MusicListViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val listState by viewModel.listUiState.collectAsState()
    val soundList = viewModel.articlesFlow.collectAsLazyPagingItems()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        MusicListScreenContent(
            onSoundSearch = listState.searchSound,
            sounds = soundList,
            navController = navController
        )
    }
}

@Composable
fun MusicListScreenContent(
    onSoundSearch: (String) -> Unit,
    sounds: LazyPagingItems<MusicModel>,
    navController: NavHostController
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_all))
            .fillMaxSize()
    ) {

        SearchBar(
            onSearchSound = { onSoundSearch(it) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (sounds.itemCount == 0) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No Sounds results",
                    style = MaterialTheme.typography.titleLarge
                )
            }

        } else {
            ItemDisplay(
                sounds = sounds,
                navController = navController
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListPreview() {
    val controller = rememberNavController()

    val sounds = flowOf(
        PagingData.from(
            listOf(
                MusicModel(
                    id = 1,
                    name = "Piano Dan",
                    images = Images(waveform = "https://i0.wp.com/codigoespagueti.com/wp-content/uploads/2023/04/kimetsu-no-yaiba-husbando-mitsuri-fanart.jpg"),
                    preview = Previews(previewHq = "Link")
                ),
                MusicModel(
                    id = 1,
                    name = "Piano Dan",
                    images = Images(waveform = "https://i0.wp.com/codigoespagueti.com/wp-content/uploads/2023/04/kimetsu-no-yaiba-husbando-mitsuri-fanart.jpg"),
                    preview = Previews(previewHq = "Link")
                ),
                MusicModel(
                    id = 1,
                    name = "Piano Dan",
                    images = Images(waveform = "https://i0.wp.com/codigoespagueti.com/wp-content/uploads/2023/04/kimetsu-no-yaiba-husbando-mitsuri-fanart.jpg"),
                    preview = Previews(previewHq = "Link")
                ),
                MusicModel(
                    id = 1,
                    name = "Piano Dan",
                    images = Images(waveform = "https://i0.wp.com/codigoespagueti.com/wp-content/uploads/2023/04/kimetsu-no-yaiba-husbando-mitsuri-fanart.jpg"),
                    preview = Previews(previewHq = "Link")
                ),
                MusicModel(
                    id = 1,
                    name = "Piano Dan",
                    images = Images(waveform = "https://i0.wp.com/codigoespagueti.com/wp-content/uploads/2023/04/kimetsu-no-yaiba-husbando-mitsuri-fanart.jpg"),
                    preview = Previews(previewHq = "Link")
                )
            )
        )
    ).collectAsLazyPagingItems()

    MusicListScreenContent(onSoundSearch = {}, sounds = sounds, navController = controller)
}
