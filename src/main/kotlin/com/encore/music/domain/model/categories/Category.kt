package com.encore.music.domain.model.categories

import com.encore.music.domain.model.playlists.Playlist
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val icon: String? = null,
    val id: String? = null,
    val name: String? = null,
    val playlists: List<Playlist>? = null,
)
