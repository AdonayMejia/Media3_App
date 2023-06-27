package com.example.data

import com.example.data.network.ApiResponse
import com.example.data.network.ApiResult
import com.example.data.network.Images
import com.example.data.network.Previews
import com.example.data.network.RetrofitSearch
import com.example.data.repositoryimpl.SoundRepositoryImpl
import com.example.domain.musicmodel.MusicModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RepositoryImplTest {

    @RelaxedMockK
    private lateinit var apiService:RetrofitSearch

    private lateinit var soundRepositoryImpl: SoundRepositoryImpl
    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        soundRepositoryImpl = SoundRepositoryImpl(apiService)
    }
    @Test
    fun `Test getSound method that will return an specific sound`() = runTest {
        //Given
        val id = 254
        val apiResult = ApiResult(
            id = 254,
            name = "Sound",
            images = Images(waveform = "Fake Waveform"),
            previews = Previews(previewHqMp3 = "Fake preview")
        )
        coEvery { apiService.getSound(id) } returns apiResult
        //When
        val soundReturn = MusicModel(
            id = 254,
            name = "Sound",
            images = com.example.domain.musicmodel.Images(waveform = "Fake Waveform"),
            preview = com.example.domain.musicmodel.Previews(previewHq = "Fake preview")
        )

        val currentSoundResult = soundRepositoryImpl.getSound(id)
        //Then
        assertEquals(soundReturn,currentSoundResult)
    }

    @Test
    fun `Test search sound when is not returning an empty list`() = runTest {
        //Given
        val query = "piano"
        val pageSize = 10
        val page = 1
        val apiResponse = ApiResponse(
            count = 2,
            results = listOf(
                ApiResult(
                    id = 254,
                    name = "Sound",
                    images = Images(waveform = "Fake Waveform"),
                    previews = Previews(previewHqMp3 = "Fake preview")
                ),
                ApiResult(
                    id = 255,
                    name = "Sound",
                    images = Images(waveform = "Fake Waveform"),
                    previews = Previews(previewHqMp3 = "Fake preview")
                )
            )
        )
        coEvery { apiService.searchSounds(query = query, page = page.toString(), pageSize = pageSize.toString()) } returns apiResponse

        //When
        val searchSoundResult = listOf(
            MusicModel(
                id = 254,
                name = "Sound",
                images = com.example.domain.musicmodel.Images(waveform = "Fake Waveform"),
                preview = com.example.domain.musicmodel.Previews(previewHq = "Fake preview")
            ),
            MusicModel(
                id = 255,
                name = "Sound",
                images = com.example.domain.musicmodel.Images(waveform = "Fake Waveform"),
                preview = com.example.domain.musicmodel.Previews(previewHq = "Fake preview")
            )
        )
        val currentResult = soundRepositoryImpl.searchSounds(query, page, pageSize)

        //Then
        assertEquals(searchSoundResult,currentResult)
    }

    @Test
    fun `Test search sound when the request is returning an empty list`() = runTest{
        //Given
        val query = ""
        val page = 1
        val pageSize = 10

        //When
        val currentResult = soundRepositoryImpl.searchSounds(query, page, pageSize)

        //Then
        assertEquals(emptyList<MusicModel>(), currentResult)
    }

}