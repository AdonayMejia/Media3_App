package com.example.musicplayercompose

import com.example.data.media.MediaServiceController
import com.example.data.media.MediaState
import com.example.data.media.PlayerEvents
import com.example.domain.usecases.GetSoundByIdUSeCase
import com.example.musicplayercompose.components.musiclistview.viewmodel.MusicListViewModel
import com.example.musicplayercompose.components.musicplayerview.viewmodel.MusicPlayerViewModel
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MusicPlayerViewModelTest {

    @RelaxedMockK
    private lateinit var mediaServiceControllerMock:MediaServiceController
    @RelaxedMockK
    private lateinit var getSongUSeCase: GetSoundByIdUSeCase

    private lateinit var playerViewModel: MusicPlayerViewModel
    private lateinit var mediaState:MutableStateFlow<MediaState>

    @get:Rule
    var setMainCoroutineRule = SetMainCoroutineRule()

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        mediaState = MutableStateFlow(MediaState.init)
        coEvery { mediaServiceControllerMock.mediaState } returns mediaState
        playerViewModel = MusicPlayerViewModel(getSongUSeCase,mediaServiceControllerMock)
    }

    @After
    fun onAfter(){
        clearMocks(getSongUSeCase,mediaServiceControllerMock)
    }

    @Test
    fun `Collect mediaState and emit its value and change UiStatePlayer value`() = runTest{
        //Given
        val mediaState = MutableStateFlow<MediaState>(MediaState.init)
        coEvery { mediaServiceControllerMock.mediaState } returns mediaState

        val mediaViewModel = MusicPlayerViewModel(getSongUSeCase,mediaServiceControllerMock)

        //When
        mediaState.value = MediaState.Playing(true)

        //Then
        assertEquals(true,mediaViewModel.uiState.value.isPlaying)
    }

    @Test
    fun `Collect mediaState set it to ready and change UiStatePlayer duration`() = runTest {
        //Given
        val mediaState = MutableStateFlow<MediaState>(MediaState.init)
        coEvery { mediaServiceControllerMock.mediaState } returns mediaState

        val mediaViewModel = MusicPlayerViewModel(getSongUSeCase,mediaServiceControllerMock)

        //When
        val duration = 150L
        mediaState.value = MediaState.Ready(duration)

        //Then
        assertEquals(duration, mediaViewModel.uiState.value.duration)
    }

    @Test
    fun `Testing on cleared method that calls onPlayerEvent with Stop`() = runTest {
        //Given
        val mediaState = MutableStateFlow<MediaState>(MediaState.init)
        coEvery { mediaServiceControllerMock.mediaState } returns mediaState
        coEvery { mediaServiceControllerMock.onPlayerEvent(PlayerEvents.Stop) } just Runs

        val mediaViewModel = MusicPlayerViewModel(getSongUSeCase,mediaServiceControllerMock)

        //When
        mediaViewModel.onCleared()

        //Then
        coVerify { mediaServiceControllerMock.onPlayerEvent(PlayerEvents.Stop) }
    }

    @Test
    fun `Test format duration method`() = runTest {
        //Given
        val mediaViewModel = MusicPlayerViewModel(getSongUSeCase,mediaServiceControllerMock)
        val milliSecondsFormatDuration = 70000L

        //When
        val durationFormat = mediaViewModel.formatDuration(milliSecondsFormatDuration)

        //Then
        assertEquals("01:10",durationFormat)
    }

    @Test
    fun `Calculate progress value`() = runTest {
        //Given
        val duration = 30000L
        val currentProgress = 20000L

        val mediaStateFlow = MutableStateFlow<MediaState>(MediaState.Ready(duration))
        coEvery { mediaServiceControllerMock.mediaState } returns mediaStateFlow

        val mediaViewModel = MusicPlayerViewModel(getSongUSeCase,mediaServiceControllerMock)

        val floatProgress = currentProgress.toFloat() / duration
        val stringProgress = mediaViewModel.formatDuration(currentProgress)

        //When
        mediaViewModel.calculateProgressValues(currentProgress)

        //Then
        assertEquals(floatProgress,mediaViewModel.uiState.value.progressFloat)
        assertEquals(stringProgress,mediaViewModel.uiState.value.progressString)

    }







































}