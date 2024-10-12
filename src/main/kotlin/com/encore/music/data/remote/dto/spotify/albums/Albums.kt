package com.encore.music.data.remote.dto.spotify.albums

import com.encore.music.data.remote.dto.spotify.album.AlbumDto
import kotlinx.serialization.Serializable

@Serializable
data class Albums(
    val href: String? = null,
    val limit: Int? = null,
    val next: String? = null,
    val offset: Int? = null,
    val previous: String? = null,
    val total: Int? = null,
    val items: List<AlbumDto>? = null, // SimplifiedAlbum
)
