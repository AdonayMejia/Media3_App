package com.example.data.network

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val count: Int,
    val results: List<ApiResult>
)

data class ApiResult(
    val id: Int,
    val name: String,
    val duration: Int,
    val images: Images,
    val previews: Previews
)

data class Images(
    @SerializedName("waveform_m")
    val waveform: String
)

data class Previews(
    @SerializedName("preview-hq-mp3")
    val previewHqMp3: String
)
