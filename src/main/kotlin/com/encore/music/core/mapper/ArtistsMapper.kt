package com.encore.music.core.mapper

import com.encore.music.data.remote.dto.spotify.artist.ArtistDto
import com.encore.music.data.remote.dto.spotify.artist.ArtistTracksDto
import com.encore.music.data.remote.dto.spotify.artists.Artists
import com.encore.music.domain.model.artists.Artist as ArtistDomainModel
import com.encore.music.domain.model.artists.Artists as ArtistsDomainModel
import com.encore.music.domain.model.tracks.Track as TrackDomainModel

fun ArtistDto.toArtistDomainModel(tracks: List<TrackDomainModel>? = null): ArtistDomainModel =
    ArtistDomainModel(
        externalUrl = externalUrls?.spotify,
        followers = followers?.total,
        id = id,
        image = images?.firstOrNull()?.url,
        name = name,
        tracks = tracks,
    )

fun Artists.toArtistsDomainModel(): ArtistsDomainModel =
    ArtistsDomainModel(
        limit = limit,
        offset = offset,
        total = total,
        items = items?.map { it.toArtistDomainModel() },
    )

fun ArtistTracksDto.toTrackDomainModelList(): List<TrackDomainModel> = tracks?.map { it.toTrackDomainModel() }.orEmpty()
