// https://firebase.google.com/docs/admin/setup#java

package com.encore.music.plugins

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.*

fun Application.configureFirebase() {
    val classLoader = this::class.java.classLoader
    val refreshToken = classLoader.getResourceAsStream("encore-music-player-firebase-adminsdk.json")

    val options =
        FirebaseOptions
            .builder()
            .setCredentials(GoogleCredentials.fromStream(refreshToken))
            .build()

    FirebaseApp.initializeApp(options)
}
