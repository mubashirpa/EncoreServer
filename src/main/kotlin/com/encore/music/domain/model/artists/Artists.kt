package com.encore.music.domain.model.artists

import kotlinx.serialization.Serializable

@Serializable
data class Artists(
    val limit: Int? = null,
    val offset: Int? = null,
    val total: Int? = null,
    val items: List<Artist>? = null,
)
