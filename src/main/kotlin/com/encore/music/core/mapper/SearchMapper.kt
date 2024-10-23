package com.encore.music.core.mapper

import com.encore.music.data.remote.dto.spotify.search.SearchDto
import com.encore.music.domain.model.search.Search as SearchDomainModel

fun SearchDto.toSearchDomainModel(): SearchDomainModel =
    SearchDomainModel(
        tracks = tracks?.toTracksDomainModel(),
        artists = artists?.toArtistsDomainModel(),
        playlists = playlists?.toPlaylistsDomainModel(),
    )
