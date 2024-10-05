package com.encore.music.data.remote.dto.spotify.album

import com.encore.music.data.remote.dto.spotify.*
import com.encore.music.data.remote.dto.spotify.artist.ArtistDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto(
    @SerialName("album_type")
    val albumType: String? = null,
    @SerialName("total_tracks")
    val totalTracks: Int? = null,
    @SerialName("available_markets")
    val availableMarkets: List<String>? = null,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val href: String? = null,
    val id: String? = null,
    val images: List<Image>? = null,
    val name: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("release_date_precision")
    val releaseDatePrecision: String? = null,
    val restrictions: Restrictions? = null,
    val type: String? = null,
    val uri: String? = null,
    val artists: List<ArtistDto>? = null, // SimplifiedArtist
    val tracks: TracksAlbum? = null,
    val copyrights: List<Copyright>? = null,
    @SerialName("external_ids")
    val externalIds: ExternalIds? = null,
    val genres: List<String>? = null,
    val label: String? = null,
    val popularity: Int? = null,
)
