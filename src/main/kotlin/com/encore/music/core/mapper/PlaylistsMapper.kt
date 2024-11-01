package com.encore.music.core.mapper

import com.encore.music.data.remote.dto.spotify.playlist.PlaylistDto
import com.encore.music.data.remote.dto.spotify.playlist.PlaylistTrack
import com.encore.music.data.remote.dto.spotify.playlist.TracksPlaylist
import com.encore.music.data.remote.dto.spotify.playlists.Playlists
import com.encore.music.data.remote.dto.spotify.playlists.PlaylistsDto
import com.encore.music.domain.model.playlists.Playlist as PlaylistDomainModel
import com.encore.music.domain.model.playlists.Playlists as PlaylistsDomainModel
import com.encore.music.domain.model.tracks.Track as TrackDomainModel
import com.encore.music.domain.model.tracks.Tracks as TracksDomainModel

fun TracksPlaylist.toTracksDomainModel(): TracksDomainModel =
    TracksDomainModel(
        limit = limit,
        offset = offset,
        total = total,
        items = items?.map { it.toTrackDomainModel() },
    )

fun PlaylistTrack.toTrackDomainModel(): TrackDomainModel =
    TrackDomainModel(
        artists = track?.artists?.map { it.toArtistDomainModel() },
        externalUrl = track?.externalUrls?.spotify,
        id = track?.id,
        image =
            track
                ?.album
                ?.images
                ?.firstOrNull()
                ?.url,
        name = track?.name,
        mediaUrl = track?.previewUrl,
    )

fun PlaylistDto.toPlaylistDomainModel(): PlaylistDomainModel =
    PlaylistDomainModel(
        description = description,
        externalUrl = externalUrls?.spotify,
        id = id,
        image = images?.firstOrNull()?.url,
        isLocal = false,
        name = name,
        owner = owner?.displayName,
        ownerId = owner?.id,
        tracks = tracks?.items?.map { it.toTrackDomainModel() },
    )

fun PlaylistsDto.toPlaylistDomainModelList(): List<PlaylistDomainModel> = playlists?.items?.map { it.toPlaylistDomainModel() }.orEmpty()

fun PlaylistsDto.toPlaylistsDomainModel(): PlaylistsDomainModel? =
    playlists?.run {
        PlaylistsDomainModel(
            limit = limit,
            offset = offset,
            total = total,
            items = items?.map { it.toPlaylistDomainModel() },
        )
    }

fun Playlists.toPlaylistsDomainModel(): PlaylistsDomainModel =
    PlaylistsDomainModel(
        limit = limit,
        offset = offset,
        total = total,
        items = items?.map { it.toPlaylistDomainModel() },
    )
