package com.encore.music.domain.model.tracks

import com.encore.music.domain.model.artists.Artist
import kotlinx.serialization.Serializable

@Serializable
data class Track(
    val artists: List<Artist>? = null,
    val id: String? = null,
    val image: String? = null,
    val name: String? = null,
    val mediaUrl: String? = null,
)
