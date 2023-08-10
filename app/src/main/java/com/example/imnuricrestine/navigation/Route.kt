package com.example.imnuricrestine.navigation

sealed class Route(val route: String, val title: String="Item") {
    object Index : Route("index", "Index")
    object HymnDetails : Route("hymn-details")
    object Favorites : Route("favorites", "Favorites")
}
