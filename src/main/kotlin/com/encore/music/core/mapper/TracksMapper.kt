package com.encore.music.core.mapper

import com.encore.music.data.remote.dto.spotify.track.TrackDto
import com.encore.music.domain.model.tracks.Track as TrackDomainModel

fun TrackDto.toTrackDomainModel(): TrackDomainModel =
    TrackDomainModel(
        artists = artists?.map { it.toArtistDomainModel() },
        id = id,
        image = album?.images?.firstOrNull()?.url,
        name = name,
        mediaUrl = previewUrl,
    )
