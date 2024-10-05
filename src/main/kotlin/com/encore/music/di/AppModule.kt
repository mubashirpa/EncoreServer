package com.encore.music.di

import io.ktor.server.application.*
import org.koin.dsl.module

fun appModule(application: Application) =
    module {
        includes(
            ktorModule,
            repositoryModule(application),
        )
    }
