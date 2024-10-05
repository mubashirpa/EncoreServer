package com.encore.music.data.remote.dto.spotify.category

import com.encore.music.data.remote.dto.spotify.Image
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val href: String? = null,
    val icons: List<Image>? = null,
    val id: String? = null,
    val name: String? = null,
)
