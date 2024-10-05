package com.encore.music.domain.model.home

import com.encore.music.domain.model.playlists.Playlist
import kotlinx.serialization.Serializable

@Serializable
data class HomePlaylist(
    val title: String = "",
    val playlists: List<Playlist> = emptyList(),
)
