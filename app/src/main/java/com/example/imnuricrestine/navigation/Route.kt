package com.example.imnuricrestine.navigation

sealed class Route(val route: String) {
    object Index : Route("index")
    object HymnDetails : Route("hymn-details")
}