package com.encore.music.data.remote.dto.spotify.playlist

import com.encore.music.data.remote.dto.spotify.ExternalUrls
import com.encore.music.data.remote.dto.spotify.Followers
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val followers: Followers? = null,
    val href: String? = null,
    val id: String? = null,
    val type: String? = null,
    val uri: String? = null,
    @SerialName("display_name")
    val displayName: String? = null,
)
