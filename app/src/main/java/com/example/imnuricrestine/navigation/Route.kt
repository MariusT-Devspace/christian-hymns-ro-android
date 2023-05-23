package com.example.imnuricrestine.navigation

sealed class Route(val route: String) {
    object IndexRoute : Route("index-route")
    object HymnDetailsRoute : Route("hymn-details-route")
}