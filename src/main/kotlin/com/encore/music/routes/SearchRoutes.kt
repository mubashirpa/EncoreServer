package com.encore.music.routes

import com.encore.music.core.mapper.toSearchDomainModel
import com.encore.music.domain.model.search.IncludeExternal
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
            val accessToken = spotifyTokenService.getAccessToken()

            val market = call.parameters["market"]
            val limit =
                try {
                    call.parameters["limit"]?.toInt()
                } catch (e: NumberFormatException) {
                    20
                } ?: 20
            val offset =
                try {
                    call.parameters["offset"]?.toInt()
                } catch (e: NumberFormatException) {
                    0
                } ?: 0
            val includeExternal =
                call.parameters["include_external"]?.let {
                    enumValueOf<IncludeExternal>(it.uppercase())
                }

            try {
                val search =
                    spotifyRepository
                        .search(
                            accessToken = accessToken,
                            query = query,
                            type = type.map { enumValueOf(it.uppercase()) },
                            market = market,
                            limit = limit,
                            offset = offset,
                            includeExternal = includeExternal,
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
