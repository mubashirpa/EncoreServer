package com.encore.music.routes

import com.encore.music.core.AuthorizationException
import com.encore.music.core.mapper.toSearchDomainModel
import com.encore.music.core.utils.authorizeRequest
import com.encore.music.domain.repository.SpotifyRepository
import com.encore.music.domain.service.SpotifyTokenService
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.searchRoutes(
    spotifyTokenService: SpotifyTokenService,
    spotifyRepository: SpotifyRepository,
) {
    route("/v1/search") {
        get {
            try {
                authorizeRequest()
            } catch (e: AuthorizationException) {
                return@get call.respondText(
                    text = e.message.orEmpty(),
                    status = e.status,
                )
            }

            val query =
                call.parameters["query"] ?: return@get call.respondText(
                    text = "You must specify a query",
                    status = HttpStatusCode.BadRequest,
                )
            val type =
                call.parameters.getAll("type") ?: return@get call.respondText(
                    text = "You must specify a type",
                    status = HttpStatusCode.BadRequest,
                )
            val market = call.parameters["market"]
            val limit = call.parameters["limit"]?.toIntOrNull() ?: 20
            val offset = call.parameters["offset"]?.toIntOrNull() ?: 0
            val includeExternal = call.parameters["include_external"]
            val accessToken = spotifyTokenService.getAccessToken()

            try {
                val search =
                    spotifyRepository
                        .search(
                            accessToken,
                            query,
                            type.map { enumValueOf(it.uppercase()) },
                            market,
                            limit,
                            offset,
                            includeExternal,
                        ).toSearchDomainModel()
                call.respond(search)
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
