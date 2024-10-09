package com.encore.music.core

object Spotify {
    const val API_BASE_URL = "https://api.spotify.com/v1"
    const val API_TOKEN_URL = "https://accounts.spotify.com/api/token"
    const val ENDPOINT_GET_ARTIST = "artists/{artist_id}"
    const val ENDPOINT_GET_ARTIST_TOP_TRACKS = "artists/{artist_id}/top-tracks"
    const val ENDPOINT_GET_CATEGORIES = "browse/categories"
    const val ENDPOINT_GET_CATEGORY_PLAYLISTS = "browse/categories/{category_id}/playlists"
    const val ENDPOINT_GET_FEATURED_PLAYLISTS = "browse/featured-playlists"
    const val ENDPOINT_GET_PLAYLIST = "playlists/{playlist_id}"
    const val ENDPOINT_GET_PLAYLIST_ITEMS = "playlists/{playlist_id}/tracks"

    object Parameters {
        const val ADDITIONAL_TYPES = "additional_types"
        const val CLIENT_CREDENTIALS = "client_credentials"
        const val CLIENT_ID = "client_id"
        const val CLIENT_SECRET = "client_secret"
        const val FIELDS = "fields"
        const val GRANT_TYPE = "grant_type"
        const val LIMIT = "limit"
        const val LOCALE = "locale"
        const val MARKET = "market"
        const val OFFSET = "offset"
    }

    object Categories {
        const val CHARTS = "0JQ5DAudkNjCgYMM0TZXDw"
        const val PARTY = "0JQ5DAqbMKFA6SOHvT3gck"
        const val TRENDING = "0JQ5DAqbMKFQIL0AXnG5AK"
    }
}
