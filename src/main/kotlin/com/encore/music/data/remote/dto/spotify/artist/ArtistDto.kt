package com.encore.music.data.remote.dto.spotify.artist

import com.encore.music.data.remote.dto.spotify.ExternalUrls
import com.encore.music.data.remote.dto.spotify.Followers
import com.encore.music.data.remote.dto.spotify.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistDto(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val followers: Followers? = null,
    val genres: List<String>? = null,
    val href: String? = null,
    val id: String? = null,
    val images: List<Image>? = null,
    val name: String? = null,
    val popularity: Int? = null,
    val type: String? = null,
    val uri: String? = null,
)
