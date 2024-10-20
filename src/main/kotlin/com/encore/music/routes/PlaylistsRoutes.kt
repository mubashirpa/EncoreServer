package com.encore.music.routes

import com.encore.music.core.AuthorizationException
import com.encore.music.core.Spotify
import com.encore.music.core.mapper.toPlaylistDomainModel
import com.encore.music.core.mapper.toPlaylistDomainModelList
import com.encore.music.core.mapper.toTracksDomainModel
import com.encore.music.core.utils.authorizeRequest
import com.encore.music.domain.model.home.HomePlaylist
import com.encore.music.domain.repository.SpotifyRepository
import com.encore.music.domain.service.SpotifyTokenService
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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

            try {
                val featuredPlaylists =
                    spotifyRepository
                        .getFeaturedPlaylists(accessToken, locale, limit, offset)
                        .toPlaylistDomainModelList()
                val trendingPlaylists =
                    spotifyRepository
                        .getCategoryPlaylists(accessToken, Spotify.Categories.TRENDING, limit, offset)
                        .toPlaylistDomainModelList()
                val partyPlaylists =
                    spotifyRepository
                        .getCategoryPlaylists(accessToken, Spotify.Categories.PARTY, limit, offset)
                        .toPlaylistDomainModelList()
                val chartsPlaylists =
                    spotifyRepository
                        .getCategoryPlaylists(accessToken, Spotify.Categories.CHARTS, limit, offset)
                        .toPlaylistDomainModelList()

                val homePlaylists: MutableList<HomePlaylist> = mutableListOf()
                homePlaylists.add(HomePlaylist(title = "Popular", playlists = featuredPlaylists))
                homePlaylists.add(HomePlaylist(title = "Trending", playlists = trendingPlaylists))
                homePlaylists.add(HomePlaylist(title = "Party", playlists = partyPlaylists))
                homePlaylists.add(HomePlaylist(title = "Top Charts", playlists = chartsPlaylists))

                call.respond(homePlaylists)
            } catch (e: Exception) {
                e.printStackTrace()
                call.respondText(
                    text = "An error occurred while processing your request. Please try again later or contact support if the issue persists.",
                    status = HttpStatusCode.InternalServerError,
                )
            }
        }
    }
}
