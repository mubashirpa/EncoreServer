package com.encore.music.core

import io.ktor.http.*

class AuthorizationException(
    message: String,
    val status: HttpStatusCode,
) : RuntimeException(message)
