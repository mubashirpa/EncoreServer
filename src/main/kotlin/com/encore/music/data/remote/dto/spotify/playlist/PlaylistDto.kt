package com.encore.music.data.remote.dto.spotify.playlist

import com.encore.music.data.remote.dto.spotify.ExternalUrls
import com.encore.music.data.remote.dto.spotify.Followers
import com.encore.music.data.remote.dto.spotify.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistDto(
    val collaborative: Boolean? = null,
    val description: String? = null,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val followers: Followers? = null,
    val href: String? = null,
    val id: String? = null,
    val images: List<Image>? = null,
    val name: String? = null,
    val owner: Owner? = null,
    val `public`: Boolean? = null,
    @SerialName("snapshot_id")
    val snapshotId: String? = null,
    val tracks: TracksPlaylist? = null,
    val type: String? = null,
    val uri: String? = null,
)
