package com.encore.music.data.remote.dto.spotify.track

import com.encore.music.data.remote.dto.spotify.ExternalUrls
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinkedFrom(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val href: String? = null,
    val id: String? = null,
    val type: String? = null,
    val uri: String? = null,
)
