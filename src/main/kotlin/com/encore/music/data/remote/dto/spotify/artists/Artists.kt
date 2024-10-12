package com.encore.music.data.remote.dto.spotify.artists

import com.encore.music.data.remote.dto.spotify.artist.ArtistDto
import kotlinx.serialization.Serializable

@Serializable
data class Artists(
    val href: String? = null,
    val limit: Int? = null,
    val next: String? = null,
    val offset: Int? = null,
    val previous: String? = null,
    val total: Int? = null,
    val items: List<ArtistDto>? = null,
)
