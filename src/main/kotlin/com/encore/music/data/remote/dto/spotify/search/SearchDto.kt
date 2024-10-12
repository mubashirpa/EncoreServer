package com.encore.music.data.remote.dto.spotify.search

import com.encore.music.data.remote.dto.spotify.albums.Albums
import com.encore.music.data.remote.dto.spotify.artists.Artists
import com.encore.music.data.remote.dto.spotify.episodes.Episodes
import com.encore.music.data.remote.dto.spotify.playlists.Playlists
import com.encore.music.data.remote.dto.spotify.shows.Shows
import com.encore.music.data.remote.dto.spotify.tracks.Tracks
import kotlinx.serialization.Serializable

@Serializable
data class SearchDto(
    val tracks: Tracks? = null,
    val artists: Artists? = null,
    val albums: Albums? = null,
    val playlists: Playlists? = null,
    val shows: Shows? = null,
    val episodes: Episodes? = null,
    // val audiobooks: Audiobooks? = null,
)
