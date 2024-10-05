package com.encore.music.domain.model.categories

import kotlinx.serialization.Serializable

@Serializable
data class Categories(
    val limit: Int? = null,
    val offset: Int? = null,
    val total: Int? = null,
    val items: List<Category>? = null,
)
