package com.encore.music.domain.service

interface SpotifyTokenService {
    suspend fun getAccessToken(): String

    suspend fun requestAccessToken(): String
}
