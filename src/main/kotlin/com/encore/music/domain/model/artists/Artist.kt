package com.encore.music.domain.model.artists

import com.encore.music.domain.model.tracks.Track
import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: String? = null,
    val image: String? = null,
    val name: String? = null,
    val tracks: List<Track>? = null,
)
