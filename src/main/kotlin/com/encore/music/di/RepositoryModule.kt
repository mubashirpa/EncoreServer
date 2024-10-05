package com.encore.music.di

import com.encore.music.data.repository.SpotifyRepositoryImpl
import com.encore.music.data.service.SpotifyTokenServiceImpl
import com.encore.music.domain.repository.SpotifyRepository
import com.encore.music.domain.service.SpotifyTokenService
import io.ktor.server.application.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun repositoryModule(application: Application) =
    module {
        singleOf(::SpotifyRepositoryImpl) bind SpotifyRepository::class
        single<SpotifyTokenService> {
            val clientId =
                application.environment.config
                    .propertyOrNull("spotify.client.id")
                    ?.getString()
                    ?: throw IllegalStateException("Spotify client id not found")
            val clientSecret =
                application.environment.config
                    .propertyOrNull("spotify.client.secret")
                    ?.getString()
                    ?: throw IllegalStateException("Spotify client secret not found")

            SpotifyTokenServiceImpl(
                httpClient = get(),
                clientId = clientId,
                clientSecret = clientSecret,
            )
        }
    }
