package com.encore.music.core.mapper

import com.encore.music.data.remote.dto.spotify.search.SearchDto
import com.encore.music.domain.model.search.Search as SearchDomainModel

fun SearchDto.toSearchDomainModel(): SearchDomainModel =
    SearchDomainModel(
        tracks = tracks?.items?.map { it.toTrackDomainModel() },
        artists = artists?.items?.map { it.toArtistDomainModel() },
        playlists = playlists?.items?.map { it.toPlaylistDomainModel() },
    )
