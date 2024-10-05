package com.encore.music.core.mapper

import com.encore.music.data.remote.dto.spotify.category.CategoryDto
import com.encore.music.domain.model.categories.Category

fun CategoryDto.toCategory(): Category =
    Category(
        icon = icons?.firstOrNull()?.url,
        id = id,
        name = name,
        playlists = null,
    )
