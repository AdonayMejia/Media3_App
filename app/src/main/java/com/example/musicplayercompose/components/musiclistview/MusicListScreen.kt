package com.example.musicplayercompose.components.musiclistview

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.domain.musicmodel.MusicModel
import com.example.musicplayercompose.R
import com.example.musicplayercompose.components.musiclistview.viewmodel.MusicListViewModel

@Composable
fun MusicListScreen(
    viewModel: MusicListViewModel = hiltViewModel()
) {
    val listState by viewModel.listUiState.collectAsState()
    val soundList = viewModel.articlesFlow.collectAsLazyPagingItems()
    val list by viewModel.sounds.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        MusicListScreenContent(
            onSoundSearch = listState.searchSound,
            sounds = soundList
        )
    }
}

@Composable
fun MusicListScreenContent(
    onSoundSearch: (String) -> Unit,
    sounds: LazyPagingItems<MusicModel>
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_all))
            .fillMaxSize()
    ) {
//        OutlinedTextField(
//            value = searchQuery.value,
//            onValueChange = { searchQuery.value = it },
//            label = { Text("Search") },
//            trailingIcon = { IconButton(onClick = { onSoundSearch(searchQuery.value) })
//            { Icon(Icons.Default.Search, contentDescription = "Search Icon") } },
//            singleLine = true,
//            modifier = Modifier.fillMaxWidth()
//        )
        SearchBar(
//            searchQuery = searchQuery,
            onSearchSound = { onSoundSearch(it) }
        )
        Spacer(modifier = Modifier.height(8.dp))
//        list?.let {
//            LazyColumn{
//                items(it){
//                    Text(text = it.name)
//                }
//            }
//        }
        if (sounds.itemCount == 0) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No anime results",
                    style = MaterialTheme.typography.titleLarge
                )
            }

        } else {
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                items(sounds.itemCount) { sound ->
                    val songList = sounds[sound] ?: return@items
                    SoundListItem(sounds = songList)
                }
            }
        }
    }

}

@Composable
fun SoundListItem(sounds: MusicModel) {
    val painter = rememberAsyncImagePainter(model = sounds.images.waveform)
    Card(
        shape = RectangleShape,
        modifier = Modifier.fillMaxSize()
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
            text = sounds.name
        )
    }
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_all)))
}
