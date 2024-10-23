package com.encore.music.domain.model.search

import com.encore.music.domain.model.artists.Artists
import com.encore.music.domain.model.playlists.Playlists
import com.encore.music.domain.model.tracks.Tracks
import kotlinx.serialization.Serializable

@Serializable
data class Search(
    val tracks: Tracks? = null,
    val artists: Artists? = null,
    val playlists: Playlists? = null,
)
