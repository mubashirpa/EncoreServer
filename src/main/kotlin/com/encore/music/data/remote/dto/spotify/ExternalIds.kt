package com.encore.music.data.remote.dto.spotify

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExternalIds(
    @SerialName("ean")
    val internationalArticleNumber: String? = null,
    @SerialName("isrc")
    val internationalStandardRecordingCode: String? = null,
    @SerialName("upc")
    val universalProductCode: String? = null,
)
