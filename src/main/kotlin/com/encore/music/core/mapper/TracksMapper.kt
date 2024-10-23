package com.encore.music.core.mapper

import com.encore.music.data.remote.dto.spotify.track.TrackDto
import com.encore.music.data.remote.dto.spotify.tracks.Tracks
import com.encore.music.domain.model.tracks.Track as TrackDomainModel
import com.encore.music.domain.model.tracks.Tracks as TracksDomainModel

fun TrackDto.toTrackDomainModel(): TrackDomainModel =
    TrackDomainModel(
        artists = artists?.map { it.toArtistDomainModel() },
        id = id,
        image = album?.images?.firstOrNull()?.url,
        name = name,
        mediaUrl = previewUrl,
    )

fun Tracks.toTracksDomainModel(): TracksDomainModel =
    TracksDomainModel(
        limit = limit,
        offset = offset,
        total = total,
        items = items?.map { it.toTrackDomainModel() },
    )
