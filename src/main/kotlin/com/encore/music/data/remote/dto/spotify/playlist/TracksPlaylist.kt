package com.encore.music.data.remote.dto.spotify.playlist

import kotlinx.serialization.Serializable

@Serializable
data class TracksPlaylist(
    val href: String? = null,
    val limit: Int? = null,
    val next: String? = null,
    val offset: Int? = null,
    val previous: String? = null,
    val total: Int? = null,
    val items: List<PlaylistTrack>? = null,
)
