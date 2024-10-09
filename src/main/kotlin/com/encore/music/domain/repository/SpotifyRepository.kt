package com.encore.music.domain.repository

import com.encore.music.data.remote.dto.spotify.artist.ArtistDto
import com.encore.music.data.remote.dto.spotify.artist.ArtistTracksDto
import com.encore.music.data.remote.dto.spotify.categories.CategoriesDto
import com.encore.music.data.remote.dto.spotify.playlist.PlaylistDto
import com.encore.music.data.remote.dto.spotify.playlist.TracksPlaylist
import com.encore.music.data.remote.dto.spotify.playlists.PlaylistsDto

interface SpotifyRepository {
    suspend fun getCategories(
        accessToken: String,
        locale: String? = null,
        limit: Int = 20,
        offset: Int = 0,
    ): CategoriesDto

    suspend fun getFeaturedPlaylists(
        accessToken: String,
        locale: String? = null,
        limit: Int = 20,
        offset: Int = 0,
    ): PlaylistsDto

    suspend fun getCategoryPlaylists(
        accessToken: String,
        categoryId: String,
        limit: Int = 20,
        offset: Int = 0,
    ): PlaylistsDto

    suspend fun getPlaylist(
        accessToken: String,
        playlistId: String,
        market: String? = null,
        fields: String? = null,
        additionalTypes: String? = null,
    ): PlaylistDto

    suspend fun getPlaylistItems(
        accessToken: String,
        playlistId: String,
        market: String? = null,
        fields: String? = null,
        limit: Int = 20,
        offset: Int = 0,
        additionalTypes: String? = null,
    ): TracksPlaylist

    suspend fun getArtist(
        accessToken: String,
        artistId: String,
    ): ArtistDto

    suspend fun getArtistTopTracks(
        accessToken: String,
        artistId: String,
        market: String? = null,
    ): ArtistTracksDto
}
