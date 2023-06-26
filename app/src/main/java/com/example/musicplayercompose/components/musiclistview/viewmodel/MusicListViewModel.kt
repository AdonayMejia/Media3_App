package com.example.musicplayercompose.components.musiclistview.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.musicmodel.MusicModel
import com.example.domain.usecases.GetSoundsUseCase
import com.example.musicplayercompose.components.musiclistview.uistate.ListUiState
import com.example.musicplayercompose.utils.NewPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MusicListViewModel @Inject constructor(
    private val getSoundsUseCase: GetSoundsUseCase,
) : ViewModel() {

    private val _searchValue = MutableStateFlow("")
    private val _listUiState = MutableStateFlow(
        ListUiState(
            searchSound = this::searchSounds
        )
    )
    val listUiState = _listUiState.asStateFlow()
    val searchValue: StateFlow<String> get() = _searchValue.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    val articlesFlow: Flow<PagingData<MusicModel>> = _searchValue.flatMapLatest { query ->
        Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewPagingSource(getSoundsUseCase, query) }
        ).flow
    }.cachedIn(viewModelScope)

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun searchSounds(query: String) {
        _searchValue.value = query
    }
}
