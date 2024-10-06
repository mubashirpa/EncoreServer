package com.encore.music.routes

import com.encore.music.core.Spotify
import com.encore.music.core.mapper.toPlaylistDomainModel
import com.encore.music.core.mapper.toPlaylistDomainModelList
import com.encore.music.core.mapper.toTrackDomainModelList
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
            val playlistId =
                call.parameters["playlist_id"] ?: return@get call.respondText(
                    text = "You must specify a playlist id",
                    status = HttpStatusCode.BadRequest,
                )
            val accessToken = spotifyTokenService.getAccessToken()

            try {
                val playlist = spotifyRepository.getPlaylist(accessToken, playlistId).toPlaylistDomainModel()
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
            val playlistId =
                call.parameters["playlist_id"] ?: return@get call.respondText(
                    text = "You must specify a playlist id",
                    status = HttpStatusCode.BadRequest,
                )
            val accessToken = spotifyTokenService.getAccessToken()

            try {
                val playlistItems =
                    spotifyRepository.getPlaylistItems(accessToken, playlistId).toTrackDomainModelList()
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
            val accessToken = spotifyTokenService.getAccessToken()

            try {
                val featuredPlaylists =
                    spotifyRepository
                        .getFeaturedPlaylists(accessToken)
                        .toPlaylistDomainModelList()
                val trendingPlaylists =
                    spotifyRepository
                        .getCategoryPlaylists(
                            accessToken = accessToken,
                            categoryId = Spotify.Categories.TRENDING,
                        ).toPlaylistDomainModelList()
                val partyPlaylists =
                    spotifyRepository
                        .getCategoryPlaylists(
                            accessToken = accessToken,
                            categoryId = Spotify.Categories.PARTY,
                        ).toPlaylistDomainModelList()
                val chartsPlaylists =
                    spotifyRepository
                        .getCategoryPlaylists(
                            accessToken = accessToken,
                            categoryId = Spotify.Categories.CHARTS,
                        ).toPlaylistDomainModelList()

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
