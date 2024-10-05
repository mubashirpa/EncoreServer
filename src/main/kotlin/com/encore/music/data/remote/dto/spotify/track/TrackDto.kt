package com.encore.music.data.remote.dto.spotify.track

import com.encore.music.data.remote.dto.spotify.ExternalIds
import com.encore.music.data.remote.dto.spotify.ExternalUrls
import com.encore.music.data.remote.dto.spotify.Restrictions
import com.encore.music.data.remote.dto.spotify.album.AlbumDto
import com.encore.music.data.remote.dto.spotify.artist.ArtistDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDto(
    val album: AlbumDto? = null, // SimplifiedAlbum
    val artists: List<ArtistDto>? = null, // SimplifiedArtist
    @SerialName("available_markets")
    val availableMarkets: List<String>? = null,
    @SerialName("disc_number")
    val discNumber: Int? = null,
    @SerialName("duration_ms")
    val durationMs: Int? = null,
    val explicit: Boolean? = null,
    @SerialName("external_ids")
    val externalIds: ExternalIds? = null,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val href: String? = null,
    val id: String? = null,
    @SerialName("is_playable")
    val isPlayable: Boolean? = null,
    @SerialName("linked_from")
    val linkedFrom: LinkedFrom? = null,
    val restrictions: Restrictions? = null,
    val name: String? = null,
    val popularity: Int? = null,
    @SerialName("preview_url")
    val previewUrl: String? = null,
    @SerialName("track_number")
    val trackNumber: Int? = null,
    val type: String? = null,
    val uri: String? = null,
    @SerialName("is_local")
    val isLocal: Boolean? = null,
)
