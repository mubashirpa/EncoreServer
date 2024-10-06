package com.encore.music.core.mapper

import com.encore.music.data.remote.dto.spotify.categories.CategoriesDto
import com.encore.music.data.remote.dto.spotify.category.CategoryDto
import com.encore.music.domain.model.categories.Categories as CategoriesDomainModel
import com.encore.music.domain.model.categories.Category as CategoryDomainModel

fun CategoriesDto.toCategoriesDomainModel(): CategoriesDomainModel =
    CategoriesDomainModel(
        limit = categories?.limit,
        offset = categories?.offset,
        total = categories?.total,
        items = categories?.items?.map { it.toCategoryDomainModel() },
    )

fun CategoryDto.toCategoryDomainModel(): CategoryDomainModel =
    CategoryDomainModel(
        icon = icons?.firstOrNull()?.url,
        id = id,
        name = name,
        playlists = null,
    )
