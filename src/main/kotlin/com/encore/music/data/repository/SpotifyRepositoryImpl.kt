package com.encore.music.data.repository

import com.encore.music.core.Spotify
import com.encore.music.data.remote.dto.spotify.artist.ArtistDto
import com.encore.music.data.remote.dto.spotify.artist.ArtistTracksDto
import com.encore.music.data.remote.dto.spotify.categories.CategoriesDto
import com.encore.music.data.remote.dto.spotify.playlist.PlaylistDto
import com.encore.music.data.remote.dto.spotify.playlist.TracksPlaylist
import com.encore.music.data.remote.dto.spotify.playlists.PlaylistsDto
import com.encore.music.data.remote.dto.spotify.search.SearchDto
import com.encore.music.domain.model.search.IncludeExternal
import com.encore.music.domain.model.search.SearchType
import com.encore.music.domain.repository.SpotifyRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class SpotifyRepositoryImpl(
    private val httpClient: HttpClient,
) : SpotifyRepository {
    override suspend fun getCategories(
        accessToken: String,
        locale: String?,
        limit: Int,
        offset: Int,
    ): CategoriesDto =
        httpClient
            .get(Spotify.API_BASE_URL) {
                url {
                    appendPathSegments(Spotify.ENDPOINT_GET_CATEGORIES)
                    locale?.let { parameters.append(Spotify.Parameters.LOCALE, it) }
                    parameters.append(Spotify.Parameters.LIMIT, limit.toString())
                    parameters.append(Spotify.Parameters.OFFSET, offset.toString())
                }
                authorisationHeader(accessToken)
            }.body()

    override suspend fun getFeaturedPlaylists(
        accessToken: String,
        locale: String?,
        limit: Int,
        offset: Int,
    ): PlaylistsDto =
        httpClient
            .get(Spotify.API_BASE_URL) {
                url {
                    appendPathSegments(Spotify.ENDPOINT_GET_FEATURED_PLAYLISTS)
                    locale?.let { parameters.append(Spotify.Parameters.LOCALE, it) }
                    parameters.append(Spotify.Parameters.LIMIT, limit.toString())
                    parameters.append(Spotify.Parameters.OFFSET, offset.toString())
                }
                authorisationHeader(accessToken)
            }.body()

    override suspend fun getCategoryPlaylists(
        accessToken: String,
        categoryId: String,
        limit: Int,
        offset: Int,
    ): PlaylistsDto =
        httpClient
            .get(Spotify.API_BASE_URL) {
                url {
                    appendPathSegments(Spotify.ENDPOINT_GET_CATEGORY_PLAYLISTS.replace("{category_id}", categoryId))
                    parameters.append(Spotify.Parameters.LIMIT, limit.toString())
                    parameters.append(Spotify.Parameters.OFFSET, offset.toString())
                }
                authorisationHeader(accessToken)
            }.body()

    override suspend fun getPlaylist(
        accessToken: String,
        playlistId: String,
        market: String?,
        fields: String?,
        additionalTypes: String?,
    ): PlaylistDto =
        httpClient
            .get(Spotify.API_BASE_URL) {
                url {
                    appendPathSegments(Spotify.ENDPOINT_GET_PLAYLIST.replace("{playlist_id}", playlistId))
                    market?.let { parameters.append(Spotify.Parameters.MARKET, it) }
                    fields?.let { parameters.append(Spotify.Parameters.FIELDS, it) }
                    additionalTypes?.let { parameters.append(Spotify.Parameters.ADDITIONAL_TYPES, it) }
                }
                authorisationHeader(accessToken)
            }.body()

    override suspend fun getPlaylistItems(
        accessToken: String,
        playlistId: String,
        market: String?,
        fields: String?,
        limit: Int,
        offset: Int,
        additionalTypes: String?,
    ): TracksPlaylist =
        httpClient
            .get(Spotify.API_BASE_URL) {
                url {
                    appendPathSegments(Spotify.ENDPOINT_GET_PLAYLIST_ITEMS.replace("{playlist_id}", playlistId))
                    market?.let { parameters.append(Spotify.Parameters.MARKET, it) }
                    fields?.let { parameters.append(Spotify.Parameters.FIELDS, it) }
                    parameters.append(Spotify.Parameters.LIMIT, limit.toString())
                    parameters.append(Spotify.Parameters.OFFSET, offset.toString())
                    additionalTypes?.let { parameters.append(Spotify.Parameters.ADDITIONAL_TYPES, it) }
                }
                authorisationHeader(accessToken)
            }.body()

    override suspend fun getArtist(
        accessToken: String,
        artistId: String,
    ): ArtistDto =
        httpClient
            .get(Spotify.API_BASE_URL) {
                url {
                    appendPathSegments(Spotify.ENDPOINT_GET_ARTIST.replace("{artist_id}", artistId))
                }
                authorisationHeader(accessToken)
            }.body()

    override suspend fun getArtistTopTracks(
        accessToken: String,
        artistId: String,
        market: String?,
    ): ArtistTracksDto =
        httpClient
            .get(Spotify.API_BASE_URL) {
                url {
                    appendPathSegments(Spotify.ENDPOINT_GET_ARTIST_TOP_TRACKS.replace("{artist_id}", artistId))
                    market?.let { parameters.append(Spotify.Parameters.MARKET, it) }
                }
                authorisationHeader(accessToken)
            }.body()

    override suspend fun search(
        accessToken: String,
        query: String,
        type: List<SearchType>,
        market: String?,
        limit: Int,
        offset: Int,
        includeExternal: IncludeExternal?,
    ): SearchDto =
        httpClient
            .get(Spotify.API_BASE_URL) {
                url {
                    appendPathSegments(Spotify.ENDPOINT_GET_SEARCH)
                    parameters.append(Spotify.Parameters.QUERY, query)
                    parameters.append(
                        Spotify.Parameters.TYPE,
                        type.joinToString(",") { it.name.lowercase() },
                    )
                    market?.let { parameters.append(Spotify.Parameters.MARKET, it) }
                    parameters.append(Spotify.Parameters.LIMIT, limit.toString())
                    parameters.append(Spotify.Parameters.OFFSET, offset.toString())
                    includeExternal?.let { parameters.append(Spotify.Parameters.INCLUDE_EXTERNAL, it.name.lowercase()) }
                }
                authorisationHeader(accessToken)
            }.body()

    private fun HttpRequestBuilder.authorisationHeader(accessToken: String) = header(HttpHeaders.Authorization, "Bearer $accessToken")
}
