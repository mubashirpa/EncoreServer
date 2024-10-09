package com.encore.music.core.mapper

import com.encore.music.data.remote.dto.spotify.artist.ArtistDto
import com.encore.music.data.remote.dto.spotify.artist.ArtistTracksDto
import com.encore.music.domain.model.artists.Artist as ArtistDomainModel
import com.encore.music.domain.model.tracks.Track as TrackDomainModel

fun ArtistDto.toArtistDomainModel(tracks: List<TrackDomainModel>? = null): ArtistDomainModel =
    ArtistDomainModel(
        followers = followers?.total,
        id = id,
        image = images?.firstOrNull()?.url,
        name = name,
        tracks = tracks,
    )

fun ArtistTracksDto.toTrackDomainModelList(): List<TrackDomainModel> = tracks?.map { it.toTrackDomainModel() }.orEmpty()
