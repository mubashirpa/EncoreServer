package com.encore.music.core.utils

import com.encore.music.core.AuthorizationException
import com.google.firebase.auth.FirebaseAuthException
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun RoutingContext.authorizeRequest() {
    val token =
        call.request.header(HttpHeaders.Authorization)
            ?: throw AuthorizationException(message = "No token provided", status = HttpStatusCode.Unauthorized)

    if (token.isBlank()) {
        throw AuthorizationException(
            message = "Only valid authentication supported",
            status = HttpStatusCode.BadRequest,
        )
    }

    try {
        FirebaseUtils.getUserIdFromToken(token)
    } catch (e: FirebaseAuthException) {
        when (e.authErrorCode.name) {
            "EXPIRED_ID_TOKEN" -> throw AuthorizationException(
                message = "The access token is expired",
                status = HttpStatusCode.Unauthorized,
            )

            "REVOKED_ID_TOKEN" -> throw AuthorizationException(
                message = "The access token has been revoked",
                status = HttpStatusCode.Unauthorized,
            )

            else -> throw AuthorizationException(
                message = "The provided access token is not valid",
                status = HttpStatusCode.Unauthorized,
            )
        }
    }
}
