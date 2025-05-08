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
     * @param screenName The name of the screen (e.g., "Index", "HymnDetails").
     */
    fun logScreenView(screenName: String) {
        val params = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
    }

    /**
     * Logs any form of navigation within the index pagination.
     * @param method The method used ("range browser", "previous button", "next button").
     * @param currentRange The range the user was in before navigating.
     * @param targetRange The range the user navigated to.
     */
    fun logIndexNavigation(method: String, currentRange: String, targetRange: String) {
        val params = Bundle().apply {
            putString("method", method)
            putString("current_range", currentRange)
            putString("target_range", targetRange)
        }
        firebaseAnalytics.logEvent("navigate_index", params)
    }
}