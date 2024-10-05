// https://firebase.google.com/docs/admin/setup#java

package com.encore.music.plugins

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.FileInputStream

fun configureFirebase() {
    val refreshToken = FileInputStream("src/main/resources/encore-music-player-firebase-adminsdk.json")

    val options =
        FirebaseOptions
            .builder()
            .setCredentials(GoogleCredentials.fromStream(refreshToken))
            .build()

    FirebaseApp.initializeApp(options)
}
