package com.example.musicplayercompose.components.musicplayerview.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.musicmodel.MusicModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(

) : ViewModel() {
    private val _soundDetail = MutableStateFlow<MusicModel?>(null)
    val soundDetail: StateFlow<MusicModel?> = _soundDetail
}