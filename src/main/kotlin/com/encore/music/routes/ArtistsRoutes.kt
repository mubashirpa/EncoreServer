package com.encore.music.routes

import com.encore.music.core.mapper.toArtistDomainModel
import com.encore.music.core.mapper.toTrackDomainModelList
import com.encore.music.domain.repository.SpotifyRepository
import com.encore.music.domain.service.SpotifyTokenService
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.artistsRoutes(
    spotifyTokenService: SpotifyTokenService,
    spotifyRepository: SpotifyRepository,
) {
    route("/v1/artists") {
        route("/{artist_id}") {
            get {
                val artistId =
                    call.parameters["artist_id"] ?: return@get call.respondText(
                        text = "You must specify a artist id",
                        status = HttpStatusCode.BadRequest,
                    )
                val accessToken = spotifyTokenService.getAccessToken()

                try {
                    val artist = spotifyRepository.getArtist(accessToken, artistId).toArtistDomainModel()
                    call.respond(artist)
                } catch (e: Exception) {
                    e.printStackTrace()
                    call.respondText(
                        text = "An error occurred while processing your request. Please try again later or contact support if the issue persists.",
                        status = HttpStatusCode.InternalServerError,
                    )
                }
            }
            get("/top-tracks") {
                val artistId =
                    call.parameters["artist_id"] ?: return@get call.respondText(
                        text = "You must specify a artist id",
                        status = HttpStatusCode.BadRequest,
                    )
                val accessToken = spotifyTokenService.getAccessToken()

                try {
                    val artistTopTracks =
                        spotifyRepository.getArtistTopTracks(accessToken, artistId).toTrackDomainModelList()
                    val artist = spotifyRepository.getArtist(accessToken, artistId).toArtistDomainModel(artistTopTracks)
                    call.respond(artist)
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
}
