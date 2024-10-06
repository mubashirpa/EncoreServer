package com.encore.music.data.service

import com.encore.music.core.Spotify
import com.encore.music.data.remote.dto.spotify.token.AccessTokenDto
import com.encore.music.domain.service.SpotifyTokenService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import java.util.concurrent.atomic.AtomicReference

class SpotifyTokenServiceImpl(
    private val httpClient: HttpClient,
    private val clientId: String,
    private val clientSecret: String,
) : SpotifyTokenService {
    private var token: AtomicReference<String?> = AtomicReference(null)
    private var tokenExpirationTime: Long = 0L

    override suspend fun getAccessToken(): String {
        val currentTime = System.currentTimeMillis()

        return if (token.get() == null || currentTime >= tokenExpirationTime) {
            requestAccessToken()
        } else {
            token.get()!!
        }
    }

    override suspend fun requestAccessToken(): String {
        val accessTokenDto =
            httpClient
                .post(Spotify.API_TOKEN_URL) {
                    contentType(ContentType.Application.FormUrlEncoded)
                    setBody(
                        FormDataContent(
                            Parameters.build {
                                append(Spotify.Parameters.GRANT_TYPE, Spotify.Parameters.CLIENT_CREDENTIALS)
                                append(Spotify.Parameters.CLIENT_ID, clientId)
                                append(Spotify.Parameters.CLIENT_SECRET, clientSecret)
                            },
                        ),
                    )
                }.body<AccessTokenDto>()
        val currentTime = System.currentTimeMillis()

        token.set(accessTokenDto.accessToken)
        tokenExpirationTime = currentTime + ((accessTokenDto.expiresIn ?: 0) * 1000)

        return accessTokenDto.accessToken.orEmpty()
    }
}
