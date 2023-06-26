package com.example.musicplayercompose

import com.example.domain.usecases.GetSoundsUseCase
import com.example.musicplayercompose.components.musiclistview.viewmodel.MusicListViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test


class HomeViewModelTest {
    @RelaxedMockK
    private lateinit var searchUseCase: GetSoundsUseCase
    private lateinit var musicListViewModel: MusicListViewModel

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        musicListViewModel = MusicListViewModel((searchUseCase))
    }

    @Test
    fun `should update query when search function is called`() = runTest {
        // Given
        val query = "Test"
        musicListViewModel = MusicListViewModel(searchUseCase)

        // When
        musicListViewModel.searchSounds(query)

        // Then
        val actual = musicListViewModel.searchValue.first()
        assert(query == actual)
    }
}
