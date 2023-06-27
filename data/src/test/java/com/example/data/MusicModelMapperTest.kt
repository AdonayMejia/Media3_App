package com.example.data

import com.example.data.mapper.toMusicModel
import com.example.data.network.ApiResult
import com.example.data.network.Images
import com.example.data.network.Previews
import org.junit.Test

class MusicModelMapperTest {
    @Test
    fun `Test musicModel maps correctly`() {
        //Given
        val apiResult = ApiResult(
            id = 254,
            name = "Sound",
            images = Images(waveform = "Fake Waveform"),
            previews = Previews(previewHqMp3 = "Fake preview")
        )

        //When
        val musicModel = apiResult.toMusicModel()

        //Then
        assert(apiResult.id == musicModel.id)
        assert(apiResult.name == musicModel.name)
        assert(apiResult.images.waveform == musicModel.images.waveform)
        assert(apiResult.previews.previewHqMp3 == musicModel.preview.previewHq)
    }
    
}