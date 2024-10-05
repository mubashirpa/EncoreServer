package com.encore.music.core.mapper

import com.encore.music.data.remote.dto.spotify.artist.ArtistDto
import com.encore.music.domain.model.artists.Artist

fun ArtistDto.toArtist(): Artist =
    Artist(
        id = id,
        image = images?.firstOrNull()?.url,
        name = name,
        tracks = null,
    )
