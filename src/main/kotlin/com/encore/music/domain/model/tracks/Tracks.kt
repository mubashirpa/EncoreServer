package com.encore.music.domain.model.tracks

import kotlinx.serialization.Serializable

@Serializable
data class Tracks(
    val limit: Int? = null,
    val offset: Int? = null,
    val total: Int? = null,
    val items: List<Track>? = null,
)
