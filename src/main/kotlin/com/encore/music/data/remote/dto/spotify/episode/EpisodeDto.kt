package com.encore.music.data.remote.dto.spotify.episode

import com.encore.music.data.remote.dto.spotify.ExternalUrls
import com.encore.music.data.remote.dto.spotify.Image
import com.encore.music.data.remote.dto.spotify.Restrictions
import com.encore.music.data.remote.dto.spotify.show.ShowDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeDto(
    @SerialName("audio_preview_url")
    val audioPreviewUrl: String? = null,
    val description: String? = null,
    @SerialName("html_description")
    val htmlDescription: String? = null,
    @SerialName("duration_ms")
    val durationMs: Int? = null,
    val explicit: Boolean? = null,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val href: String? = null,
    val id: String? = null,
    val images: List<Image>? = null,
    @SerialName("is_externally_hosted")
    val isExternallyHosted: Boolean? = null,
    @SerialName("is_playable")
    val isPlayable: Boolean? = null,
    val languages: List<String>? = null,
    val name: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("release_date_precision")
    val releaseDatePrecision: String? = null,
    @SerialName("resume_point")
    val resumePoint: ResumePoint? = null,
    val type: String? = null,
    val uri: String? = null,
    val restrictions: Restrictions? = null,
    val show: ShowDto? = null, // SimplifiedShow
)
