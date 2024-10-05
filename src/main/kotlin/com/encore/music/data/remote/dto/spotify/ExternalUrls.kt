package com.encore.music.data.remote.dto.spotify

import kotlinx.serialization.Serializable

@Serializable
data class ExternalUrls(
    val spotify: String? = null,
)
