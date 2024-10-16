package com.encore.music.routes

import com.encore.music.core.AuthorizationException
import com.encore.music.core.mapper.toCategoriesDomainModel
import com.encore.music.core.mapper.toPlaylistsDomainModel
import com.encore.music.core.utils.authorizeRequest
import com.encore.music.domain.repository.SpotifyRepository
import com.encore.music.domain.service.SpotifyTokenService
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoriesRoutes(
    spotifyTokenService: SpotifyTokenService,
    spotifyRepository: SpotifyRepository,
) {
    route("/v1/categories") {
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
                val categories =
                    spotifyRepository.getCategories(accessToken, locale, limit, offset).toCategoriesDomainModel()
                call.respond(categories)
            } catch (e: Exception) {
                e.printStackTrace()
                call.respondText(
                    text = "An error occurred while processing your request. Please try again later or contact support if the issue persists.",
                    status = HttpStatusCode.InternalServerError,
                )
            }
        }
        get("/{category_id}/playlists") {
            try {
                authorizeRequest()
            } catch (e: AuthorizationException) {
                return@get call.respondText(
                    text = e.message.orEmpty(),
                    status = e.status,
                )
            }

            val categoryId =
                call.parameters["category_id"] ?: return@get call.respondText(
                    text = "You must specify a category id",
                    status = HttpStatusCode.BadRequest,
                )
            val limit = call.parameters["limit"]?.toIntOrNull() ?: 20
            val offset = call.parameters["offset"]?.toIntOrNull() ?: 0
            val accessToken = spotifyTokenService.getAccessToken()

            try {
                val categoryPlaylists =
                    spotifyRepository
                        .getCategoryPlaylists(accessToken, categoryId, limit, offset)
                        .toPlaylistsDomainModel()
                call.respond(categoryPlaylists!!)
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
