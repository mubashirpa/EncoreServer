package com.encore.music.data.remote.dto.spotify.show

import com.encore.music.data.remote.dto.spotify.Copyright
import com.encore.music.data.remote.dto.spotify.ExternalUrls
import com.encore.music.data.remote.dto.spotify.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowDto(
    @SerialName("available_markets")
    val availableMarkets: List<String>? = null,
    val copyrights: Copyright? = null,
    val description: String? = null,
    @SerialName("html_description")
    val htmlDescription: String? = null,
    val explicit: Boolean? = null,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val href: String? = null,
    val id: String? = null,
    val images: List<Image>? = null,
    @SerialName("is_externally_hosted")
    val isExternallyHosted: Boolean? = null,
    val languages: List<String>? = null,
    @SerialName("media_type")
    val mediaType: String? = null,
    val name: String? = null,
    val publisher: String? = null,
    val type: String? = null,
    val uri: String? = null,
    @SerialName("total_episodes")
    val totalEpisodes: Int? = 0,
    val episodes: EpisodesShow? = null,
)
