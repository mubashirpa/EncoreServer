package com.encore.music.domain.model.playlists

import com.encore.music.domain.model.tracks.Track
import kotlinx.serialization.Serializable

@Serializable
data class Playlist(
    val description: String? = null,
    val id: String? = null,
    val image: String? = null,
    val isLocal: Boolean? = null,
    val name: String? = null,
    val owner: String? = null,
    val ownerId: String? = null,
    val tracks: List<Track>? = null,
)
