package com.encore.music.data.remote.dto.spotify.episode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResumePoint(
    @SerialName("full_played")
    val fullPlayed: Boolean? = null,
    @SerialName("resume_position_ms")
    val resumePositionMs: Int? = null,
)
