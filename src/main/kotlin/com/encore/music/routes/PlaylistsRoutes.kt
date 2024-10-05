package com.encore.music.routes

import com.encore.music.core.AuthorizationException
import com.encore.music.core.Constants
import com.encore.music.core.Spotify
import com.encore.music.core.mapper.toPlaylistDomainModel
import com.encore.music.core.mapper.toTrackDomainModelList
import com.encore.music.core.utils.FirebaseUtils
import com.encore.music.domain.model.home.HomePlaylist
import com.encore.music.domain.repository.SpotifyRepository
import com.encore.music.domain.service.SpotifyTokenService
import com.google.firebase.auth.FirebaseAuthException
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.playlistsRoutes(
    spotifyTokenService: SpotifyTokenService,
    spotifyRepository: SpotifyRepository,
) {
    route(Constants.Routes.CATEGORY_PLAYLISTS) {
        get {
            try {
                authorizeRequest()
            } catch (e: AuthorizationException) {
                return@get call.respondText(text = e.message ?: "Unauthorized", status = e.status)
            }

            val accessToken = spotifyTokenService.getAccessToken()
            val featuredPlaylists =
                spotifyRepository
                    .getFeaturedPlaylists(
                        accessToken = accessToken,
                    )

            call.respond(featuredPlaylists)
        }
    }
    route(Constants.Routes.HOME_PLAYLISTS) {
        get {
            try {
                authorizeRequest()
            } catch (e: AuthorizationException) {
                return@get call.respondText(text = e.message ?: "Unauthorized", status = e.status)
            }

            val accessToken = spotifyTokenService.getAccessToken()
            val featuredPlaylists =
                spotifyRepository
                    .getFeaturedPlaylists(accessToken)
            val trendingPlaylists =
                spotifyRepository
                    .getCategoryPlaylists(
                        accessToken = accessToken,
                        categoryId = Spotify.Categories.TRENDING,
                    )
            val newReleasesPlaylists =
                spotifyRepository
                    .getCategoryPlaylists(
                        accessToken = accessToken,
                        categoryId = Spotify.Categories.NEW_RELEASES,
                    )
            val chartsPlaylists =
                spotifyRepository
                    .getCategoryPlaylists(
                        accessToken = accessToken,
                        categoryId = Spotify.Categories.CHARTS,
                    )

            val homePlaylists: MutableList<HomePlaylist> = mutableListOf()
//            homePlaylists.add(HomePlaylist(title = "Popular", playlists = featuredPlaylists))
//            homePlaylists.add(HomePlaylist(title = "Trending", playlists = trendingPlaylists))
//            homePlaylists.add(HomePlaylist(title = "New Releases", playlists = newReleasesPlaylists))
//            homePlaylists.add(HomePlaylist(title = "Top Charts", playlists = chartsPlaylists))

            call.respond(homePlaylists)
        }
    }

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
}

private fun RoutingContext.authorizeRequest() {
    val token =
        call.request.header(HttpHeaders.Authorization)
            ?: throw AuthorizationException(message = "No token provided", status = HttpStatusCode.Unauthorized)

    if (token.isBlank()) {
        throw AuthorizationException(
            message = "Only valid authentication supported",
            status = HttpStatusCode.BadRequest,
        )
    }

    try {
        FirebaseUtils.getUserIdFromToken(token)
    } catch (e: FirebaseAuthException) {
        when (e.authErrorCode.name) {
            "EXPIRED_ID_TOKEN" -> throw AuthorizationException(
                message = "The access token is expired",
                status = HttpStatusCode.Unauthorized,
            )

            "REVOKED_ID_TOKEN" -> throw AuthorizationException(
                message = "The access token has been revoked",
                status = HttpStatusCode.Unauthorized,
            )

            else -> throw AuthorizationException(
                message = "The provided access token is not valid",
                status = HttpStatusCode.Unauthorized,
            )
        }
    }
}
