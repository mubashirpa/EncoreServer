package com.encore.music.data.repository

import com.encore.music.core.Spotify
import com.encore.music.data.remote.dto.spotify.categories.CategoriesDto
import com.encore.music.data.remote.dto.spotify.playlist.PlaylistDto
import com.encore.music.data.remote.dto.spotify.playlist.TracksPlaylist
import com.encore.music.data.remote.dto.spotify.playlists.PlaylistsDto
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

    private fun HttpRequestBuilder.authorisationHeader(accessToken: String) = header(HttpHeaders.Authorization, "Bearer $accessToken")
}