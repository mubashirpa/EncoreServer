package com.encore.music.data.remote.dto.spotify.episodes

import com.encore.music.data.remote.dto.spotify.episode.EpisodeDto
import kotlinx.serialization.Serializable

@Serializable
data class Episodes(
    val href: String? = null,
    val limit: Int? = null,
    val next: String? = null,
    val offset: Int? = null,
    val previous: String? = null,
    val total: Int? = null,
    val items: List<EpisodeDto>? = null, // SimplifiedEpisode
)