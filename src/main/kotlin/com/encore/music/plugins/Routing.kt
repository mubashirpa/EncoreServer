package com.encore.music.plugins

import com.encore.music.domain.repository.SpotifyRepository
import com.encore.music.domain.service.SpotifyTokenService
import com.encore.music.routes.artistsRoutes
import com.encore.music.routes.categoriesRoutes
import com.encore.music.routes.playlistsRoutes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val spotifyRepository by inject<SpotifyRepository>()
    val spotifyTokenService by inject<SpotifyTokenService>()

    routing {
        staticResources("static", "static")
        artistsRoutes(
            spotifyTokenService = spotifyTokenService,
            spotifyRepository = spotifyRepository,
        )
        categoriesRoutes(
            spotifyTokenService = spotifyTokenService,
            spotifyRepository = spotifyRepository,
        )
        playlistsRoutes(
            spotifyTokenService = spotifyTokenService,
            spotifyRepository = spotifyRepository,
        )
    }
}
