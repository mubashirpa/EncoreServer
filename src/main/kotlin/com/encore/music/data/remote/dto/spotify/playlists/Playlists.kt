package com.encore.music.data.remote.dto.spotify.playlists

import com.encore.music.data.remote.dto.spotify.playlist.PlaylistDto
import kotlinx.serialization.Serializable

@Serializable
data class Playlists(
    val href: String? = null,
    val limit: Int? = null,
    val next: String? = null,
    val offset: Int? = null,
    val previous: String? = null,
    val total: Int? = null,
    val items: List<PlaylistDto>? = null, // SimplifiedPlaylist
)
