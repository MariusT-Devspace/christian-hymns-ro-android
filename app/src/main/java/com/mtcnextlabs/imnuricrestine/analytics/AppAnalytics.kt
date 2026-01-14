package com.mtcnextlabs.imnuricrestine.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase



object AppAnalytics {
    private val firebaseAnalytics: FirebaseAnalytics
        get() = Firebase.analytics

    /**
     * Logs a screen view event.
     * @param screenName The name of the screen (e.g., "Hymns", "HymnDetails").
     */
    fun logScreenView(screenName: String) {
        val params = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
    }

    /**
     * Logs any form of navigation within the hymns pagination.
     * @param method The method used ("range browser", "previous button", "next button").
     * @param currentRange The range the user was in before navigating.
     * @param targetRange The range the user navigated to.
     */
    fun logHymnsPagination(method: String, currentRange: String, targetRange: String) {
        val params = Bundle().apply {
            putString("method", method)
            putString("current_range", currentRange)
            putString("target_range", targetRange)
        }
        firebaseAnalytics.logEvent("navigate_hymns_pagination", params)
    }

    /**
     * Logs when a user taps on a bottom navigation bar item.
     * @param destination The destination route/name (e.g., "Hymns", "Favorite").
     */
    fun logBottomBarNavigation(destination: String) {
        val params = Bundle().apply {
            putString("destination", destination)
        }
        firebaseAnalytics.logEvent("navigate_bottom_bar", params)
    }

    /**
     * Logs when a hymn is added to favorites.
     * @param hymnId The id of the hymn saved to favorites.
     */
    fun logAddFavorite(hymnId: Int) {
        val params = Bundle().apply {
            putInt("hymn_id", hymnId)
        }
        firebaseAnalytics.logEvent("add_favorite", params)
    }

    /**
     * Logs when a hymn is removed from favorites.
     * @param hymnId The id of the hymn removed from favorites.
     */
    fun logRemoveFavorite(hymnId: Int) {
        val params = Bundle().apply {
            putInt("hymn_id", hymnId)
        }
        firebaseAnalytics.logEvent("remove_favorite", params)
    }

    /**
     * Logs when a delete favorite action is undone.
     * @param hymnId The id of the hymn restored.
     */
    fun logUndoDeleteFavorite(hymnId: Int) {
        val params = Bundle().apply {
            putInt("hymn_id", hymnId)
        }
        firebaseAnalytics.logEvent("undo_delete_favorite", params)
    }

    /**
     * Logs when a user accesses hymn content, indicating the source.
     * @param hymnId The id of the hymn being viewed.
     * @param source The origin of the access ("hymns_screen" or "favorites_screen").
     */
    fun logNavigateToHymnDetails(hymnId: Int, source: String) {
        val params = Bundle().apply {
            putInt("hymn_id", hymnId)
            putString("source", source)
        }
        firebaseAnalytics.logEvent("navigate_to_hymn_details", params)
    }
}