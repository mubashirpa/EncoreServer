package com.encore.music.data.remote.dto.spotify.playlist

import com.encore.music.data.remote.dto.spotify.episode.EpisodeDto
import com.encore.music.data.remote.dto.spotify.track.TrackDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistTrack(
    @SerialName("added_at")
    val addedAt: String? = null,
    @SerialName("added_by")
    val addedBy: AddedBy? = null,
    @SerialName("is_local")
    val isLocal: Boolean? = null,
    val track: TrackDto? = null,
    val episode: EpisodeDto? = null,
)
