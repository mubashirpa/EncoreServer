package com.encore.music.data.remote.dto.spotify.categories

import com.encore.music.data.remote.dto.spotify.category.CategoryDto
import kotlinx.serialization.Serializable

@Serializable
data class Categories(
    val href: String? = null,
    val limit: Int? = null,
    val next: String? = null,
    val offset: Int? = null,
    val previous: String? = null,
    val total: Int? = null,
    val items: List<CategoryDto>? = null,
)
