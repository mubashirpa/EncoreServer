package com.encore.music.data.remote.dto.spotify.shows

import com.encore.music.data.remote.dto.spotify.show.ShowDto
import kotlinx.serialization.Serializable

@Serializable
data class Shows(
    val href: String? = null,
    val limit: Int? = null,
    val next: String? = null,
    val offset: Int? = null,
    val previous: String? = null,
    val total: Int? = null,
    val items: List<ShowDto>? = null, // SimplifiedShow
)
