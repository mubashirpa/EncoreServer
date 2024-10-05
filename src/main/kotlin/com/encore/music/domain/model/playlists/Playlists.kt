package com.encore.music.domain.model.playlists

import kotlinx.serialization.Serializable

@Serializable
data class Playlists(
    val limit: Int? = null,
    val offset: Int? = null,
    val total: Int? = null,
    val items: List<Playlist>? = null,
)
