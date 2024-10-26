package com.encore.music.routes

import com.encore.music.core.AuthorizationException
import com.encore.music.core.Spotify
import com.encore.music.core.mapper.toPlaylistDomainModel
import com.encore.music.core.mapper.toPlaylistDomainModelList
import com.encore.music.core.mapper.toTracksDomainModel
import com.encore.music.core.utils.authorizeRequest
import com.encore.music.domain.model.home.HomePlaylist
import com.encore.music.domain.model.playlists.Playlist
import com.encore.music.domain.repository.SpotifyRepository
import com.encore.music.domain.service.SpotifyTokenService
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

fun Route.playlistsRoutes(
    spotifyTokenService: SpotifyTokenService,
    spotifyRepository: SpotifyRepository,
) {
    route("/v1/playlists/{playlist_id}") {
        get {
            try {
                authorizeRequest()
            } catch (e: AuthorizationException) {
                return@get call.respondText(
                    text = e.message.orEmpty(),
                    status = e.status,
                )
            }

            val playlistId =
                call.parameters["playlist_id"] ?: return@get call.respondText(
                    text = "You must specify a playlist id",
                    status = HttpStatusCode.BadRequest,
                )
            val market = call.parameters["market"]
            val fields =
                "description,id,images.url,name,owner(id,display_name),tracks.items(track(id,name,preview_url,album.images(url),artists(id,name)))"
            val additionalTypes = call.parameters["additional_types"]
            val accessToken = spotifyTokenService.getAccessToken()

            try {
                val playlist =
                    spotifyRepository
                        .getPlaylist(accessToken, playlistId, market, fields, additionalTypes)
                        .toPlaylistDomainModel()
                call.respond(playlist)
            } catch (e: Exception) {
                e.printStackTrace()
                call.respondText(
                    text = "An error occurred while processing your request. Please try again later or contact support if the issue persists.",
                    status = HttpStatusCode.InternalServerError,
                )
            }
        }
        get("/tracks") {
            try {
                authorizeRequest()
            } catch (e: AuthorizationException) {
                return@get call.respondText(
                    text = e.message.orEmpty(),
                    status = e.status,
                )
            }

            val playlistId =
                call.parameters["playlist_id"] ?: return@get call.respondText(
                    text = "You must specify a playlist id",
                    status = HttpStatusCode.BadRequest,
                )
            val market = call.parameters["market"]
            val fields = "limit,offset,total,items(track(id,name,preview_url,album.images(url),artists(id,name)))"
            val limit = call.parameters["limit"]?.toIntOrNull() ?: 20
            val offset = call.parameters["offset"]?.toIntOrNull() ?: 0
            val additionalTypes = call.parameters["additional_types"]
            val accessToken = spotifyTokenService.getAccessToken()

            try {
                val playlistItems =
                    spotifyRepository
                        .getPlaylistItems(
                            accessToken,
                            playlistId,
                            market,
                            fields,
                            limit,
                            offset,
                            additionalTypes,
                        ).toTracksDomainModel()
                call.respond(playlistItems)
            } catch (e: Exception) {
                e.printStackTrace()
                call.respondText(
                    text = "An error occurred while processing your request. Please try again later or contact support if the issue persists.",
                    status = HttpStatusCode.InternalServerError,
                )
            }
        }
    }
    route("/v1/home-playlists") {
        get {
            try {
                authorizeRequest()
            } catch (e: AuthorizationException) {
                return@get call.respondText(
                    text = e.message.orEmpty(),
                    status = e.status,
                )
            }

            val locale = call.parameters["locale"]
            val limit = call.parameters["limit"]?.toIntOrNull() ?: 20
            val offset = call.parameters["offset"]?.toIntOrNull() ?: 0
            val accessToken = spotifyTokenService.getAccessToken()

            suspend fun fetchPlaylists(category: String): List<Playlist> =
                try {
                    spotifyRepository
                        .getCategoryPlaylists(accessToken, category, limit, offset)
                        .toPlaylistDomainModelList()
                } catch (e: Exception) {
                    emptyList()
                }

            val homePlaylists =
                coroutineScope {
                    listOf(
                        async {
                            spotifyRepository
                                .getFeaturedPlaylists(accessToken, locale, limit, offset)
                                .toPlaylistDomainModelList()
                        },
                        async { fetchPlaylists(Spotify.Categories.TRENDING) },
                        async { fetchPlaylists(Spotify.Categories.PARTY) },
                        async { fetchPlaylists(Spotify.Categories.CHARTS) },
                    ).awaitAll()
                }.mapIndexed { index, playlists ->
                    HomePlaylist(title = getTitleByIndex(index), playlists = playlists)
                }
            call.respond(homePlaylists)
        }
    }
}

private fun getTitleByIndex(index: Int): String =
    when (index) {
        0 -> "Popular"
        1 -> "Trending"
        2 -> "Party"
        3 -> "Top Charts"
        else -> "Unknown"
    }
