package com.encore.music

import com.encore.music.plugins.configureFirebase
import com.encore.music.plugins.configureKoin
import com.encore.music.plugins.configureRouting
import com.encore.music.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain
        .main(args)
}

fun Application.module() {
    configureKoin()
    configureSerialization()
    configureRouting()
    configureFirebase()
}
