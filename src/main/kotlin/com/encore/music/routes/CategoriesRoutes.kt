package com.encore.music.routes

import com.encore.music.core.mapper.toCategoriesDomainModel
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
            val accessToken = spotifyTokenService.getAccessToken()

            try {
                val categories = spotifyRepository.getCategories(accessToken).toCategoriesDomainModel()
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
            val categoryId =
                call.parameters["category_id"] ?: return@get call.respondText(
                    text = "You must specify a category id",
                    status = HttpStatusCode.BadRequest,
                )
            val accessToken = spotifyTokenService.getAccessToken()
            val categoryPlaylists =
                spotifyRepository
                    .getCategoryPlaylists(
                        accessToken = accessToken,
                        categoryId = categoryId,
                    )

            call.respond(categoryPlaylists)
        }
    }
}