package com.encore.music.core.mapper

import com.encore.music.data.remote.dto.spotify.playlist.PlaylistDto
import com.encore.music.data.remote.dto.spotify.playlist.PlaylistTrack
import com.encore.music.data.remote.dto.spotify.playlist.TracksPlaylist
import com.encore.music.data.remote.dto.spotify.playlists.PlaylistsDto
import com.encore.music.domain.model.playlists.Playlist as PlaylistDomainModel
import com.encore.music.domain.model.tracks.Track as TrackDomainModel

fun TracksPlaylist.toTrackDomainModelList(): List<TrackDomainModel> = items?.map { it.toTrackDomainModel() }.orEmpty()

fun PlaylistTrack.toTrackDomainModel(): TrackDomainModel =
    TrackDomainModel(
        artists = track?.artists?.map { it.toArtist() },
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
        id = id,
        image = images?.firstOrNull()?.url,
        name = name,
        owner = owner?.displayName,
        tracks = tracks?.items?.map { it.toTrackDomainModel() },
    )

fun PlaylistsDto.toPlaylistDomainModelList(): List<PlaylistDomainModel> = playlists?.items?.map { it.toPlaylistDomainModel() }.orEmpty()