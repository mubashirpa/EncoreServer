package com.encore.music.data.remote.dto.spotify.album

import com.encore.music.data.remote.dto.spotify.track.TrackDto
import kotlinx.serialization.Serializable

@Serializable
data class TracksAlbum(
    val href: String? = null,
    val limit: Int? = null,
    val next: String? = null,
    val offset: Int? = null,
    val previous: String? = null,
    val total: Int? = null,
    val items: List<TrackDto>? = null, // SimplifiedTrack
)
