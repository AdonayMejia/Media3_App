package com.example.musicplayercompose.components.musiclistview.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.musicmodel.MusicModel
import com.example.domain.repository.SoundRepository
import com.example.domain.usecases.GetSoundsUseCase
import com.example.musicplayercompose.components.musiclistview.uistate.ListUiState
import com.example.musicplayercompose.utils.NewPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicListViewModel @Inject constructor(
    private val getSoundsUseCase: GetSoundsUseCase,
) : ViewModel() {

    private val searchValue = MutableStateFlow("")
    private val _sounds = MutableStateFlow<List<MusicModel>?>(null)
    val sounds: StateFlow<List<MusicModel>?> get() = _sounds

    private val _listUiState = MutableStateFlow(
        ListUiState(
            searchSound = this::searchSounds
        )
    )

    val listUiState = _listUiState.asStateFlow()

//    private fun createPaging(
//       query:String
//    ): Pager<Int,MusicModel>{
//        return Pager(
//            config = PagingConfig(
//                pageSize = 15,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { NewPagingSource(getSoundsUseCase,query) }
//        )
//    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val articlesFlow: Flow<PagingData<MusicModel>> = searchValue.flatMapLatest { query ->
        Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewPagingSource(getSoundsUseCase, query) }
        ).flow
    }.cachedIn(viewModelScope)

    private fun searchSounds(query:String){
//        viewModelScope.launch {
//            try {
//                val result = getSoundsUseCase.getExecute(query)
//                _sounds.emit(result)
//            } catch (e: Exception) {
//                Log.wtf("SoundViewModel", "API Error: ${e.message}")
//            }
//        }
        searchValue.value = query
//        Log.wtf("Search", "${createPaging(searchValue.value).flow}")
    }
}