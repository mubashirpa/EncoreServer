package com.encore.music.data.remote.dto.spotify.artist

import com.encore.music.data.remote.dto.spotify.track.TrackDto
import kotlinx.serialization.Serializable

@Serializable
data class ArtistTracksDto(
    val tracks: List<TrackDto>? = null,
)
